<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Red Bear`s ${orderHistory}</title>
      <link rel="stylesheet" type="text/css" href="././Cart.css"/>
      
      
              <fmt:setLocale value="${sessionScope.local}" />
              <fmt:setBundle basename="localization.local" var="loc" />

              <fmt:message bundle="${loc}" key="local.orderHistory" var="orderHistory" />
              <fmt:message bundle="${loc}" key="local.orderHistory.order" var="orderNumber"/>
              
              <fmt:message bundle="${loc}" key="local.orderHistory.checkoutDate" var="checkoutDate"/>
              <fmt:message bundle="${loc}" key="local.orderHistory.deliveryDate" var="deliveryDate"/>
              
              <fmt:message bundle="${loc}" key="local.orderHistory.status" var="status"/>
              <fmt:message bundle="${loc}" key="local.orderHistory.status.order" var="statusOrder"/>
              <fmt:message bundle="${loc}" key="local.orderHistory.status.bought" var="statusBought"/>
              
              <fmt:message bundle="${loc}" key="local.orderHistory.orderDetail" var="orderDetail"/>
      

</head>
<body>


   <main>
   
   <jsp:include page="menu.jsp"/>
   
   </main>


 <div class="container px-4 py-5 mx-auto">
	    <div class="row d-flex justify-content-center">
	        <div class="col-5">
	            <h4 class="heading">${orderHistory}</h4>
	        </div>
	    </div>

		<c:forEach items="${groupOrder}" var="order">
	
			    <div class="row justify-content-center">
			        <div class="col-lg-12">
			            <div class="card">
			                <div class="row">
			                    <div class="col-lg-4 ">
			
			                        <div class="row d-flex justify-content-between px-4">
			                            <p class="mb-1 text-left">${orderNumber}</p>
			                            <h6 class="mb-1 text-right">№${order.id}</h6>
			                        </div>
									<div class="row d-flex justify-content-between px-4">
			                            <p class="mb-1 text-left">${checkoutDate}</p>
			                            <h6 class="mb-1 text-right">${order.dateOrder}</h6>
			                        </div>
									<div class="row d-flex justify-content-between px-4">
			                            <p class="mb-1 text-left">${deliveryDate}</p>
			                            <h6 class="mb-1 text-right">${order.delivery.dateDelivery}</h6>
			                        </div>
			
			                    </div>
			                    <div class="col-lg-4 mt-2">
									<div class="row d-flex justify-content-between px-4">
			
			                        </div>
			                    </div>
			                    <div class="col-lg-4 mt-2">
			                        <div class="row d-flex justify-content-between px-4">
			                            <p class="mb-1 text-left">${status}</p>
			                            <c:if test="${order.status=='ORDER'}">
			                               <h6 class="mb-1 text-right">${statusOrder}</h6>
			                            </c:if>
			                            <c:if test="${order.status=='BOUGHT'}">
			                               <h6 class="mb-1 text-right">${statusBought}</h6>
			                           </c:if>
			                            
			                       </div>
			                        <div class="row d-flex justify-content-between px-4" id="tax"></div>
				                      <form action="Controller" method="post">
				                        <input type="hidden" name="command" value="go_to_order_info"/>
				                        <input type="hidden" name="orderId" value="${order.id}">
										<button type="submit" class="btn-block btn-blue" > <span> <span id="checkout">${orderDetail}</span> <span id="check-amt"></span> </span> </button>
				                    </form>
			                    </div>
			                </div>
			            </div>
			        </div>
			    </div>
 		</c:forEach>   
	</div>

	<footer>
	  <jsp:include page="footer.jsp"/>
	</footer>

</body>
</html>