<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Create Columns" scope="request" />
<jsp:include page="_header.jsp" flush="true" />
    <div id="cover" >
    </div>
    
	<div> 
    
       <h2>Add a column from another table</h2>
       <article id=infohead>Please tell us what table and column you want to include.</article>
       
       <form method="post" action="${pageContext.request.contextPath}/user/foreignkey">
       <section class=container>
       <section class=dataForm>
           <table>
                <tr>
                <td><p>Enter the title of your new column:</p></td>
                <td><input type="text" name="childColumn"/></td>
               </tr>
                <tr>
	                <td><p>What table do you want to add a column from?</p></td>
		            <td> 
		                <select id="selectTable" onChange="changeTable()" name="parentTable">
		                	<c:forEach items="${model.tables}" var="t" >
			             		<option value="${t.name}"><c:out value="${t.name}" /></option>
			            	</c:forEach>
		          		</select>
		           </td>
               </tr>
             </table>
               
           <c:forEach items="${model.tables}" var="t">
	           <table id="${t.name}" style="display:none">
	               <tr >
	                <td><p>Select the primary key column you want to add to your table: (MUST be a primary key)</p></td>
	                <td>
	                	<select name="parentColumn" id="${t.name}select" disabled>
	                		<c:forEach items="${t.columns}" var="c">
	                			<c:if test="${t.getIsPrimary(c)}">
	                				<option value="${c.name}"><c:out value="${c.name}" />
	                			</c:if>
	                		</c:forEach>
	                	</select>
					</td>
	               </tr>
	            </table>
           </c:forEach>
       </section>
       </section>
  
    <section class=buttons>
    	<input type="hidden" name="childTable" value='${param.table}'/>
  		<input type="submit" value="Submit" />
    </section>
    </form>

    </div>
    
<script type="text/javascript">
	var currentVisible = 'none';
	
	function changeTable(){
	    var table = document.getElementById('selectTable').value;
	    var element = document.getElementById(table);
	
		if(currentVisible != 'none') {
			document.getElementById(currentVisible).style.display = 'none';
			document.getElementById(currentVisible + 'select').disabled = true;
		}

	    element.style.display = 'block';
	    document.getElementById(table + 'select').disabled = false;
		currentVisible = table;
	}
	
	window.onload = function() {
		changeTable();
	}
   
</script>
    
<jsp:include page="_footer.jsp" flush="true" />