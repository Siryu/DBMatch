<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="DBMatch" scope="request" />
<jsp:include page="/WEB-INF/_header.jsp" flush="true" />	 
    <div id="cover" >
    </div>
    
    <div>
        <section class=introsect>
            <img src="${pageContext.request.contextPath}/Assets/Images/dbstock.jpg"/>
            <h1>Welcome to DBMatch!</h1>
            <article id=intro>Our application will perform the job that would normally be handled by a database consultant; 
            It will help less technically minded people develop a database design that will work well for the data they need to store.</article>
        </section>
    </div>
    
    <section class=testimonials>
        <div>
            <section class=test>
                <section class=one>
                <img src="${pageContext.request.contextPath}/Assets/Images/profile1.jpg">
                <article>This brings up a save dialog, which asks him where he wants to save the script. 
                He chooses a location and clicks save. The setup script is generated and sent to his computer, 
                which saves it in his chosen location. <br> <a href="">Read more</a></article>
                <a href="">Kim R.</a>
                </section>
            </section>
              <section class=test>
                  <section class=two>
                <img src="${pageContext.request.contextPath}/Assets/Images/profile3.jpg">
                <article>If I were a real person, I would love this product so much! DBMatch
                makes people's lives easier, grants wings to angels, and causes kisses from the
                heavens to distill down upon the heads of tiny puppies.
                 <br> <a href="">Read more</a></article>
                <a href="">Perry H.</a>
                </section>
            </section>
            <section class=test>
                <section class=three>
                <img src="${pageContext.request.contextPath }/Assets/Images/profile4.jpg">
                <article> He clicks on the export button at the top, which gives him two options 
                - create setup script or connect to server and generate database. 
                He clicks on the second option, since he’s not very good with sql. <br> <a href="">Read more</a></article>
                <a href="">Tom J.</a>
                </section>
            </section>
            <section class=test>
                <section class=four>
                <img src="${pageContext.request.contextPath }/Assets/Images/profile2.jpg">
                <article>He runs the setup script, and it generates the same database that he designed! Extremely happy,
                 he is now able to work with the database, and not have to worry about creating the database script. 
                 <br> <a href="">Read more</a></article>
                <a href="">Dana L.</a>
                </section>
            </section>
            <article id=links><a href="">About Us</a> | <a href="">Testimonials</a> | <a href="">Templates</a></article>
        </div>        
    </section>
<jsp:include page="/WEB-INF/_footer.jsp" flush="true" />