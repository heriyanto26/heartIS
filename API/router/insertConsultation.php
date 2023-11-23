<?php
    require "../util/response.php";
    require "../entity/test_result.php";
    require "../service/insertPsikolog.php";

    $op = "router/insertTestResult.php";

    if($_SERVER["REQUEST_METHOD"] != "POST"){
        $msg = "[$op]Invalid request method";
        BuildErrorResponse($StatusBadRequest, $msg);
        return;
    }

    $req = new TestResult();

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
    
    if(isset($_POST["psikolog"])){
        $psikolog = $_POST["psikolog"];
        if($psikolog != ""){
            $req -> psikologid= $psikolog;
        }else{
            $msg = "[$op]Invalid psikologid format";
            BuildErrorResponse($StatusBadRequest, $msg);
            return;
        }
    }

    if(isset($_POST["test_date"])){
        $test_date = $_POST["test_date"];
        if($test_date != ""){
            $test_date = str_replace(' ', '', $test_date);
            $test_date = str_replace('-', '-', $test_date);
            $dateFormat = date('Y-m-d', strtotime($test_date));
            $req -> test_date = $dateFormat;

           // $req -> test_date = $test_date;
        }else{
            $msg = "[$op]Invalid test_date format";
            BuildErrorResponse($StatusBadRequest, $msg);
            return;
        }
    }

    if(isset($_POST["place"])){
        $Place = $_POST["place"];
        if($Place != ""){
            $req -> place = $Place;
        }else{
            $msg = "[$op]Invalid Place format";
            BuildErrorResponse($StatusBadRequest, $msg);
            return;
        }
    }

    // if(isset($_POST[""])){
    //     $test_result = $_POST["score"];
    //     if($test_result != ""){
    //         $req -> test_result = $test_result;
    //     }else{
    //         $msg = "[$op]Invalid test_result format";
    //         BuildErrorResponse($StatusBadRequest, $msg);
    //         return;
    //     }
    // }

    
    serviceInputConsultation($req);
    $msg = "[$op]input success";
   
    BuildSuccessResponse($msg);
?>