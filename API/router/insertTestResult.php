<?php
    require "../util/response.php";
    require "../entity/test_result.php";
    require "../service/insertTest.php";

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

    // get score

    if(isset($_POST["score"])){
        $score = $_POST["score"];
        if($score != ""){
            $req -> test_result = $score;
        }else{
            $msg = "[$op]Invalid score format";
            BuildErrorResponse($StatusBadRequest, $msg);
            return;
        }
    }

    serviceInputTest($req);
    
    $msg = "[$op]input success";
    BuildSuccessResponse($msg);

?>