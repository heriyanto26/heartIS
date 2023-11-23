<?php 
    require "../util/response.php";
    require "../entity/update.php";
    require "../service/update.php";
    require "../util/validate.php";
    require "../util/globalvariable.php";


    $op = "router/changePassword.php";

    if($_SERVER["REQUEST_METHOD"] != "POST"){
        $msg = "[$op]Invalid request method";
        BuildErrorResponse($StatusBadRequest, $msg);
        return;
    }

    $req = new UpdatePassword();

    //get userID
    if(isset($_POST["email"])){
        $email = $_POST["email"];
        if($email != ""){
            $req -> email = $email;
        }else{
            $msg = "[$op]Invalid userID format";
            BuildErrorResponse($StatusBadRequest, $msg);
            return;
        }
    }

    //Get new password 
    if(isset($_POST["newPass"])){
        $password = $_POST["newPass"];
        if($password != ""){
            $req -> password = $password;
        }else{
            $msg = "[$op]Invalid password format";
            BuildErrorResponse($StatusBadRequest, $msg);
            return;
        }
    }

    changePassword($req);
    $msg = "[$op] password has been updated";

    BuildSuccessResponse($msg);


?>
