<?php

class Message extends Page
{
    protected $userOnly = true;
    
    protected function title()
    {
        return "Messages";
    }
    
    protected function run()
    {
        $codes = "";
        foreach($this->db->knownCodes($this->citizen["id"]) as $code)
            $codes .= "<option value='$code'>:$code:</options>";
        
        $contact_list = "";
        foreach($this->db->conversations($this->citizen["id"]) as $conversation)
        {
            $date = strftime("%e %B %Y, %H:%M", $conversation["timestamp"]);
            
            if($conversation["sender_id"] == $this->citizen["id"])
            {
                $contactID = $conversation["receiver_id"];
                if($conversation["seen"])
                    $status = "&#10003";
                else
                    $status = "&#11208";
            }
            else
            {
                $contactID = $conversation["sender_id"];
                if($conversation["seen"])
                    $status = "";
                else
                    $status = "[".tr("new")."]";
            }
            
            $contact = $this->db->citizen($contactID);
            
            $contact_list .= "<tr>\n"
                ."<td>$date</td>\n"
                ."<td>:@".$contact["code"].":</td>\n"
                ."<td>$status ".$conversation["body"]."</td>\n"
                ."</tr>\n";
        }
        
        $this->tpl->set("contacts", $contact_list);
        $this->tpl->set("codes", $codes);
    }
    
    protected function submit()
    {
        $code = strtoupper($_POST["code"]);
        
        if($code == $this->citizen["code"])
            throw new InvalidInputException("You cannot start a conversation with yourself.");
        
        if(empty($this->db->citizenByCode($code)))
            throw new InvalidInputException("Invalid contact's code.");
        
        header("Location: /conversation/$code");
    }
}
