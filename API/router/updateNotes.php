<?php

    require "../util/response.php";
    require "../entity/users.php";
    require "../database/users.php";
    require "../util/globalvariable.php";

    $op = "router/schedule";

    if($_SERVER["REQUEST_METHOD"] != "POST"){
        $msg = "[$op] wrong request method"; // [$op] -> biar indikasi errornya gampang 
        BuildErrorResponse($StatusBadRequest, $msg);
        return;
    }

    $req = new medicalNotes();

    //get fullname

    if(isset($_POST['name'])){
        $req-> fullname = $_POST['name'];
    }else{
        $msg = "[$op] name can not be empty";
        BuildErrorResponse($StatusBadRequest, $msg);
        return;
    }

    //get notes
    if(isset($_POST['notes'])){
        $req-> notes = $_POST['notes'];
    }else{
        $msg = "[$op] notes can not be empty";
        BuildErrorResponse($StatusBadRequest, $msg);
        return;
    }

    updateMedicalNotes($req);
    $msg = "[$op] notes has been updated";

    BuildSuccessResponse($msg);


?>