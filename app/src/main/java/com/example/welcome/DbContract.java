package com.example.welcome;

public class DbContract {
    public static final String ip = "192.168.100.59";

    public static final String urlLogin = "http://"+ip+"/API/router/login.php";
    public static final String urlSignUp = "http://"+ip+"/API/router/register.php";

    public static final String urlGetProfile = "http://"+ip+"/API/router/getProfile.php";
    public static final String urlEditProfile = "http://"+ip+"/API/router/updateProfile.php";
    public static final String urlEditPass = "http://"+ip+"/API/router/updatePassword.php";
    public static final String urlDeleteProfile = "http://"+ip+"/API/router/deleteProfile.php";

    public static final String urlInsertRequest = "http://"+ip+"/API/router/insertTestpsikolog.php";

    public static final String urlGetUser = "http://"+ip+"/API/router/getAllUser.php";
    public static final String urlGetMedicalNotes = "http://"+ip+"/API/router/getUserMedicalNotes.php";
    public static final String urlInsertConsultation = "http://"+ip+"/API/router/insertConsultation.php";
    public static final String urlInsertTestResult = "http://"+ip+"/API/router/insertTestResult.php";
    public static final String urlGetListHistory = "http://"+ip+"/API/router/getMentalTestList.php";
    public static final String urlUpdateNotes = "http://"+ip+"/API/router/updateNotes.php";

    public static final String urlGetPsikolog = "http://"+ip+"/API/router/getPsikologList.php";
    public static final String urlGetSchedule = "http://"+ip+"/API/router/getSchedule.php";
    public static final String urlGetPsikologSchedule = "http://"+ip+"/API/router/getPsikologSchedule.php";
    public static final String urlGetDashboard = "http://"+ip+"/API/router/getDashboard.php";
    public static final String urlGetForgot = "http://"+ip+"/API/router/getForgotPass.php";

    public static final String urlAddPsikolog = "http://"+ip+"/API/router/insertPsikolog.php";
}