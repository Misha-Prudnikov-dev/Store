<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
      <link rel="stylesheet" type="text/css" href="././footer.css"/>
    
		<fmt:setLocale value="${sessionScope.local}" />
		<fmt:setBundle basename="localization.local" var="loc" />      
		<fmt:message bundle="${loc}" key="local.message" var="message" />
		<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
		<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />

		<fmt:message bundle="${loc}" key="local.footer.aboutUs" var="aboutUs" />
		<fmt:message bundle="${loc}" key="local.footer.support" var="support" />
		<fmt:message bundle="${loc}" key="local.footer.contacts" var="contacts" />
		<fmt:message bundle="${loc}" key="local.footer.deliveryAndPayment" var="deliveryAndPayment" />
		<fmt:message bundle="${loc}" key="local.footer.help" var="help" />
		<fmt:message bundle="${loc}" key="local.footer.privacy" var="privacyPolicy" />
		<fmt:message bundle="${loc}" key="local.footer.community" var="community" />
		<fmt:message bundle="${loc}" key="local.footer.faq" var="faq" />
		<fmt:message bundle="${loc}" key="local.titlePage" var="titlePage" />
 
</head>
<body>
<footer class="footer_area section_padding_130_0">
      <div class="container"><hr>
        <div class="row">

          <div class="col-12 col-sm-6 col-lg-4">
            <div class="single-footer-widget section_padding_0_130">

              <div class="footer-logo mb-3"></div>
              <p>${titlePage}</p>

              <div class="copywrite-text mb-5">
                   <a class="ml-1" href="Controller?command=change_local&local=en">EN </a>
                    <a class="ml-1" href="Controller?command=change_local&local=ru">RU </a>
                    <a class="ml-1" href="Controller?command=change_local&local=by">BY</a>
                </p>
              </div>

              <div class="footer_social_area">
                 <a href="#" data-toggle="tooltip" data-placement="top" title="" data-original-title="Facebook">
                    <i class="fab fa-facebook-f"></i>
                </a>
                <a href="#" data-toggle="tooltip" data-placement="top" title="" data-original-title="Pinterest">
                   <i class="fab fa-pinterest"></i>
               </a>
               <a href="#" data-toggle="tooltip" data-placement="top" title="" data-original-title="instagram">
                  <i class="fab fa-instagram"></i>
              </a>
              <a href="#" data-toggle="tooltip" data-placement="top" title="" data-original-title="Twitter">
                 <i class="fab fa-twitter"></i>
              </a>
            </div>
            </div>
          </div>
          
          <div class="col-12 col-sm-6 col-lg">
            <div class="single-footer-widget section_padding_0_130">
              <h5 class="widget-title">${aboutUs}</h5>

              <div class="footer_menu">
                <ul>
                  <li><a href="#">${aboutUs}</a></li>
                  <li><a href="#">${community}</a></li>
                  <li><a href="#">${deliveryAndPayment}</a></li>                  
                </ul>
                
              </div>
            </div>
          </div>

          <div class="col-12 col-sm-6 col-lg">
            <div class="single-footer-widget section_padding_0_130">
              <h5 class="widget-title">${support}</h5>
              
              <div class="footer_menu">
                <ul>
                  <li><a href="#">${faq}</a></li>
                  <li><a href="#">${support}</a></li>
                  <li><a href="#">${privacyPolicy}</a></li>
                </ul>
              </div>
            </div>
          </div>

          <div class="col-12 col-sm-6 col-lg">
            <div class="single-footer-widget section_padding_0_130">
              <h5 class="widget-title">${contacts}</h5>
              <div class="footer_menu">
                <ul>
                  <li><a href="#"></a></li>
                </ul>
              </div>              
            </div>
          </div>
        </div>
      </div>
    </footer>

</body>
</html>