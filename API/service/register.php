<?php
    require "../database/users.php";
    

    /**
     * @return object
     */ 

    function RegisterUser($param){
        error_reporting(0);
        $op = "service/users.php:RegisterUser()";
        
        $isExist = CheckUser($param->email);
        if ($isExist) {
            return new Exception("[$op] email already exist");
        }
        $param->password = password_hash($param->password, PASSWORD_BCRYPT);
        $data = InsertUser($param);
        
        if($data instanceof Exception){
            return $data;
        }
        
    }


?>