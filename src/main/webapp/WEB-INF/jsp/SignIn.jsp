<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
        <meta http-equiv="Content-Type" 
              content="text/html; charset=UTF-8">

        <script src="http://code.jquery.com/jquery-2.2.4.js" 
                type="text/javascript"></script>
        <script src="././app-ajax.js" type="text/javascript"></script>

<link rel="stylesheet" href="././SignIn.css">


<title>Red Bear`s ${signIn}</title>

               <fmt:setLocale value="${sessionScope.local}" />
               <fmt:setBundle basename="localization.local" var="loc" />
               
               <fmt:message bundle="${loc}" key="local.signIn" var="signIn"/>               
              <fmt:message bundle="${loc}" key="local.userInfo.email" var="email"/>
              <fmt:message bundle="${loc}" key="local.userInfo.password" var="password"/>              
              <fmt:message bundle="${loc}" key="local.registration.fail.password" var="failPassword"/>              
              <fmt:message bundle="${loc}" key="local.signIn.fail.notFound" var="userNotFound"/>
              <fmt:message bundle="${loc}" key="local.signIn.noAccountMessage" var="noAccountMessage"/>
              <fmt:message bundle="${loc}" key="local.signIn.createAccount" var="createAccount"/>
                            
</head>
<body>


   <main>
   
       <jsp:include page="menu.jsp"/>
   
   </main>
 
	  <div class="signup-form">
	    <form action="Controller" method="post" class="form-horizontal">
	        <input type="hidden" name="command" value="Sign_In"/>
	      	<div class="row">
	        	<div class="col-8 offset-4">
					<h2>${signIn}</h2>
				</div>	
	      	</div>			
	
			<div class="form-group row">
				<label class="col-form-label col-4">${email}</label>
				<div class="col-8">
	                <input type="email" class="form-control" id="email" name="email"  >
	            </div>        	
	        </div>
			<div class="form-group row">
				<label class="col-form-label col-4">${password}</label>
				<div class="col-8">
	                <input type="password" class="form-control" name="password"  >
	                <c:if test="${param.signIn=='fail'}">
			           <small class="form-text text-danger">${failPassword}</small>
				   </c:if>      	
	                <c:if test="${param.signIn=='not_found'}">
			           <small class="form-text text-danger">${userNotFound}</small>
				   </c:if>      	
	                
	            </div>  
	        </div>
	
			<div class="form-group row">
				<div class="col-8 offset-4">
					<button type="submit" class="btn btn-primary btn-lg" id="btn" >${signIn}</button>
				</div>  
			</div>		      
	  </form>
	
		<div class="text-center">${noAccountMessage} <a href="Controller?command=go_to_registration">${createAccount}</a></div>
	</div>

<footer>
	<jsp:include page="footer.jsp"/>
</footer>


</body>
</html>