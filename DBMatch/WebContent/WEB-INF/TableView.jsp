<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Table View" scope="request" />
<jsp:include page="_header.jsp" flush="true" />	
    	
    	<div>
    	
    	<section class="tableDisplayTwo">
    		<h3>${model.name}</h3>
    		<table>
    			<tr>
    				<th>Name</th>
    				<th>Data Type</th>
    				<th>Primary Key?</th>
    				<th></th>
    			</tr>
	    		<c:forEach items="${model.columns}" var="column">
	    			<c:if test="${column.isForeignKey}">
	    			<tr title='References ${pageContext.request.session.getAttribute("chosenDB").getForeignKeyReference(column.getName())}'>
	    				<td><b>${column.name}</b></td>
	    			</c:if>
	    			<c:if test="${not column.isForeignKey}">
	    			<tr>
	    				<td>${column.name}</td>
	    			</c:if>
	    				<td>${column.dataType}</td>
	    				<td><form method="POST" action="${pageContext.request.contextPath}/user/primarykey">
	    					<input type="hidden" name="columnName" value="${column.name}"/>
	    					<input type="hidden" name="tableName" value="${model.name}" />
	    					<input type="checkbox" name="isPrimary" ${model.getIsPrimary(column) ? "checked" : ""} onclick="fireEvent('${column.name}', '${model.name}')" />
	    				</form></td>
	    				<td><form method="GET" action="${pageContext.request.contextPath}/user/delete_Column"><input type="submit" value="delete"/>
	    					<input type="hidden" name="columnName" value="${column.name}"/>
	    					<input type="hidden" name="tableName" value="${model.name}" />
	    				</form></td>
	    			</tr>
	    		</c:forEach>
    		</table>
    		<section class="editAdd">
	    		<c:if test="${not user.admin}">
		    		<a class="addButton" href="${pageContext.request.contextPath}/user/column?table=${model.name}">add</a>
		    	</c:if>
		    	<c:if test="${user.admin}">
		    		<a class="addButton" href="${pageContext.request.contextPath}/user/create_template_column">add</a>
		    	</c:if>
	    		<a class="addButton" href="${pageContext.request.contextPath}/user/foreignkey?table=${model.name}">Add a column from another table</a>
    		</section>
    	</section>
    	
	</div>
    	
<jsp:include page="_footer.jsp" flush="true" />   	
