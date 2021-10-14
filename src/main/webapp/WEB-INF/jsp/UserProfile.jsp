<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Red Bear`s</title>

      <link rel="stylesheet" type="text/css" href="././UserProfile.css"/>
      <link rel="stylesheet" type="text/css" href="././SignIn.css"/>
 
 
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
              <fmt:message bundle="${loc}" key="local.userInfo.personal" var="personal"/>
              <fmt:message bundle="${loc}" key="local.userInfo.personal.save" var="save"/>
              <fmt:message bundle="${loc}" key="local.registration.fail.date" var="failDate"/>
               <fmt:message bundle="${loc}" key="local.registration.fail.name" var="failName"/>
              <fmt:message bundle="${loc}" key="local.registration.fail.surname" var="failSurname"/>
 
      


</head>
<body>


   <main>
   
   <jsp:include page="menu.jsp"/>
   
   </main>

<div class="container rounded bg-white mt-5 mb-5">
    <div class="row">
        <div class="col-md-3 border-right">
            <div class="d-flex flex-column align-items-center text-center p-3 py-5">
               <img class="rounded-0 mt-5" src="././img/account.png">
                 <span class="font-weight-bold">${userInfo.name}</span>
                 <span class="text-black-50">${userInfo.email}</span>
           </div>
        </div>
        <div class="col-md-5 border-right">
            <div class="p-3 py-5">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h4 class="text-right">${personal}</h4>
                </div>
                <form action="Controller" method="post">
                        <input type="hidden" name="command" value="Change_User_Info"/>
	                <div class="row mt-2">
	                    <div class="col-md-6"><label class="labels">${name}</label>
	                       <input type="text" class="form-control" name="name" value="${userInfo.name}">
			                 <c:if test="${param.change=='fail'}">
						       <small class="form-text text-danger">${failName}</small>
						    </c:if>      	
	                    </div>
	                    <div class="col-md-6"><label class="labels">${surname}</label>
	                       <input type="text" class="form-control" name="surname" value="${userInfo.surname}">
			                 <c:if test="${param.change=='fail'}">
						       <small class="form-text text-danger">${failSurname}</small>
						    </c:if>      	
	                    </div>
	                </div>
	                <div class="row mt-3">
	                    <div class="col-md-12"><label class="labels">${phone}</label><input type="text" class="form-control" name="phone" value="${userInfo.phone}"></div>
	                    <div class="col-md-12"><label class="labels">${email}</label><input type="email" class="form-control" name="email" value="${userInfo.email}"></div>
	                    <div class="col-md-12"><label class="labels">${dateOfBirth}</label>
	                       <input class="form-control" type="date" name="dateOfBirth"value="${userInfo.dateOfBirth}"  id="example-date-input">
	                     	<c:if test="${param.change=='wrong_date'}">
						       <small class="form-text text-danger">${failDate}</small>
						    </c:if>      	
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
	                   
	                </div>
	
	                <div class="mt-5 text-center">
	                   <button class="btn btn-primary " id="profile-button" type="submit">${save}</button>
	               </div>
            </form>
         </div>
     </div>
    </div>
</div>
		
	<footer>
	    <jsp:include page="footer.jsp"/>
	</footer>											  

</body>
</html>