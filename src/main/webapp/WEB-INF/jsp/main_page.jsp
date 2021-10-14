<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
      <link rel="stylesheet" type="text/css" href="././GroupProduct.css"/>

		<fmt:setLocale value="${sessionScope.local}" />
		<fmt:setBundle basename="localization.local" var="loc" />
		
		<fmt:message bundle="${loc}" key="local.message" var="message" />
		<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
		<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
		<fmt:message bundle="${loc}" key="local.locbutton.name.by" var="by_button" />
		
		<fmt:message bundle="${loc}" key="local.productCart.addFavorites" var="addFavorites" />
		<fmt:message bundle="${loc}" key="local.productCard.addCart" var="addCart" />
		<fmt:message bundle="${loc}" key="local.cookie.groupProduct" var="cookieGroupProduct" />
		


</head>
<body>

   <main>
   
   <jsp:include page="menu.jsp"/>
   
   </main>

 <section>

  <!--Carousel Wrapper-->
  <div id="carousel-example-2" class="carousel slide carousel-fade" data-ride="carousel">
    <!--Indicators-->
    <ol class="carousel-indicators">
      <li data-target="#carousel-example-2" data-slide-to="0" class="active"></li>
      <li data-target="#carousel-example-2" data-slide-to="1"></li>
      <li data-target="#carousel-example-2" data-slide-to="2"></li>
    </ol>
    <!--/Indicators-->
    <!--Slides-->
    <div class="carousel-inner" role="listbox">
      <div class="carousel-item active">
        <div class="view">
          <img class="d-block w-100" src="././img/banner/b1.jpg"
            alt="First slide">
          <a href="#!">
            <div class="mask rgba-black-light"></div>
          </a>
        </div>
        <div class="carousel-caption">
          <h3 class="h3-responsive">First shop item</h3>
          <p>First text</p>
        </div>
      </div>
      <div class="carousel-item">
        <!--Mask color-->
        <div class="view">
          <img class="d-block w-100" src="././img/banner/b2.jpg"
            alt="Second slide">
          <a href="#!">
            <div class="mask rgba-black-light"></div>
          </a>
        </div>
        <div class="carousel-caption">
          <h3 class="h3-responsive">Second shop item</h3>
          <p>Secondary text</p>
        </div>
      </div>
      <div class="carousel-item">
        <!--Mask color-->
        <div class="view">
          <img class="d-block w-100" src="././img/banner/b3.jpg"
            alt="Third slide">
          <a href="#!">
            <div class="mask rgba-black-light"></div>
          </a>
        </div>
        <div class="carousel-caption">
          <h3 class="h3-responsive">Third shop item</h3>
          <p>Third text</p>
        </div>
      </div>
    </div>
    <!--/Slides-->
    <!--Controls-->
    <a class="carousel-control-prev" href="#carousel-example-2" role="button" data-slide="prev">
      <span class="carousel-control-prev-icon" aria-hidden="true"></span>
      <span class="sr-only">Previous</span>
    </a>
    <a class="carousel-control-next" href="#carousel-example-2" role="button" data-slide="next">
      <span class="carousel-control-next-icon" aria-hidden="true"></span>
      <span class="sr-only">Next</span>
    </a>
    <!--/Controls-->
  </div>
  <!--/Carousel Wrapper-->

</section>
 
 <div class="container cont">
<h4>${cookieGroupProduct} </h4>
	<div class="row">

		<c:forEach items="${productCookie}" var="productCook" varStatus="status"  >
		
					<div class="col-md-2 col-sm-6 ">
						<div class="product-grid6">
							<div class="product-image6">
								<a href="Controller?command=go_to_product_info&productId=${productCook.id}">
									<img class="pic-1" src="././img/${productCook.productImage.image}">
								</a>
								
							</div>
							<div class="product-content">
								<h3 class="title"><a href="#">${productCook.title}</a></h3>
								<div class="price">$<fmt:formatNumber value="${productCook.price}" pattern=".00"/></div>
							</div>
							<ul class="social">
								<li><a href="Controller?command=add_product_favorites&productId=${productCook.id}" data-tip="${addFavorites}"><i class="fa fa-heart"></i></a></li>
								<li><a href="Controller?command=add_to_cart&productId=${productCook.id}&quantityProduct=${1}" data-tip="${addCart}"><i class="fa fa-shopping-bag"></i></a></li>
							<div class="rating-mini">
							     <span <c:if test="${productCook.rating>=1}"> class="active"</c:if>></span>	
							     <span <c:if test="${productCook.rating>=2}"> class="active"</c:if>></span>	
							     <span <c:if test="${productCook.rating>=3}"> class="active"</c:if>></span>	
							     <span <c:if test="${productCook.rating>=4}"> class="active"</c:if>></span>	
							     <span <c:if test="${productCook.rating>=5}"> class="active"</c:if>></span>	
							</div>
							</ul>
						</div>
					</div>					
			
		</c:forEach>		
		
	</div>
</div>
 
  
   
   <footer>
   
   <jsp:include page="footer.jsp"/>
   
   </footer>
   

</body>
</html>