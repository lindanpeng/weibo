<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试页面</title>
<style>
</style>
</head>
<body class="bgBody">

	<iframe ID="tryit" width="200px" height="100px" scrolling="auto">
		<style>
html {
	overflow-x: hidden;
}
</style>
	</iframe>

	<script language=javascript>  
    tryit.document.designMode="On";  
    tryit.document.open();  
    tryit.document.innerHTML+=tryit.document.write("<b>asdf</b>");  
    tryit.document.close();  
    tryit.focus();  
  </script>
</body>
</html>