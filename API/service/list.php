<?php
    require "../database/list.php";

    /**
     * @return object
     */

    function GetMentalTestScore($param) {
        error_reporting(0);
        $op = "service/GetFoodList";
        
        $data = showMentalTest($param);
        return $data;
    }

    function GetMentalTestList($param) {
       // error_reporting(0);
        $op = "service/GetFoodList";
        
        $data = showMentalList($param);
        return $data;
    }
    
    function GetPsikologList($param){
        //error_reporting(0);
        $op = "service/GetPsikologList";
        
        $data = showPsikologList($param);
        return $data;
    }

    function GetSchedule(){
        $op = "service/GetSchedule";

        $data = showSchedule('');
        return $data;        
    }
    

    function GetMedicalNotes($param){
        $op = "service/GetMedicalNotes";

        $data = getUserMedicalNotes($param);
        return $data;        
    }

    function RequestInput1($param){
        //error_reporting(0);
        $op = "service/RequestInput1";
        
        $data = showPsikologList($param);
        return $data;
    }

    function GetPsikologSchedule($param){
        //error_reporting(0);
        $op = "service/GetPsikologSchedule";
        
        $data = showPsikologSchedule($param);
        return $data;
    }
    function GetDashboard($param){
        $op = "service/GetDashboard";
        $data = showDashboard($param);
        return $data;
    }
?>