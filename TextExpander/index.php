<?php
	$templateName=$_POST["name"];
	$templateContent=$_POST["content"];
	foreach(glob('*.txt') as $existingFile)
	{
		$existingFile=str_replace(".txt","",$existingFile);
		if($templateName==$existingFile)
			echo "<script>
			alert('Template name already exists');
			history.go(-1);
			</script>";
	}
	$myfile = fopen($templateName.".txt", "w");
	fwrite($myfile, $templateContent);
	echo "<script>alert('Template Saved');
		  window.location.href='home.php';
		</script>";
?>