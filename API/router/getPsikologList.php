<?php
    require "../util/response.php";
    require "../entity/list.php";
    require "../service/list.php";
    require "../util/globalvariable.php";

    $op = "router/psikologlist";

    if($_SERVER["REQUEST_METHOD"] != "POST"){
        $msg = "[$op] wrong request method"; // [$op] -> biar indikasi errornya gampang 
        BuildErrorResponse($StatusBadRequest, $msg);
        return;
    }
    // get catagory
    $req = new psikolog();

    if(isset($_POST['catagory'])){
        $req-> catagory = $_POST['catagory'];
    }else{
        $msg = "[$op] catagory can not be empty";
        BuildErrorResponse($StatusBadRequest, $msg);
        return;
    }


    $res = GetPsikologList($req);

    if($res instanceof Exception){
        BuildErrorResponse($StatusInternalServerError, $res->getMessage());
        return;
    }
    BuildSuccessResponse($res);

?>