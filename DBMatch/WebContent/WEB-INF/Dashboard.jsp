<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="DBMatch" scope="request" />
<jsp:include page="/WEB-INF/_header.jsp" flush="true" />	 
    <div id="cover" >
    </div>
    		<div id="tableNamer"> 
	        <a href="#" class="cancel">&times;</a> 
	        <section class=loginbox>
		        <c:if test="${model.admin }">
		      	  <article>Template Name</article>
	        		<form method="get" action="${pageContext.request.contextPath }/user/selection">
			        <input type="text" value="template name" name="chosen"></input>
		        </c:if>
		         <c:if test="${ not model.admin }">
	        		<article>Database Name</article>
	        		<form method="get" action="${pageContext.request.contextPath }/user/database_view">
			        <input type="text" value="database name" name="databaseName"></input>
	        	</c:if>
			    	<button type="submit" id=loginbutt>Submit</button>
	        	</form>
		   	</section>
		</div>	 
    <div id="cover" >
    </div>

    <div>
        <section class=dashbox>
            <h2>Hello, ${model.email}!</h2>
            <a href="${ pageContext.request.contextPath }/user/account_Settings"><img id=accountsettings src="../Assets/Images/icon_gear.png" /></a>
            <article><a href="${ pageContext.request.contextPath }/user/account_Settings">Account Settings</a></article>
                

            <c:if test="${model.admin }">
            	<article id=create> + Create a <a href="#tableNamer">Template</a></article>
            	<article id=viewusers><a href="${ pageContext.request.contextPath }/user/users">View Users</a></article>
            </c:if>
               
            <c:if test="${ not model.admin }">
            	<article id=create> + Create a <a href="#tableNamer">Database</a></article>
            </c:if>

            <section class=recents>

                <article>Created Items</article>             
       
       		<table>
            	<c:forEach var="i" items="${ databases }">

            	<tr>
            		<td><a href="${ pageContext.request.contextPath }/user/selection?chosen=${i.name}" ><c:out value="${ i.name }" /></a></td>
            		<c:if test="${model.admin}">
            	 		<td><form method="GET" action="${pageContext.request.contextPath}/user/delete_Database"><input type="submit" value="delete"/>
	    					<input type="hidden" name="tableName" value="${i.name}" />
	    				</form></td> 
	    			</c:if>
	    			<c:if test="${not model.admin}">
		    			<td><form method="GET" action="${pageContext.request.contextPath}/user/delete_Database"><input type="submit" value="delete"/>
		    				<input type="hidden" name="tableName" value="${i.name}" />
		    			</form></td>
	    			</c:if>
            	</tr>

            	</c:forEach>
            </table>
           
            </section>
                
        </section>
    </div>

 <jsp:include page="/WEB-INF/_footer.jsp" flush="true" />
