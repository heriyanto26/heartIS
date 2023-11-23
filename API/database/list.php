<?php
    require '../util/connection.php';

    

    /**
     * @return object
     */

    function showMentalTest($param){
        $op = "database/list.php";
        try{
            $con = GetConnection();

            $query = "SELECT * FROM test_result 
                      WHERE userID = ?
                      ORDER BY testID desc";

            $result = $con->prepare($query);
            $result->execute([$param->id]);
            // $list = array();
            // while($row = $result->fetch(PDO::FETCH_ASSOC)){
            //     $list[] = $row;
            // }
            if($row = $result->fetch()){
                $getScore = new mentalTest();
                $getScore->id = $row["userID"];
                $getScore->testID = $row["testID"];
                $getScore->test_result = $row["test_result"];
                $getScore->test_date = $row["test_date"];
               
                return $getScore;
            }
                
            
        }catch(PDOException $e){
            throw new Exception("[$op]" . $e->getMessage());
        }
    }
    
    /**
     * @return object
     */

    function showMentalList($param){
        $op = "database/list.php";
        try{
            $con = GetConnection();

            $query = "SELECT * FROM test_result 
                      WHERE userID = ?
                      ORDER BY testID desc";

            $result = $con->prepare($query);
            $result->execute([$param->id]);

            $list = array();
            while($row = $result->fetch(PDO::FETCH_ASSOC)){
                $list[] = $row;
            }
            return $list;
       }catch(PDOException $e){
        throw new Exception("[$op]" . $e->getMessage());
    }
}

    /**
     * @return object
     */

    function showPsikologList($param){
        $op = "database/list.php";
        try{
            $con = GetConnection();

            $query = "SELECT * FROM psikolog 
                      WHERE category = ?";

            $result = $con->prepare($query);
            $result->execute([$param->catagory]);

            $list = array();
            while($row = $result->fetch(PDO::FETCH_ASSOC)){
                $list[] = $row;
            }
            return $list;
       }catch(PDOException $e){
        throw new Exception("[$op]" . $e->getMessage());
        }
    }

    /**
     * @return object
     */

     function showUserList($param){
        $op = "database/list.php";
        try{
            $con = GetConnection();

            $query = "SELECT * FROM user where role = 'user'";

            $result = $con->prepare($query);
            $result->execute([]);

            $list = array();
            while($row = $result->fetch(PDO::FETCH_ASSOC)){
                $list[] = $row;
            }
            return $list;
       }catch(PDOException $e){
        throw new Exception("[$op]" . $e->getMessage());
        }
    }

    /**
     * @return object
     */

     function showSchedule(){
        $op = "database/list.php";
        try{
            $con = GetConnection();

            $query = "SELECT user.fullname, psikolog.name, consultation.place, date_format(consultation.test_date, '%d-%m-%y') AS DATE_TEST, CAST(consultation.test_date AS TIME) AS TIME_TEST
            FROM consultation
            JOIN user ON consultation.userID = user.userID
            JOIN psikolog ON consultation.psikologID = psikolog.psikologID
            where test_result = 'waiting'";

            $result = $con->prepare($query);
            $result->execute([]);

            $list = array();
            while($row = $result->fetch(PDO::FETCH_ASSOC)){
                $list[] = $row;
            }
            return $list;
       }catch(PDOException $e){
        throw new Exception("[$op]" . $e->getMessage());
        }
    }
     /**
     * @return object
     */

     function getUserMedicalNotes($params){
        $op = "database/list.php";
        try{
            $con = GetConnection();

            $query = "SELECT user.fullname, psikolog.name, consultation.place, notes, date_format(consultation.test_date, '%d-%m-%y') AS DATE_TEST, CAST(consultation.test_date AS TIME) AS TIME_TEST
            FROM consultation
            JOIN user ON consultation.userID = user.userID
            JOIN psikolog ON consultation.psikologID = psikolog.psikologID
            WHERE user.fullname = ?";

            $result = $con->prepare($query);
            $result->execute([$params -> fullname]);

            if($row = $result->fetch()){
                $getNotes = new medicalNotes();
                $getNotes->fullname = $row["fullname"];
                $getNotes->name = $row["name"];
                $getNotes->place = $row["place"];
                $getNotes->notes = $row["notes"];
                $getNotes->DATE_TEST = $row["DATE_TEST"];
                $getNotes->TIME_TEST = $row["TIME_TEST"];
               
                return $getNotes;
            }
            
       }catch(PDOException $e){
        throw new Exception("[$op]" . $e->getMessage());
        }
    }

    /**
     * @return object
     */

     function updateTestResult($param){
        $op = "database/list.php:UpdateTest()";
        try{
            $con = GetConnection();

            $query = "UPDATE test_result SET test_result = 'approve' WHERE testID = ?";

            $result = $con->prepare($query);
            $result->execute([$param->testID]);

            $list = array();
            while($row = $result->fetch(PDO::FETCH_ASSOC)){
                $list[] = $row;
            }
            return $list;
       }catch(PDOException $e){
        throw new Exception("[$op]" . $e->getMessage());
        }
    }

    /***
     * @return object
     */
    
    function showPsikologSchedule($param){
        $op = "database/list.php";
        try{
            $con = GetConnection();

            $query = "SELECT * FROM consultation
                      WHERE psikologID = ?
                      ORDER BY testID desc";

            $result = $con->prepare($query);
            $result->execute([$param->psikologid]);

            $list = array();
            while($row = $result->fetch(PDO::FETCH_ASSOC)){
                $list[] = $row;
            }
            return $list;
       }catch(PDOException $e){
        throw new Exception("[$op]" . $e->getMessage());
        }
    }

    /***
     * @return object
     */

    function showDashboard($param){
        $op = "database/list.php";
        try{
            $con = GetConnection();

            $queryUser = "SELECT 'tabelUser' AS tabel, count(*) 
                          AS jumlahUser 
                          FROM user 
                          WHERE YEAR(join_date) = ? and MONTH(join_date) = ? AND role = 'user'";

            $queryPsikolog = "SELECT 'tabelPsikolog' AS tabel, count(*) 
                              AS jumlahPsikolog 
                              FROM psikolog 
                              WHERE YEAR(join_date) = ? and MONTH(join_date) = ?";

            $queryTest = "SELECT 'tabelTest' AS tabel, count(*)
                          AS jumlahTest
                          FROM test_result
                          WHERE YEAR(test_date) = ? and MONTH(test_date) = ?";
            
            $queryKonsul = "SELECT 'tabelKonsul' AS tabel, count(*)
                            AS jumlahKonsul
                            FROM consultation
                            WHERE YEAR(test_date) = ? and MONTH(test_date) = ?";
                            

            $resultUser = $con->prepare($queryUser);
            $resultUser->execute([$param->year, $param->month]);

            $resultPsikolog = $con->prepare($queryPsikolog);
            $resultPsikolog->execute([$param->year, $param->month]);

            $resultTest = $con->prepare($queryTest);
            $resultTest->execute([$param->year, $param->month]);

            $resultKonsul = $con->prepare($queryKonsul);
            $resultKonsul->execute([$param->year, $param->month]);

            $list = array();
            while($row = $resultUser->fetch(PDO::FETCH_ASSOC)){
                $list[] = $row;
            }
            while($row = $resultPsikolog->fetch(PDO::FETCH_ASSOC)){
                $list[] = $row;
            }
            while($row = $resultTest->fetch(PDO::FETCH_ASSOC)){
                $list[] = $row;
            }
            while($row = $resultKonsul->fetch(PDO::FETCH_ASSOC)){
                $list[] = $row;
            }
            return $list;


       }catch(PDOException $e){
        throw new Exception("[$op]" . $e->getMessage());
        }
    }



?>