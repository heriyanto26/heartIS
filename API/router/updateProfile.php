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

    $req = new UpdateUser();

    //get userID
    if(isset($_POST["id"])){
        $userID = $_POST["id"];
        if($userID != ""){
            $req -> id = $userID;
        }else{
            $msg = "[$op]Invalid userID format";
            BuildErrorResponse($StatusBadRequest, $msg);
            return;
        }
    }

    //get new phone
    if(isset($_POST["phone"])){
        $phone = $_POST["phone"];
        if($phone != ""){
            $req -> phone = $phone;
        }else{
            $msg = "[$op]Invalid phone format";
            BuildErrorResponse($StatusBadRequest, $msg);
            return;
        }
    }

    //get new gender
    if(isset($_POST["gender"])){
        $gender = $_POST["gender"];
        if($gender != ""){
            $req -> gender = $gender;
        }else{
            $msg = "[$op]Invalid gender format";
            BuildErrorResponse($StatusBadRequest, $msg);
            return;
        }
    }

    
    changeUser($req);
    $msg = "[$op] profile has been updated";

    BuildSuccessResponse($msg);
?>