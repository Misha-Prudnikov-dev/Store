<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<title></title>

<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round|Raleway">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>

<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
<link rel="stylesheet" href="menucss.css">

<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/all.css" integrity="sha384-3AB7yXWz4OeoZcPbieVW64vVXEwADiYyAEhwilzWsLw+9FgqpyjjStpPnpBO8o8S" crossorigin="anonymous"> 
<script defer src="https://use.fontawesome.com/releases/v5.0.8/js/all.js" integrity="sha384-SlE991lGASHoBfWbelyBPLsUlwY1GwNDJo3jSJO04KZ33K2bwfV9YBauFfnzvynJ" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>

               <fmt:setLocale value="${sessionScope.local}" />
               <fmt:setBundle basename="localization.local" var="loc" />


              <fmt:message bundle="${loc}" key="local.signIn" var="signIn"/>
              <fmt:message bundle="${loc}" key="local.signOut" var="signOut"/>
              <fmt:message bundle="${loc}" key="local.profile" var="profile"/>
              <fmt:message bundle="${loc}" key="local.orders" var="orders"/>
              <fmt:message bundle="${loc}" key="local.search" var="search"/>
              
              <fmt:message bundle="${loc}" key="local.menu.admin.action" var="actionAdmin"/>
              <fmt:message bundle="${loc}" key="local.menu.admin.addRole" var="addRole"/>
              <fmt:message bundle="${loc}" key="local.menu.admin.addProduct" var="addProduct" />
                            

</head> 
<body>
<nav class="navbar navbar-expand-lg navbar-light">
	<a class="navbar-brand" href="Controller?command=go_to_main"><b>Red Bear`s</b></a>  		
	<button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div id="navbarCollapse" class="collapse navbar-collapse justify-content-start">
		<div class="navbar-nav">
			
			   <c:forEach items="${groupCategory}" var="element">
				   <a href="Controller?command=subcategory&categoryId=${element.id}" class="nav-item nav-link">${element.title}</a>
			   </c:forEach>	
			
			  <c:if test="${userAuthorized!=null}">  
			
			     <c:forEach items="${userAuthorized.roles}" var="role">
			
			         <c:if test="${role.role == 'admin'}">
						<div class="nav-item dropdown">
							<a href="#" class="nav-item nav-link dropdown-toggle" data-toggle="dropdown">${actionAdmin}</a>
							<div class="dropdown-menu">					
								<a href="#" class="dropdown-item">${addRole}</a>
								<a href="#" class="dropdown-item">${addProduct}</a>
			                </div>
			            </div>
	                </c:if> 
	                
	          </c:forEach>     
          </c:if>  
            
        </div>
		<div class="navbar-nav ml-auto action-buttons">
			<div class="navbar-form-wrapper">
				<form class="navbar-form form-inline" action="Controller">
					<div class="input-group search-box">								
						<input type="text" name="searchProduct" id="search" class="form-control" placeholder="${search}">
				        <input type="hidden" name="command" value="search_product" />
						<div class="input-group-append">
							<span class="input-group-text">
                               <button class="search-button" ><i class="material-icons">&#xE8B6;</i></button>
							</span>
						</div>
					</div>
					
                </form>
            </div>
			<a href="Controller?command=favorites" class="nav-item nav-link"><i class="far fa-heart fa-lg"></i></a>
			<a href="Controller?command=go_to_cart" class="nav-item nav-link"><i class="fas fa-shopping-bag fa-lg"></i></a>
			
           <c:if test="${userAuthorized==null}"> 
				<div class="nav-item dropdown">
					 <a href="Controller?command=go_to_sign_in"  class="nav-link dropdown-toggle mr-4 "> ${signIn}</a>
				</div>
		  </c:if>
		  <c:if test="${userAuthorized !=null}">	
			<div class="nav-item dropdown">
				<a href="#" data-toggle="dropdown" class="nav-item nav-link dropdown-toggle user-action"><img src="././img/ac2.png" class="avatar" alt="Avatar">${userAuthorized.name}<b class="caret"></b></a>
				<div class="dropdown-menu">
					<a href="Controller?command=user_profile" class="dropdown-item"><i class="fas fa-user"></i> ${profile}</a>
					<a href="Controller?command=order_history" class="dropdown-item"><i class="fas fa-box"></i> ${orders}</a>
					<div class="divider dropdown-divider"></div>
					<a href="Controller?command=Sign_Out" class="dropdown-item"><i class="fas fa-sign-out-alt"></i> ${signOut}</a>
				</div>
			</div>
		</c:if>	
    </div>
		 
 </div>
	
</nav>

</body>
</html>                            