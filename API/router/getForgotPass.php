<?php

    require "../util/response.php";
    require "../entity/users.php";
    require "../service/users.php";
    require "../util/globalvariable.php";
    require "../util/validate.php";

    $op = "router/login";

    if($_SERVER["REQUEST_METHOD"] != "POST") {
        $msg = "[$op]Invalid request method";
        BuildErrorResponse($StatusBadRequest, $msg);
        return;
    }

    $req = new login();

    //get email
    if(isset($_POST["email"])) {
        $email = $_POST["email"];
        $req -> email = $email;
    }else{
        $msg = "[$op]Email is required";
        BuildErrorResponse($StatusBadRequest, $msg);
        return; 
    }



    $res = LoginForgotUser($req);
   

    BuildSuccessResponse($res);
?>