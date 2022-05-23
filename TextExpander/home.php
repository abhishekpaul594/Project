<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Untitled Document</title>
</head>

<body>
	<a href="addnew.html"> Add New
	</a><br>
	<b>Available Templates</b> <br>
	<?php
//	session_start();
//	$_SESSION["templateName"]='';
	$txtFileCount = count( glob('*.txt') );
	echo "Total saved template:".$txtFileCount;
	echo "<br>";
	foreach(glob('*.txt') as $file) {
		$file=str_replace(".txt","",$file);
    	echo "<a href='showTemplate.php?templateName=".$file."'>".$file."</a>";
		echo "</br>";
	}
	?>
</body>
</html>
