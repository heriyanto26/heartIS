<?php
    require "../util/response.php";
    require "../entity/list.php";
    require "../service/list.php";
    require "../util/globalvariable.php";

    $op = "router/getPsikologSchedule";

    if($_SERVER["REQUEST_METHOD"] != "POST"){
        $msg = "[$op] wrong request method"; // [$op] -> biar indikasi errornya gampang 
        BuildErrorResponse($StatusBadRequest, $msg);
        return;
    }

    $req = new psikologSchedule();

    //get catagory 
    if(isset($_POST['id'])){
        $req-> psikologid = $_POST['id'];
    }else{
        $msg = "[$op] catagory can not be empty";
        BuildErrorResponse($StatusBadRequest, $msg);
        return;
    }

    $res = GetPsikologSchedule($req);
    if($res instanceof Exception){
        BuildErrorResponse($StatusInternalServerError, $res->getMessage());
        return;
    }
    BuildSuccessResponse($res);
?>