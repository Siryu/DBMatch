<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="DBMatch - Error" scope="request" />
<jsp:include page="/WEB-INF/_header.jsp" flush="true" />	
	<div id="cover" >
    </div>
	<div>
		<h3><c:out value="${model}" /></h3>
	</div> 
<jsp:include page="/WEB-INF/_footer.jsp" flush="true" />