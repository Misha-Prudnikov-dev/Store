<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Red Bear`s ${createAccount}</title>

<link rel="stylesheet" href="././SignIn.css">



               <fmt:setLocale value="${sessionScope.local}" />
               <fmt:setBundle basename="localization.local" var="loc" />

              <fmt:message bundle="${loc}" key="local.userInfo.name" var="name" />
              <fmt:message bundle="${loc}" key="local.userInfo.surname" var="surname"/>
              <fmt:message bundle="${loc}" key="local.userInfo.phone" var="phone"/>
              <fmt:message bundle="${loc}" key="local.userInfo.email" var="email"/>
              <fmt:message bundle="${loc}" key="local.userInfo.password" var="password"/>
              <fmt:message bundle="${loc}" key="local.userInfo.dateOfBirth" var="dateOfBirth"/>
            	
              <fmt:message bundle="${loc}" key="local.userInfo.gender" var="gender"/>
              <fmt:message bundle="${loc}" key="local.userInfo.gender.male" var="male"/>
              <fmt:message bundle="${loc}" key="local.userInfo.gender.female" var="female"/>
              <fmt:message bundle="${loc}" key="local.userInfo.gender.none" var="none"/>
              
              <fmt:message bundle="${loc}" key="local.registration" var="createAccount"/>
              <fmt:message bundle="${loc}" key="local.button.registration" var="create"/>
              <fmt:message bundle="${loc}" key="local.registration.haveAccount" var="haveAccount"/>
                            
              <fmt:message bundle="${loc}" key="local.registration.fail.name" var="failName"/>
              <fmt:message bundle="${loc}" key="local.registration.fail.surname" var="failSurname"/>
              <fmt:message bundle="${loc}" key="local.registration.fail.password" var="failPassword"/>
              <fmt:message bundle="${loc}" key="local.registration.fail.duplicate" var="failDuplicate"/>              
              <fmt:message bundle="${loc}" key="local.registration.fail.date" var="failDate"/>
              
              <fmt:message bundle="${loc}" key="local.signIn" var="signIn"/>
              


</head>
<body>


   <main>
   
       <jsp:include page="menu.jsp"/>
   
   </main>


<div class="signup-form">
    <form action="Controller" method="post" class="form-horizontal">
        <input type="hidden" name="command" value="Registration" />
      	<div class="row">
        	<div class="col-8 offset-4">
				<h2>${createAccount}</h2>
			</div>	
      	</div>			
        <div class="form-group row">
			<label class="col-form-label col-4">${name}</label>
			<div class="col-8">
                <input type="text" class="form-control" name="name" >
                 <c:if test="${param.registration=='fail'}">
			       <small class="form-text text-danger">${failName}</small>
			    </c:if>      	
                
            </div>        	
        </div>
        <div class="form-group row">
			<label class="col-form-label col-4">${surname}</label>
			<div class="col-8">
                <input type="text" class="form-control" name="surname" >
                 <c:if test="${param.registration=='fail'}">
			       <small class="form-text text-danger">${failSurname}</small>
			    </c:if>      	
                
            </div>        	
        </div>		
		<div class="form-group row">
			<label class="col-form-label col-4">${email}</label>
			<div class="col-8">
                <input type="email" class="form-control" name="email" >
            </div>        	
        </div>
        <div class="form-group row">
			<label class="col-form-label col-4">${phone}</label>
			<div class="col-8">
                <input type="text" class="form-control" name="phone" >
            </div>        	
        </div>		
		<div class="form-group row">
			<label class="col-form-label col-4">${password}</label>
			<div class="col-8">
                <input type="password" class="form-control" name="password" >
                 <c:if test="${param.registration=='fail'}">
			       <small class="form-text text-danger">${failPassword}</small>
			    </c:if>
                 <c:if test="${param.registration=='duplicate'}">
			       <small class="form-text text-danger">${failDuplicate}</small>
			    </c:if>      	
			          	
            </div>        	
        </div>
		<div class="form-group row">
		  <label for="example-date-input" class="col-4 col-form-label">${dateOfBirth}</label>
		  <div class="col-8">
			<input class="form-control" type="date" name="dateOfBirth"  id="example-date-input">
     	
			 <c:if test="${param.registration=='wrong_date'}">
			       <small class="form-text text-danger">${failDate}</small>
			 </c:if>      	
			
		  </div>
		</div>
		<div class="form-check form-check-inline">
		  <label class="form-check-label">
			<input class="form-check-input" type="radio" name="gender" id="inlineRadio1" value="male"> ${male}
		  </label>
		</div>
		<div class="form-check form-check-inline">
		  <label class="form-check-label">
			<input class="form-check-input" type="radio" name="gender" id="inlineRadio2" value="female"> ${female}
		  </label>
		</div>
		<div class="form-check form-check-inline">
		  <label class="form-check-label">
			<input class="form-check-input" type="radio" name="gender" id="inlineRadio2" value="none" checked> ${none}
		  </label>
		</div>		
				
		<div class="form-group row">
			<div class="col-8 offset-4">
				<input type="submit" name="Registration" id="btn" class="btn btn-primary btn-lg" value="${create}">
			</div>  
		</div>		      
    </form>
	<div class="text-center">${haveAccount} <a href="Controller?command=go_to_sign_in">${signIn}</a></div>
</div>


<footer>
    <jsp:include page="footer.jsp"/>
</footer>


</body>
</html>