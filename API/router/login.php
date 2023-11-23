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
        if(ValidateEmail($email)) {
            $req -> email = $email;
        }else{
            $msg = "[$op]Invalid email format";
            BuildErrorResponse($StatusBadRequest, $msg);
            return;
        }
        $req -> email = $email;
    }else{
        $msg = "[$op]Email is required";
        BuildErrorResponse($StatusBadRequest, $msg);
        return; 
    }

    //get password 
    if(isset($_POST["password"])) {
        $password = $_POST["password"];
        if($password != "") {
            $req -> password = $password;
        }else{
            $msg = "[$op]Invalid password format";
            BuildErrorResponse($StatusBadRequest, $msg);
            return;
        }
        $req -> password = $password;
    }else{
        $msg = "[$op]Password is required";
        BuildErrorResponse($StatusBadRequest, $msg);
        return;
    }

    $res = LoginUser($req);
    if($res instanceof Exception) {
        $msg = "[$op]Failed to login";
        BuildErrorResponse($StatusBadRequest, $msg);
        return;
    }

    BuildSuccessResponse($res);
?>