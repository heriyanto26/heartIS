<?php
    require "../util/response.php";
    require "../entity/users.php";
    require "../util/validate.php";
    require "../util/globalvariable.php";
    require "../database/users.php";

    $op = "router/getProfile.php";

    if($_SERVER["REQUEST_METHOD"] != "POST"){
        $msg = "[$op]Invalid request method";
        BuildErrorResponse($StatusBadRequest, $msg);
        return;
    }

    $req = new profile();

    if(isset($_POST["id"])){
        $req -> id = $_POST["id"];
    } else {
        $msg = "[$op]ID is required";
        BuildErrorResponse($StatusBadRequest, $msg);
        return;
    }



    $res = CheckUserInputGetProfile($req);
    BuildSuccessResponse($res);
?>

