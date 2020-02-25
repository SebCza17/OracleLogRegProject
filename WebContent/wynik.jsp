<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:scriptlet>
	request.setCharacterEncoding("UTF-8");
	String x=request.getParameter("city");
	out.print(x);
	</jsp:scriptlet>
</body>
</html>