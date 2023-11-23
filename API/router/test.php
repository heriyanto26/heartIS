<?php
require '../util/connection.php';

 if(isset($_POST["id"])){
    $userID = $_POST["id"];
    
    
}

try{
    $con = GetConnection();

    $query = "SELECT * FROM test_result 
              WHERE userID = ?
              ORDER BY test_date DESC";

    $result = $con->prepare($query);
    $result->execute([$userID]);
    $food = array();
    while($row = $result->fetch(PDO::FETCH_ASSOC)){
        $food[] = $row;
    }
        
    


echo json_encode($food);
       
    


}catch(PDOException $e){
    $op = "test";
    throw new Exception("[$op]" . $e->getMessage());
}


?>