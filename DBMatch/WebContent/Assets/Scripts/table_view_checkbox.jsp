<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
function fireEvent(column, table) {
	var http = new XMLHttpRequest(); 
	var params = 'column=' + column + '&table=' + table;
	http.open("POST", "${pageContext.request.contextPath}/user/primarykey", true);
	http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	http.setRequestHeader("Content-length", params.length);
	http.send(params);
}