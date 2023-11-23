<?php

    require '../util/connection.php';

        /**
         * @return object
         */

    //check user existing
    function CheckUser($email){

        $op = "database/user.php:CheckUser()";

        try{
            $con = GetConnection();

            $sql = "SELECT * FROM user WHERE email like ?";

            $result = $con->prepare($sql);
            $result->execute([$email]);

            $row = $result->fetch();

            if($row['email'] == $email) {
                return true;
            }else{
                return false;
            }

        }catch(PDOException $e){
           throw new Exception("[$op]" . $e->getMessage());
        }
    }

         /**
         * @return object
         */
    //register user
    function InsertUser($param){
        $op = "database/user.php:InsertUser()";

        try{
            $con = GetConnection();

            $query = "INSERT INTO USER (firstname, lastname, fullname, password, email, role, join_date) VALUES (?, ?, ?, ?, ?, 'user', ?)";

            $result = $con->prepare($query);
            $result->execute([
                $param->firstname,
                $param->lastname,
                $param->fullname,
                $param->password,
                $param->email,
                date("Y-m-d")
                
                // $param->phone,
                // $param->gender
            ]);
        }catch(PDOException $e){
            throw new Exception("[$op]" . $e->getMessage());
        }
    }

          /**
         * @return object
         */

        
    function insertTestpsikolog($param){
        $op = "database/user.php:insertTestpsikolog()";
        try{
            $con = GetConnection();

            $query = "INSERT INTO test_result(userID, psikologID) VALUES (?, ?)";

            $result = $con->prepare($query);
            $result->execute([
                $param->id,
                $param->psikolog,
            ]);
        }catch(PDOException $e){
            throw new Exception("[$op]" . $e->getMessage());
        }
    }

     /**
         * @return object
         */

        
         function insertPsikolog($param){
            $op = "database/user.php:insertPsikolog()";
            try{
                $con = GetConnection();
    
                $query = "insert into psikolog (name, gender, pengalaman, bidang) VALUES (?,?,?,?)";
    
                $result = $con->prepare($query);
                $result->execute([
                    $param->name,
                    $param->gender,
                    $param->pengalaman,
                    $param->bidang,
                ]);
            }catch(PDOException $e){
                throw new Exception("[$op]" . $e->getMessage());
            }
        }

         /**
         * @return object
         */
    
        
    function insertConsultation($param){
        $op = "database/user.php:insertTestResult()";

        try{
            $con = GetConnection();

            $query = "INSERT INTO consultation (userID, test_date, place, test_result, psikologID) VALUES (?, ?, ?, 'waiting', ?)";

            $result = $con->prepare($query);
            $result->execute([
                $param->id,
                $param->test_date,
                $param->place,
                $param->psikologid,
                
            ]);
        }catch(PDOException $e){
            throw new Exception("[$op]" . $e->getMessage());
        }
    }

         /**
         * @return object
         */
    // Login    
    function CheckUserLogin($param){
        $op = "database/user.php:CheckUserLogin()";

        try {
            $con = GetConnection();

            $query = "SELECT * FROM user WHERE email like ?";

            $result = $con->prepare($query);
            $result->execute([$param->email]);

            if($row = $result->fetch()){
                if (password_verify($param->password, $row['password'])) {
                    $loginuser = new login();
                    $loginuser -> email = $row['email'];
                    $loginuser -> password = $row['password'];
                    $loginuser -> role = $row['role'];
                    $loginuser -> gender = $row['gender'];
                    $loginuser -> firstname = $row['firstname'];
                    $loginuser -> lastname = $row['lastname'];
                    $loginuser -> phone = $row['phone'];
                    $loginuser -> id = $row['userID'];
                    
                    return $loginuser;

                }else{
                    return new Exception("[$op] Invalid username / password!");
                }
                
            } else {
                return new Exception("[$op] error in when fetch user data");
            }
        } catch (PDOException $e) {
            throw new Exception("[$op]" . $e->getMessage());
        }
    }

    function CheckUserForgotLogin($param){
        $op = "database/user.php:CheckUserLogin()";

        try {
            $con = GetConnection();

            $query = "SELECT * FROM user WHERE email like ?";

            $result = $con->prepare($query);
            $result->execute([$param->email]);

            if($row = $result->fetch()){
              
                    $loginuser = new login();
                    $loginuser -> email = $row['email'];
                    $loginuser -> password = $row['password'];
                    $loginuser -> role = $row['role'];
                    $loginuser -> gender = $row['gender'];
                    $loginuser -> firstname = $row['firstname'];
                    $loginuser -> lastname = $row['lastname'];
                    $loginuser -> phone = $row['phone'];
                    $loginuser -> id = $row['userID'];
                    
                    return $loginuser;
                
            } else {
                return new Exception("[$op] error in when fetch user data");
            }
        } catch (PDOException $e) {
            throw new Exception("[$op]" . $e->getMessage());
        }
    }

    


        /**
        * @return object
        */

    function UpdateUser($param){
        $op = "database/user.php:UpdateUser()";

        try{
            $con = GetConnection();

            $query = "UPDATE user set phone = ?, gender = ? WHERE userID = ?";

            $result = $con->prepare($query);
            $result->execute([
                $param->phone,
                $param->gender,
                $param->id
            ]);
        }catch(PDOException $e){
            throw new Exception("[$op]" . $e->getMessage());
        }
    }

        /**
        * @return object
        */

    function UpdatePassword($param){
        $op = "database/user.php:UpdatePass()";

        try{
            $con = GetConnection();

            $query = "UPDATE user set password = ? WHERE email = ?";

            $result = $con->prepare($query);
            $result->execute([
                $param->password,
                $param->email
            ]);
        }catch(PDOException $e){
            throw new Exception("[$op]" . $e->getMessage());
        }
    }

    /**
     * @return object
     */
    function updateMedicalNotes($params){
        $op = "database/list.php:UpdateNotes()";
        try{
            $con = GetConnection();
            
            $query = "UPDATE consultation
                      JOIN user ON consultation.userID = user.userID
                      SET consultation.notes = ? and consultation.test_result = 'Already Consulted'
                      WHERE user.fullname = ?";

            $result = $con->prepare($query);
            $result->execute([
                $params->notes, 
                $params->fullname]);
            
       }catch(PDOException $e){
        throw new Exception("[$op]" . $e->getMessage());
        }
    }

        /**
         * @return object
         */
    function CheckUserInputGetProfile($param){
        $op = "database/user.php:CheckUserInputGetProfile()";

        try{
            $con = GetConnection();

            $query = "SELECT * FROM user WHERE userID like ?";
            $result = $con->prepare($query);
            $result->execute([$param->id]);

            if($row = $result -> fetch()){
                $getProfile = new profile();
                $getProfile -> id = $row['userID'];
                $getProfile -> fullname = $row['fullname'];
                $getProfile -> firstname = $row['firstname'];
                $getProfile -> email = $row['email'];
                $getProfile -> phone = $row['phone'];
                $getProfile -> gender = $row['gender'];

                return $getProfile;
            }


        }catch(PDOException $e){
            throw new Exception("[$op]" . $e->getMessage());
        }
    }

    /**
     * @return object
     */
    
    function insertTestResult($param){
        $op = "database/user.php:insertTestResult()";

        try{
            $con = GetConnection();

            $query = "INSERT INTO test_result (userID, test_date, test_result) VALUES (?, ?, ?)";

            $result = $con->prepare($query);
            $result->execute([
                $param->id,
                date("Y-m-d"),
                $param -> test_result
            ]);
        }catch(PDOException $e){
            throw new Exception("[$op]" . $e->getMessage());
        }
    }





    

?>