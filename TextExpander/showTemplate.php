<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Untitled Document</title>
</head>

<body>
		<?php
		session_start();
		$_SESSION["templateName"]=$_GET["templateName"];
		?>
		<form action="update.php?templateName=<?php echo $_GET["templateName"]?>" method="post">
		<input name="name" type="textbox" value="<?php echo $_GET["templateName"] ?>"><br/><br/>
		<textarea name="content" rows="10" cols="40"><?php $fileName=__DIR__."\\".$_GET["templateName"].".txt"; echo file_get_contents($fileName) ?></textarea><br/><br/>
		<input type="submit" name="Update" value="Update">
		<input type="submit" name="Update" value="Delete" onClick="return confirm('Are you sure?');">
		</form>
	
	
</body>
</html>
