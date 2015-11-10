<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="DBMatch" scope="request" />
<jsp:include page="/WEB-INF/_header.jsp" flush="true" />	 
    <div id="cover" >
    </div>

  <section class=userbox>
  		<table>

            <c:forEach var="i" items="${ model }">
        		<form action="${ pageContext.request.contextPath }/user/view/update" method="get">
        		
        		<tr>
             		<td>${ i.email }</td>
               		<td>
               			<input type="hidden" name="userEmail" value="${ i.email }" />
               			<button name="action" value="changeAdmin">Change Admin State</button>
               		</td>
                	<td><button name="action" value="delete">Delete User</button></td>
            	 </tr>
            	 
            	 </form>
        	</c:forEach>
             
        </table>
    </section>

<jsp:include page="/WEB-INF/_footer.jsp" flush="true" />