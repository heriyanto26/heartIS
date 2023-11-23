<?php
    require "../util/response.php";
    require "../entity/test_result.php";
    require "../service/insertPsikologData.php";

    $op = "router/insertPsikolog.php";

    if($_SERVER["REQUEST_METHOD"] != "POST"){
        $msg = "[$op]Invalid request method";
        BuildErrorResponse($StatusBadRequest, $msg);
        return;
    }

    $req = new Psikolog();


    if(isset($_POST["name"])){
        $name = $_POST["name"];
        if($name != ""){
            $req -> name = $name;
        }else{
            $msg = "[$op]Invalid name format";
            BuildErrorResponse($StatusBadRequest, $msg);
            return;
        }
    }

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

    if(isset($_POST["pengalaman"])){
        $pengalaman = $_POST["pengalaman"];
        if($pengalaman != ""){
            $req -> pengalaman = $pengalaman;
        }else{
            $msg = "[$op]Invalid pengalaman format";
            BuildErrorResponse($StatusBadRequest, $msg);
            return;
        }
    }

    if(isset($_POST["bidang"])){
        $bidang = $_POST["bidang"];
        if($bidang != ""){
            $req -> bidang = $bidang;
        }else{
            $msg = "[$op]Invalid bidang format";
            BuildErrorResponse($StatusBadRequest, $msg);
            return;
        }
    }
    
    serviceInputPsikolog($req);
    $msg = "[$op]input success";
   
    BuildSuccessResponse($msg);
?>