<?php
require "../database/users.php";
    

/**
 * @return object
 */ 

function serviceInputTest($param){
    //error_reporting(0);
    $op = "service/users.php:insertTestpsikolog()";

    $data = insertTestResult($param);
    
    if($data instanceof Exception){
        return $data;
    }
    
}
?>