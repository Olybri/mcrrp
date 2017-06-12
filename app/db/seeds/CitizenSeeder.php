<?php

use Phinx\Seed\AbstractSeed;

class CitizenSeeder extends AbstractSeed
{
    /**
     * Run Method.
     *
     * Write your database seeder using this method.
     *
     * More information on writing seeders is available here:
     * http://docs.phinx.org/en/latest/seeding.html
     */
    public function run()
    {
        $this->execute("DELETE FROM citizen; ALTER TABLE citizen AUTO_INCREMENT = 1");
        
        $table = $this->table("citizen");
        $data = [
            [
                "code" => "LW84",
                "first_name" => "Loris",
                "last_name" => "Witschard",
                "sex" => "M",
                "state_id" => 1,
                "balance" => 200,
                "player" => "dee3dd3f-42f0-4f45-8d03-99ad769dd6a9"],
            [
                "code" => "HM61",
                "first_name" => "Héliodore",
                "last_name" => "Montambauds",
                "sex" => "M",
                "state_id" => 2,
                "balance" => 300,
                "player" => "579e797e-6b1d-475c-9306-1cb17a7fd248"],
            [
                "code" => "SDA2",
                "first_name" => "Sérapine",
                "last_name" => "D'Amiante",
                "sex" => "F",
                "state_id" => 1,
                "balance" => 250,
                "player" => "48c134d2-d6df-4b81-8a28-1aff29ea834e"]
        ];
        $table->insert($data);
        $table->save();
    }
}
