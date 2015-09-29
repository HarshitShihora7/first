<?php

$mysql_host = "50.62.209.152:3306"; 
$mysql_database = "classifieds"; 
$mysql_user = "prashant"; 
$mysql_password = "_737Ngsv"; 

$test=isset($_POST['test'])?$_POST['test']:'No testing string received';

$target_path="uploads/"

$conn=new mysqli($mysql_host,$mysql_user,$mysql_password,$mysql_database); 


$sql="INSERT INTO `business` (`Id`, `Name`, `Surname`, `Nativeplace`, `Currentcity`, `Mobileno`, `Email`, `Occupation`, `Category`, `Subcategory`, `Companyname`, `Companyadd`, `Businessdesc`, `Website`, `Profileimage`, `Companylogo`, `Visitingcard`, `Flag`) VALUES (NULL, 'ksdnfkn', 'fsdkjckjdsc', 'askjckjsabck', 'akjcbkbsc', 'asjkasbckj', 'sjkbcbasc', 'kdlsknclkdnc', 'asnckncknksncnsac', 'scnkjckkancanskjc', 'cknksncknasc', 'cskkjdbkcscac', 'sckdbskjbasbc', 'mnncabcbashc', 'caskcklkasck', 'klsnkncksa','askklcnasjkckjsac', '0');";

if (mysqli_query($conn, $sql)) {
                        //echo "New record created successfully";
                            //echo "record inserted";

                  } else {
                      echo "Error: " . $sql . "<br>" . mysqli_error($conn);
                  }


      if(isset($_FILES['image']['name'])) 
      {
      	$target_path = $target_path . basename($_FILES['image']['name']);
      	try{	
      		if(!move_uploaded_file($_FILES['image']['tmp_name'],$target_path)
      		{
      			$response['error'] = true;
                $response['message'] = 'Could not move the file!';
      		}
      		else{
      			$responce['error'] = false;
      			$response['message']= 'everything ok';
      		}	
      	}catch(Exception $e) {
        // Exception occurred. Make error flag true
        	$response['error'] = true;
        	$response['message'] = $e->getMessage();
    	}

      }           

$response['test']=$test;
echo json_encode($response);

?>