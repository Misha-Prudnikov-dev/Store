<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Red Bear`s ${manufacturer.title}</title>
      <link rel="stylesheet" type="text/css" href="././GroupProduct.css"/>
      
        <fmt:setLocale value="${sessionScope.local}" />
		<fmt:setBundle basename="localization.local" var="loc" />
		
		<fmt:message bundle="${loc}" key="local.productCart.addFavorites" var="addFavorites" />
		<fmt:message bundle="${loc}" key="local.productCard.addCart" var="addCart" />
      
</head>
<body>

   <main>
   
   <jsp:include page="menu.jsp"/>
   
   </main>

<div class="container mt-5">

  
  <section class="dark-grey-text text-center">
    
		    <h3 class="font-weight-bold mb-4 pb-2">${manufacturer.title}</h3>
		    <p class="grey-text w-responsive mx-auto mb-5">${manufacturer.description}</p>
     
			<div class="container cont">
			    <div class="row">
			    
			     <c:forEach items="${groupProductManufacturer}" var="product">
			        <div class="col-md-3 col-sm-6">
			            <div class="product-grid6">
			                <div class="product-image6">
			                    <a href="Controller?command=go_to_product_info&productId=${product.id}">
			                        <img class="pic-1" src="././img/${product.productImage.image}">
			                    </a>
			                </div>
			                <div class="product-content">
			                    <h3 class="title"><a href="Controller?command=go_to_product_info&productId=${product.id}">${product.title}</a></h3>
			                    <div class="price">$<fmt:formatNumber value="${product.price}" pattern=".00"/>
			                    </div>
			                </div>
			                <ul class="social">
			                    <li><a href="Controller?command=add_product_favorites&productId=${product.id}" data-tip="${addFavorites}"><i class="fa fa-heart"></i></a></li>
			                    <li><a href="Controller?command=add_to_cart&productId=${product.id}&quantityProduct=${1}" data-tip="${addCart}"><i class="fa fa-shopping-bag"></i></a></li>
								<div class="rating-mini">
								     <span <c:if test="${product.rating>=1}"> class="active"</c:if>></span>	
								     <span <c:if test="${product.rating>=2}"> class="active"</c:if>></span>	
								     <span <c:if test="${product.rating>=3}"> class="active"</c:if>></span>	
								     <span <c:if test="${product.rating>=4}"> class="active"</c:if>></span>	
								     <span <c:if test="${product.rating>=5}"> class="active"</c:if>></span>	
								</div>
			                </ul>
			            </div>
			        </div>
				</c:forEach>
					
			    </div>
			</div>

  </section>

</div>

<footer>

   <jsp:include page="footer.jsp"/>

</footer>

</body>
</html>