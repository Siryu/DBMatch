<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Database View" scope="request" />
<jsp:include page="_header.jsp" flush="true" />
		<div id="tableNamer"> 
	        <a href="#" class="cancel">&times;</a> 
	        <section class=loginbox>
	        	<article>Table Name</article>
	        	<form method="post" action="${pageContext.request.contextPath}/user/new_table">
			        <input type="text" value="table name" name="tableName"></input>
			    	<button type="submit" id=loginbutt>Submit</button>
	        	</form>
		   	</section>
		</div>	 
    <div id="cover" >
    </div>
    
    <div>
    
		<section class="downloadButton">
			<a href="${pageContext.request.contextPath }/user/setup.sql" download>Download Script</a>
			<a href="#tableNamer">Add Table</a>
			<a href="${pageContext.request.contextPath}/user/template_view">Add Template Table</a>
		</section>
		<section class="dbTitle">
			<h2>${model.name}</h2>
		</section>
    	<section class="someKindaView">
			<c:forEach var="table" items="${model.tables}">
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
			    		<a class="editButton" href="${pageContext.request.contextPath}/user/table_view?tableName=${table.name}">edit</a>
			    		<form method="GET" action="${pageContext.request.contextPath}/user/delete_Table"><input type="submit" value="delete"/>
	    							<input type="hidden" name="tableName" value="${table.name}" />
	    				</form>
		    		</section>
		    	</section>
		    </c:forEach>
	    </section>
	    
	    </div>
	    
<jsp:include page="_footer.jsp" flush="true" />
