<?php
    require "../util/response.php";
    require "../entity/users.php";
    require "../service/register.php";
    require "../util/validate.php";
    require "../util/globalvariable.php";

    $op = "router/register.php";

    if($_SERVER["REQUEST_METHOD"] != "POST"){
        $msg = "[$op]Invalid request method";
        BuildErrorResponse($StatusBadRequest, $msg);
        return;
    }

    $req = new Regis();

    //get email 
    if(isset($_POST["email"])){
        $email = $_POST["email"];
        if(ValidateEmail($email)){
            $req -> email = $email;
        }else{
            $msg = "[$op]Invalid email format";
            BuildErrorResponse($StatusBadRequest, $msg);
            return;
        }
    }else{
        $msg = "[$op]Email is required";
        BuildErrorResponse($StatusBadRequest, $msg);
        return;
    }

    //get name from email
    if(isset($_POST["email"])){
        list($localpart, $domainport) = explode("@", $email);

        if(strpos($localpart, ".") === false){
            $req -> firstname = ucfirst($localpart);
            $req -> lastname = "";
        }else{
            $name = explode(".", $localpart);
            $req -> firstname = ucfirst($name[0]);
            $req -> lastname = ucfirst($name[1]);
        }
    }else{
        $msg = "[$op]Email is required";
        BuildErrorResponse($StatusBadRequest, $msg);
        return;
    }

    // get full name from email
    $req -> fullname = $req -> firstname . " " . $req -> lastname;

    // get password
    if(isset($_POST["password"])){
        $password = $_POST["password"];
        if($password != ""){
            $req -> password = $password;
        }else{
            $msg = "[$op]Invalid password format";
            BuildErrorResponse($StatusBadRequest, $msg);
            return;
        }
    }else{
        $msg = "[$op]Password is required";
        BuildErrorResponse($StatusBadRequest, $msg);
        return;
    }


    $res = RegisterUser($req);
    if($res instanceof Exception){
        if(strcmp($res->getMessage(), $RegisterUserExist) == 0){
            BuildErrorResponse($StatusDataAlreadyExist, $res->getMessage());
            return;
        }
        BuildErrorResponse($StatusInternalServerError, $res->getMessage());
        return;
    }

    $msg = "[$op]Register success";
   
    BuildSuccessResponse($msg);

?>