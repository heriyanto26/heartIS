<?php
    require "../database/users.php";
    

    /**
     * @return object
     */ 

    function serviceInputPsikolog($param){
        //error_reporting(0);
        $op = "service/users.php:insertPsikolog()";

        $data = insertPsikolog($param);
        
        if($data instanceof Exception){
            return $data;
        }
        
    }


?>