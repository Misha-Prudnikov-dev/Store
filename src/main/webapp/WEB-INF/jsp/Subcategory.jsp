<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Subcategory</title>

      <link rel="stylesheet" type="text/css" href="././GroupProduct.css"/>

</head>
<body>

   <main>
   
   <jsp:include page="menu.jsp"/>
   
   </main>
      
<div class="container cont">
    <h3 class="h3"> </h3>
    <div class="row">
   
   <c:forEach items="${groupSubcategory}" var="subcategory">
        <div class="col-md-3 col-sm-6">
            <div class="product-grid6">
                <div class="product-image6">
                    <a href="Controller?command=group_product_by_subcategory&subcategoryId=${subcategory.id}">
                        <img class="pic-1" src="././img/subcategory/${subcategory.image}">
                    </a>
                </div>
                <div class="product-content ">
                    <h3 class="title"><a href="Controller?command=group_product_by_subcategory">${subcategory.title}</a></h3>
                </div>
                <div class="social">
                    <h3 class="title"><a href="Controller?command=group_product_by_subcategory">${subcategory.title}</a></h3>
                </div>
				
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