<?php
    require "../database/users.php";
    

    /**
     * @return object
     */ 

    function serviceInputConsultation($param){
        //error_reporting(0);
        $op = "service/users.php:insertTestResult()";

        $data = insertConsultation($param);
        
        if($data instanceof Exception){
            return $data;
        }
        
    }


?>