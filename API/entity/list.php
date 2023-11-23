<?php
    class mentalTest{
      public $id;
      public $testID;
       public $test_result;
       public $test_date;
    }

    class psikolog{
      public $catagory;
    }

    class Request{
      public $id;
      public $userid;
      public $psikologid;
      public $media;
      public $date;
      public $time;
      public $notes;
  } 
  class psikologSchedule{
    public $id;
    public $psikologid;
    public $date;
    public $time;
    public $notes;
  }
    
    ?>