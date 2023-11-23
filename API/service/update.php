<?php

    require "../database/users.php";

    /**
     * @return object
     */
     
    function changeUser($param){
        error_reporting(0);
        $op = "service/users.php:changePassword()";
        
        $data = UpdateUser($param);
        return $data;
    }

    function changePassword($param){
        //error_reporting(0);
        $op = "service/users.php:changePassword()";
        $param -> password = password_hash($param->password, PASSWORD_BCRYPT);
        
        $data = UpdatePassword($param);
        return $data;
    }

    function changeTestResult($param){
        error_reporting(0);
        $op = "service/users.php:updateTest()";

        $data = updateTestResult($param);
        return $data;
    }
?>