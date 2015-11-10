<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
function checkEmail() {
	var http = new XMLHttpRequest(); 
	var params = 'email=' + document.getElementById("email").value;
	http.open("POST", "${pageContext.request.contextPath}/checkEmail", true);
	http.onreadystatechange=function() {
		if(http.readyState == 4 && http.status == 200) {
			document.getElementById("emailError").innerHTML = http.responseText;
		}
	}
	http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	http.setRequestHeader("Content-length", params.length);
	http.send(params);
}