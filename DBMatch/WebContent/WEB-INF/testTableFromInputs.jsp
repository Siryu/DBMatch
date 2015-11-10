<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Table Page</title>
</head>
<body>
	<c:forEach var="i" items="${model.currentTable.columns}">
		<c:out value="Column name ${i.name}"/><p>
		<c:out value="Data Type  ${i.dataType}"/><p>
		
	</c:forEach>
</body>
</html>