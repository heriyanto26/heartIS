<?php
        require "../util/response.php";
        require "../entity/users.php";
        require "../service/list.php";
        require "../util/globalvariable.php";

        $op = "router/schedule";

        if($_SERVER["REQUEST_METHOD"] != "POST"){
            $msg = "[$op] wrong request method"; // [$op] -> biar indikasi errornya gampang 
            BuildErrorResponse($StatusBadRequest, $msg);
            return;
        }

        $req = new medicalNotes();

        if(isset($_POST['name'])){
            $req-> fullname = $_POST['name'];
            
        }else{
            $msg = "[$op] name can not be empty";
            BuildErrorResponse($StatusBadRequest, $msg);
            return;
        }

        $res = GetMedicalNotes($req);

        if($res instanceof Exception){
            BuildErrorResponse($StatusInternalServerError, $res->getMessage());
            return;
        }
        BuildSuccessResponse($res);
    


?>