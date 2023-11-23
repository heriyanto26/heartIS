<?php

    require "../entity/response.php";

    function BuildSuccessResponse($data){
        header('Contennt-Type: application/json; charset=utf-8');
        $response = new SuccessResponse();
        $response->code = 200;
        $response->data = $data;
        
        echo json_encode($response);
    }

    function BuildErrorResponse($code, $message){
        header('Contennt-Type: application/json; charset=utf-8');
        $response = new ErrorResponse();
        $response->code = $code;
        $response->message = $message;
        
        echo json_encode($response);
    }


?>