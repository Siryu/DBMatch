<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Create Columns" scope="request" />
<jsp:include page="_header.jsp" flush="true" />
    <div id="cover" >
    </div>
    
	<div> 
    
       <h2>Sample Data</h2>
       <article id=infohead>Please enter some sample data so we can build your table.</article>
       
       <form method="post" action="${pageContext.request.contextPath}/user/column">
       <section class=container>
       <section class=dataForm>
           <article>We'll be taking this one column at a time so we can build your table just the way you need it.</article>
          
           <table>
                <tr>
                <td><p>Enter the title of your column:</p></td>
                <td><input type="text" name="columnName"/></td>
               </tr>
                <tr>
                <td><p>What would be stored in this column?</p></td>
                <td> 
                <select onchange="checkSelectBox(this.value)" name="valueType">
              <option value="numbers">Numbers</option>
              <option value="numbersAndLetters">Numbers and Letters</option>
           </select></td>
               </tr>
               <tr>
               <td><p>Can this data be null?</p></td>
               <td><input type="checkbox" name="nullableBox"/></td>
               </tr>
                </table>
               
           <table id=nums>
               <tr id=numbox >
                <td><p>Minimum value:</p></td>
                <td><input type="text" name="minValue"/></td>
               </tr>
               <tr id=numbox2 >
                <td><p>Maximum value:</p></td>
                <td><input type="text" name="maxValue"/></td>
               </tr>
            </table>
               
           <table id=alpha>
               <tr id=alphabox style="display: none;">
                <td>Give us an example of what information would go in this column. 
                <br><p id=smallText>(i.e. JaneDoe@mail.com or Patrick Henry)</p></td>
                <td><input type="text" name="sampleText"/></td>
               </tr>
           </table>
       </section>
       </section>
  
    <section class=buttons>
    	<input type="hidden" name="tableName" value="${model}"/>
        <button name="nextColumn">Next Column</button>
        <button name="done">Done</button>
    </section>
    </form>

    </div>
    
<script type="text/javascript">

function checkSelectBox(val){
    var element = document.getElementById('numbox');
    var element2 = document.getElementById('numbox2');
    if(val == 'numbers'){
        element.style.display = 'block';
        element2.style.display = 'block';
    }
    else {
        element.style.display = 'none'; 
        element2.style.display = 'none';
    }
    
    var element = document.getElementById('alphabox');
    if(val == 'numbersAndLetters')
        element.style.display = 'inherit';   
    
    else
        element.style.display = 'none';   
    
}
   
</script>
    
<jsp:include page="_footer.jsp" flush="true" />