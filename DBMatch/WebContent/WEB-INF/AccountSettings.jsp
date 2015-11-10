<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="_header.jsp" flush="true" />

<form method="post" action="${ pageContext.request.contextPath }/user/account_Settings">
 <div>
        <section class=accountBox>
            
            <h2>Account Settings</h2>
            
            <section class=accntBox>
          
            <article>Current Email:</article>
            <input type="email" value="${ model.email }"/>
            
            <article>New Email:</article>
            <input type=email name="newEmail"/>
            
            <article>Old Password:</article>
            <input type=password name="oldPassword" />
            
            <article>New Password:</article>
            <input type=password name="newPassword" />
            
            <button>submit</button>
                
            </section>
        </section>
    </div>
</form>
    

 <jsp:include page="_footer.jsp" flush="true" /> 