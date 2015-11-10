<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Register" scope="request" />
<c:set var="script" value="registerScript" scope="request" />
<jsp:include page="_header.jsp" flush="true" />	 
    <div id="cover" >
    </div>
    
    <div>
        <section class=regbox>
            <h2>Registration</h2>
        
        <form method="post" action="${pageContext.request.contextPath }/register">
            <section class=dataForm>
            <table>
                <tr>
                <td class="tall">Email:</td>
                <td>
                	<input class="tall" type="email" name="username" onblur="checkEmail()" id="email"></input>
               		<div class="register" id="emailError"></div>
                </td>
                </tr>
                 <tr>
                <td>Password:</td>
                <td><input type="password" name="password" onblur=""></input></td>
                </tr>
                 <tr>
                <td>Verify password:</td>
                <td><input type="password" name="passwordVerification"></input></td>
                </tr>
            </table>

            <button id=regsubmit type="submit">Submit</button>

        </section>
        </form>
        
        </section>
    </div>
<jsp:include page="_footer.jsp" flush="true" />   