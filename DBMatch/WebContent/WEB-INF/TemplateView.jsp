<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="_header.jsp" flush="true" />
	<section class="someKindaView">
		<c:forEach var="table" items="${model}">
	    	<section class="tableDisplay">
	    		<h3>${table.name}</h3>
	    		<table>
	    			<tr>
	    				<th>Name</th>
	    				<th>Data Type</th>
	    			</tr>
		    		<c:forEach items="${table.columns}" var="column">
		    			<tr>
		    				<td>${column.name}</td>
		    				<td>${column.dataType}</td>
		    			</tr>
		    		</c:forEach>
	    		</table>
	    		<section class="editAdd">
		    		<a class="addButton" href="${pageContext.request.contextPath}/user/database_view?chosen=${table.name}">add</a>
	    		</section>
	    	</section>
	    </c:forEach>
    </section>
 <jsp:include page="_footer.jsp" flush="true" />   	