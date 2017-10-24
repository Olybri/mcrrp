<?php

/**
 * Page displaying all companies.
 */
class Employment extends Page
{
    protected $userOnly = true;
    
    protected function title()
    {
        return "Employment";
    }
    
    protected function run()
    {
        $employment = [
            "governments" => $this->db->governments(),
            "banks" => $this->db->banks(),
            "presses" => $this->db->presses(),
            "other companies" => $this->db->otherCompanies()
        ];
        
        $this->set("employment", $employment);
    }
    
    protected function submit()
    {
    }
}

