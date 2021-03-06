#!/usr/bin/env bash

set -E
cd $(dirname "$0")

trap 'echo -e "\e[1;31mInstallation failed!\e[0m" ; exit' ERR

echo -n "Installing web app... "
cd ../app
if type "composer" &> /dev/null; then
    composer -q install
else
    php composer.phar -q install
fi

echo "Done!"
echo -n "Creating database... "
cd ..
host=$(bin/config.sh database host)
name=$(bin/config.sh database name)
user=$(bin/config.sh database user)
pass=$(bin/config.sh database pass)
statement="CREATE DATABASE IF NOT EXISTS $name"

if [ -z $pass ]; then
    mysql -h $host -u $user -e "$statement"
else
    mysql -h $host -u $user -p$pass -e "$statement"
fi

echo "Done!"
echo -n "Running migrations... "
cd app
if [ -f phinx.yml ]; then
    rm phinx.yml
fi
vendor/bin/phinx -q init

sed -i "s/localhost/$host/g" phinx.yml
sed -i "s/development_db/$name/g" phinx.yml
sed -i "s/root/$user/g" phinx.yml
if [ ! -z $pass ]; then
    sed -i "s/''/$pass/g" phinx.yml
fi
vendor/bin/phinx -q migrate

echo "Done!"
cd ..
bin/build.sh

echo -n "Downloading Spigot... "
curl -sS -o server/spigot.jar https://cdn.getbukkit.org/spigot/spigot-1.12.2.jar

echo "Done!"
echo -n "Installing Spigot... "

init=$(bin/config.sh memory initial)
max=$(bin/config.sh memory max)
cd server
java -Xms$init -Xmx$max -Dcom.mojang.eula.agree=true -Dme.olybri.mcrrp.setup -jar spigot.jar &>/dev/null
sed -i "s/tab-complete:.*$/tab-complete: -1/m" spigot.yml

echo "Done!"
echo "Installation finished!"
