<?php

    require "../util/response.php";
    require "../entity/list.php";
    require "../database/list.php";
    require "../util/globalvariable.php";

    $op = "router/getalluser";

    if($_SERVER["REQUEST_METHOD"] != "POST"){
        $msg = "[$op] wrong request method"; // [$op] -> biar indikasi errornya gampang 
        BuildErrorResponse($StatusBadRequest, $msg);
        return;
    }

    $res = showUserList("");


    BuildSuccessResponse($res)

?>