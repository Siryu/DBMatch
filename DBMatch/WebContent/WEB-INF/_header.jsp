<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>${requestScope.pageTitle}</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/Assets/CSS/main.css" type="text/css">
		<c:if test="${requestScope.script != null }">
				<script src="${pageContext.request.contextPath}/Assets/Scripts/register.jsp"></script>
		</c:if>
				<script src="${pageContext.request.contextPath}/Assets/Scripts/table_view_checkbox.jsp"></script>
	</head>
	<body>
	<header>
        <div>
            <a id="logo" href="${pageContext.request.contextPath}"><h1>DBMatch</h1></a>
            <p>Find Your Data's Match</p>
            <c:choose>
	            <c:when test="${sessionScope.user == null }">
	            	<section class="headerLinks">
	            		<a href="#loginScreen">Login</a>
		            	<a href="${pageContext.request.contextPath }/register">Register</a> 
		            </section>
	            </c:when>
	            <c:otherwise>
		            <section class="headerLinks">
		            	<a href="${pageContext.request.contextPath }/user/dash">DashBoard</a>
		            	<a href="${pageContext.request.contextPath }/user/logout">Log out</a>
	            	</section>
	            </c:otherwise>
            </c:choose>
        </div>
    </header>
    <div id="loginScreen"> 
        <a href="#" class="cancel">&times;</a> 
        <section class=loginbox>
        	<article>Login</article>
        	<form method="post" action="${pageContext.request.contextPath }/login">
		        <input type="email" value="email" name="username"></input>
		        <input type="password" value="password" name="password"></input>
		    	<button type="submit" id=loginbutt>Login</button>
        	</form>
	    	<article id=register>Don't have an account? 
	    		<a href="${pageContext.request.contextPath }/register">Register Now!</a>
	    	</article>
	   	</section>
	</div>
