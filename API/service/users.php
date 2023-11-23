<?php
    require "../database/users.php";

    /**
     * @return object
     */ 
    
     //login

    function LoginUser($param){
        $op = "service/users.php:LoginUser()";
        error_reporting(0);

        $data = CheckUserLogin($param);

        
        if(empty($data)){
            return new exception("[$op] Invalid username / password!");
        }

        if(!password_verify($param->password, $data->password)){
            return new exception("[$op] Invalid username / password!");
        }
        return $data;

     }

     function LoginForgotUser($param){
        $op = "service/users.php:LoginUser()";
        

        $data = CheckUserForgotLogin($param);
        return $data;

     }

     /**
      * @return object
      */
    function EditProfile($param){
        error_reporting(0);
        $op = "service/users.php:EditProfile()";
        
        $data = UpdateUser($param);
        return $data;
    }

    /**
     * @return object
     */
    function updateNotes($param){
        error_reporting(0);
        $op = "service/users.php:updateNotes()";
        
        $data = updateMedicalNotes($param);
        return $data;
    }

   
?>