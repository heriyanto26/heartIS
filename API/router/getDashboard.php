<?php
    require "../util/response.php";
    require "../entity/dashboard.php";
    require "../service/list.php";

    $op = "router/insertTestResult.php";

    if($_SERVER["REQUEST_METHOD"] != "POST"){
        $msg = "[$op]Invalid request method";
        BuildErrorResponse($StatusBadRequest, $msg);
        return;
    }

    $req = new dashboard();

    if(isset($_POST["bulan"])){
        $bulan = $_POST["bulan"];
        if($bulan != ""){
            $nama_bulan = array(
                'Januari', 'Februari', 'Maret', 'April', 'Mei', 'Juni',
                'Juli', 'Agustus', 'September', 'Oktober', 'November', 'Desember'
            );
            
            $angka_bulan = array_search($bulan, $nama_bulan) + 1;
    
            $Bulan = sprintf("%02d", $angka_bulan);
            //echo $Bulan;
            $req -> month = $Bulan;
        }else{
            $msg = "[$op]Invalid bulan format";
            BuildErrorResponse($StatusBadRequest, $msg);
            return;
        }
    }

    // get score

    if(isset($_POST["tahun"])){
        $tahun = $_POST["tahun"];

        if($tahun != ""){
           
            $cleanedString = str_replace('"', '', $tahun); // Menghilangkan kutipan ganda
            $yearInt = intval($cleanedString);
            $req -> year = $yearInt;

        }else{
            $msg = "[$op]Invalid tahun format";
            BuildErrorResponse($StatusBadRequest, $msg);
            return;
        }
    }

    
    $res = GetDashboard($req);
    if($res instanceof Exception){
        BuildErrorResponse($StatusInternalServerError, $res->getMessage());
        return;
    }
   BuildSuccessResponse($res);
?>