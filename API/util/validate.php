
<?php
    function ValidateEmail($email) {
        if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
            return False;
        }
        return True;
    }

    function ValidateName($name) {
        if (!preg_match("/^[a-zA-Z-' ]*$/",$name)) {
            return False;
        }
        return True;
    }

    // make a validation for password
    function ValidatePassword($password) {
        if (!preg_match("/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/", $password)) {
            return False;
        }
        return True;
    }
?>
