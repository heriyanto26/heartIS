<?php
    require "../database/users.php";
    

    /**
     * @return object
     */ 

    function serviceInput($param){
        //error_reporting(0);
        $op = "service/users.php:insertTestpsikolog()";

        $data = insertTestpsikolog($param);
        
        if($data instanceof Exception){
            return $data;
        }
        
    }


?>