<?php
    function GetConnection(){
        $servername = "localhost";
        $username = "root";
        $password = ""; 
        $db =  "project";

        try{
            $conn = new PDO("mysql:host=$servername;dbname=$db", $username, $password);
            $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
            return $conn;
            
        }catch(PDOException $e){
            echo "Connection failed: " . $e->getMessage();
        }
    }
?>