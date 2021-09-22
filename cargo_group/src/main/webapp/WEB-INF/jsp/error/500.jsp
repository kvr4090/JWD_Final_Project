<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
  <link rel="stylesheet" href="css/style500.css">
  
	<fmt:setLocale value="${sessionScope.locale}" />
		<c:if test="${empty sessionScope.locale}">
        	<fmt:setLocale value="ru" />
    	</c:if>
	<fmt:setBundle basename="resources.localization.app"/>
	<fmt:message key="page_title_500" var="title"/>
	<fmt:message key="message_500_1" var="msg1"/>
	<fmt:message key="message_500_2" var="msg2"/>
	<fmt:message key="message_500_3" var="msg3"/>
	<fmt:message key="home" var="home"/>
	
	<title>${title}</title>
</head>
<body>
<!-- partial:index.partial.html -->
<div class="full-screen">
      <div class='container'>
        <span class="error-num">5</span>
        <div class='eye'></div>
        <div class='eye'></div>

        <p class="sub-text">${msg1}<span class="italic"> ${msg2} </span>${msg3}</p>
        <a href="index.jsp">${home}</a>
      </div>
    </div>
<!-- partial -->
  <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js'></script><script  src="js/script500.js"></script>

</body>
</html>
  