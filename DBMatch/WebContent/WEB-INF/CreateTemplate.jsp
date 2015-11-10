<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="models.database.DataType"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Create Template" scope="request" />
<jsp:include page="_header.jsp" flush="true" /> 
<c:set var="dataTypes" value="<%=DataType.values()%>" />
    <div id="cover" >
    </div>
    

   <div> 
       <h2>Create Template</h2>
       <article id=infohead>Enter the data for this column.</article>
       
       
       <form method="post" action="${pageContext.request.contextPath}/user/template">
       <section class=container>
       <section class=dataForm>
           <article>We'll be taking this one column at a time so we can build your table just the way you need it.</article>
          
           <table>
                <tr>
                <td><p>Enter the title of the column:</p></td>
                <td><input type="text" name="columnName"/></td>
               </tr>
                <tr>
                <td><p>What would be stored in this column?</p></td>
                <td> 
                <select onchange="checkSelectBox(this.value)" name="valueType">
                <c:forEach var="i" items="${dataTypes}">
             		 <option>${i}</option>
              </c:forEach>
              </select></td>
               </tr>
               <tr>
               <td><p>Can this data be null?</p></td>
               <td><input type="checkbox" name="nullableBox"/></td>
               </tr>
                </table>
       </section>
       </section>
  
    <section class=buttons>
        <button name="nextColumn">Next Column</button>
        <button name="done">Done</button>
    </section>
    </form>

    </div>
    
<jsp:include page="_footer.jsp" flush="true" />