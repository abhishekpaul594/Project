<?php
	session_start();
//	$_SESSION["templateName"]=$_GET["templateName"];
	switch($_POST["Update"])
	{
		case 'Update':
	$oldTemplateName=$_GET["templateName"].".txt";
	$templateName=$_POST["name"].".txt";
	$templateContent=$_POST["content"];
//	$myfile = fopen($templateName.".txt", "w");
//	foreach(glob('*.txt') as $existingFile)
//	{
//		$existingFile=str_replace(".txt","",$existingFile);
//		if($templateName==$existingFile)
//			echo "<script>
//			alert('Template name already exists');
//			history.go(-1);
//			</script>";
//	}
	$filePath=$oldTemplateName;
	file_put_contents($filePath, $templateContent);
	rename($oldTemplateName,$templateName);
//	unlink($oldTemplateName);
	echo "<script>
		  alert('Template updated');
		  window.location.href='home.php';
		  </script>";
	break;
		case 'Delete':
			unlink($_GET["templateName"].".txt");
			echo "<script>
		  alert('Template Deleted');
		  window.location.href='home.php';
		  </script>";
	}
?>