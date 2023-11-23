<?php
    require "../util/response.php";
    require "../entity/update.php";
    require "../service/update.php";
    require "../util/validate.php";
    require "../util/globalvariable.php";


    $op = "router/updateTestResult.php";

    if($_SERVER["REQUEST_METHOD"] != "POST"){
        $msg = "[$op]Invalid request method";
        BuildErrorResponse($StatusBadRequest, $msg);
        return;
    }

    $req = new UpdateTestResult();

    //get userID
    if(isset($_POST["testID"])){
        $testID = $_POST["testID"];
        if($testID != ""){
            $req -> testID = $testID;
        }else{
            $msg = "[$op]Invalid testID format";
            BuildErrorResponse($StatusBadRequest, $msg);
            return;
        }
    }

    changeTestResult($req);
    $msg = "[$op] profile has been updated";

    BuildSuccessResponse($msg);
?>