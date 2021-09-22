<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
    <!-- Template CSS -->
    <link type="text/css" media="all" href="./css/style404.css" rel="stylesheet" />
    <!-- Responsive CSS -->
    <link type="text/css" media="all" href="./css/respons.css" rel="stylesheet" />
    <!-- Google Fonts -->
    <link href='https://fonts.googleapis.com/css?family=Raleway:300,400,500,600,700,800,900' rel='stylesheet' type='text/css'>
	
	<fmt:setLocale value="${sessionScope.locale}" />
		<c:if test="${empty sessionScope.locale}">
        	<fmt:setLocale value="ru" />
    	</c:if>
	<fmt:setBundle basename="resources.localization.app"/>
	<fmt:message key="page_title_404" var="title"/>
	<fmt:message key="on" var="on"/>
	<fmt:message key="off" var="off"/>
	<fmt:message key="light" var="light"/>
	<fmt:message key="message_404" var="message"/>
	<fmt:message key="home" var="home"/>
	<fmt:message key="contact" var="contact"/>
	
	<title>${title}</title>
</head>
<body>

    <!-- Load page -->
    <div class="animationload">
        <div class="loader">
        </div>
    </div>
    <!-- End load page -->

    <!-- Content Wrapper -->
    <div id="wrapper">
        <div class="container">
            <!-- Switcher -->
            <div class="switcher">
                <input id="sw" type="checkbox" class="switcher-value">
                <label for="sw" class="sw_btn"></label>
                <div class="bg"></div>
                <div class="text"><span class="text-l">${on}</span><span class="text-d">${off}</span><br />${light}</div>
            </div>
            <!-- End Switcher -->

            <!-- Dark version -->
            <div id="dark" class="row text-center">
                <div class="info">
                    <img src="images/404.png" alt="404 error" />
                </div>
            </div>
            <!-- End Dark version -->

            <!-- Light version -->
            <div id="light" class="row text-center">
                <!-- Info -->
                <div class="info">
                    <img src="images/404.gif" alt="404 error" />
                    <h4>${message}</h4>
                   
                    <form action="controller" method ="get">
                    		 <a href="index.jsp" class="btn">${home}</a>
							<input type = "hidden" name = "command" value = "contact_page">
							
							 <button type="submit" class="btn btn-brown">${contact}</button>
					</form>
                   
                </div>
                <!-- end Info -->
            </div>
            <!-- End Light version -->

        </div>
        <!-- end container -->
    </div>
    <!-- end Content Wrapper -->
    
    <!-- Scripts -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="./js/modernizr.custom.js" type="text/javascript"></script>
    <script src="./js/jquery.nicescroll.min.js" type="text/javascript"></script>
    <script src="./js/scripts.js" type="text/javascript"></script>
    
</body>
</html>