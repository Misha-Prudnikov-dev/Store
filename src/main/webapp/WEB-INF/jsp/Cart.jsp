<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
  <link rel="stylesheet" href="././Cart.css">
  
  
<title>Red Bear`s ${cart}</title>

               
              <fmt:setLocale value="${sessionScope.local}" />
              <fmt:setBundle basename="localization.local" var="loc" />

              <fmt:message bundle="${loc}" key="local.cart.empty" var="cartEmptyMessage" />
              
              <fmt:message bundle="${loc}" key="local.productInfo.color" var="color"/>
              <fmt:message bundle="${loc}" key="local.orderInfo.size" var="size"/>
              
              <fmt:message bundle="${loc}" key="local.product.price" var="price"/>
              <fmt:message bundle="${loc}" key="local.product.quantity" var="quantity"/>
              <fmt:message bundle="${loc}" key="local.cart.priceUnitProduct" var="priceUnitProduct"/>

              <fmt:message bundle="${loc}" key="local.cart.producQuantityNoStock" var="producQuantityNoStock"/>
              
              <fmt:message bundle="${loc}" key="local.cart" var="cart"/>
              <fmt:message bundle="${loc}" key="local.cart.checkout.success" var="checkoutSuccess"/>
              
              <fmt:message bundle="${loc}" key="local.payment" var="payment"/>
              <fmt:message bundle="${loc}" key="local.payment.cash" var="cash"/>
              <fmt:message bundle="${loc}" key="local.payment.card" var="card"/>

              <fmt:message bundle="${loc}" key="local.delivery" var="deliveryDate"/>
              
              <fmt:message bundle="${loc}" key="local.address.city" var="city"/>
              <fmt:message bundle="${loc}" key="local.address.street" var="street"/>
              <fmt:message bundle="${loc}" key="local.address.numberHouse" var="numberHouse"/>
                            
              <fmt:message bundle="${loc}" key="local.cart.checkout" var="checkout"/>
              <fmt:message bundle="${loc}" key="local.cart.total" var="totalSum"/>
              <fmt:message bundle="${loc}" key="local.field.empty" var="fieldEmpty"/>


</head>
<body>


   
    <main>
   
   <jsp:include page="menu.jsp"/>
   
   </main>
 
     <c:if test="${param.checkout=='success'}">
        <center><h4>${checkoutSuccess}</h4></center>
     </c:if>
 
     <c:if test="${ empty groupProductCart}">
         <center><h4> ${cartEmptyMessage} </h4></center>
     </c:if>
     
    <c:if test="${ ! empty groupProductCart}">
 
		 <div class="container px-4 py-5 mx-auto">
		    <div class="row d-flex justify-content-center">
		        <div class="col-5">
		            <h4 class="heading">${cart}</h4>
		        </div>
		        <div class="col-7">
		            <div class="row text-right">
		                <div class="col-4">
		                    <h6 class="mt-2">${quantity}</h6>
		                </div>
		                <div class="col-4">
		                    <h6 class="mt-2">${priceUnitProduct}</h6>
		                </div>
		                <div class="col-4">
		                    <h6 class="mt-2">${price}</h6>
		                </div>
						
		            </div>
		        </div>
		    </div>
		    <c:set var="total" value="${0}"/>
		    <c:forEach items="${groupProductCart}"  var="orderDetail">
			    <div class="row d-flex justify-content-center border-top">
			        <div class="col-5">
			            <div class="row d-flex">
			                <div class="book"> <img src="././img/${orderDetail.product.productImage.image}" class="book-img"> </div>
			                <div class="my-auto flex-column d-flex pad-left">
			                    <h6 class="mob-text">${orderDetail.product.title}</h6>
			                    <p class="mob-text">${size}: ${orderDetail.productSize}</p>
			                    <p class="mob-text">${color}: ${orderDetail.product.color}</p>
			                    <c:set var="total" value="${total+orderDetail.product.price*orderDetail.quantity}"/>
			                    <c:if test="${orderDetail.product.id==param.idProduct && orderDetail.productSize==param.productSize}">
			                        <small class="form-text text-danger">${producQuantityNoStock}</small>
			                    </c:if>
			                </div>
			            </div>
			        </div>
			        <div class="my-auto col-7">
			            <div class="row text-right">
			                <div class="col-4">
			                    <p class="mob-text">${orderDetail.quantity}</p>
			                </div>
			                <div class="col-4">
			                  <div class="row d-flex justify-content-end px-3">
							     <div class="number">
			                        $<fmt:formatNumber value="${orderDetail.product.price}" pattern=".00"/> 				
			                    </div>                    
			                 </div>
			                </div>
			                <div class="col-4">
			                    <h6 class="mob-text">$<fmt:formatNumber value="${orderDetail.product.price*orderDetail.quantity}" pattern=".00"/></h6>
			                </div>
						    <div class="col row text-right">
							   <a href="Controller?command=delete_product_in_cart&productId=${orderDetail.product.id}&productSize=${orderDetail.productSize}"><i class="fas fa-times"> </i></a> 
					       </div>
			            </div>		      	
			        </div> 
			    </div>
		   </c:forEach>
		
	  <form action="Controller" method="post">
		   <input type="hidden" name="command" value="Checkout" />
			
		    <div class="row justify-content-center">
		        <div class="col-lg-12">
		            <div class="card">
		                <div class="row">
		                    <div class="col-lg-3 radio-group">
			                    ${payment}
			                    <div class="row px-2">
							       <div class="form-check col-md-6">
								      <label class="form-check-label">${cash}
									    <input class="form-check-input" type="radio" name="typePayment" id="gridRadios2" value="cash">
						              </label>
								  </div>
							   </div>
								<div class="row px-2">
								  <div class="form-check col-md-6 ">
								     <label class="form-check-label">${card}
									    <input class="form-check-input" type="radio" name="typePayment" id="gridRadios3" value="card" >
						             </label>
						          </div>
							  </div>
		                    </div>
		                    <div class="col-lg-5">
		                        <div class="row px-2">
		                            <div class="form-group col-md-6"> <label class="form-control-label">${city}</label> <input type="text" id="cname" name="city"> </div>
		                            <div class="form-group col-md-6"> <label class="form-control-label">${street}</label> <input type="text" id="cnum" name="street"> </div>
		                        </div>
		                        <div class="row px-2">
		                            <div class="form-group col-md-6"> <label class="form-control-label">${numberHouse}</label> <input type="text" id="exp" name="numberHouse"> </div>
		                        </div>
		                    </div>
		                    <div class="col-lg-4 mt-2">
		                        <div class="row d-flex justify-content-between px-4">
		                            <p class="mb-1 text-left">${deliveryDate}</p>
		                            <h6 class="mb-1 text-right">${dateDelivery}</h6>
		                        </div>
		                        <div class="row d-flex justify-content-between px-4" id="tax">
		                            <p class="mb-1 text-left">${totalSum} </p>
		                            <h6 class="mb-1 text-right">$<fmt:formatNumber value="${total}" pattern=".00"/></h6>
		                        </div> 
		                        <button type="submit" class="btn-block btn-blue"> <span> <span id="checkout">${checkout}</span> <span id="check-amt"></span> </span> </button>
		                      
		                       <c:if test="${param.checkout=='fail'}">
		                            <small class="form-text text-danger">${fieldEmpty}</small>
		                        </c:if>
		                    </div>
		                </div>
		            </div>
		        </div>
		     </div>
		</form> 
	  </div>
  </c:if>
  
  <footer>
      <jsp:include page="footer.jsp"/>
  </footer>
  
</body>
</html>