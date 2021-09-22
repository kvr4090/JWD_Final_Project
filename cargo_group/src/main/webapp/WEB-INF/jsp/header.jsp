<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
		<meta name="format-detection" content="telephone=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="viewport" content="width=device-width, initial-scale=1">

		<link href="https://fonts.googleapis.com/css2?family=Baloo+2:wght@400;500&display=swap" rel="stylesheet">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
		<link rel="stylesheet" href="./css/style2.css">
		<link href="https://cdnjs.cloudflare.com/ajax/libs/simple-line-icons/2.4.1/css/simple-line-icons.min.css" rel="stylesheet">
   		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
   		<link rel="stylesheet" href="./assetsForTheme/css/style.css">
   		<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css'>
		<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-beta/css/bootstrap.min.css'>
		<link rel="stylesheet" href="./css/styleTable.css">
		<link href="https://fonts.googleapis.com/css?family=Roboto:300,400&display=swap" rel="stylesheet">
   		<link href="https://fonts.googleapis.com/css?family=Poppins:300,400,500&display=swap" rel="stylesheet">
    	<link href="https://fonts.googleapis.com/css?family=Source+Serif+Pro:400,600&display=swap" rel="stylesheet">
    	<link rel="stylesheet" href="./assetsForModal/css/style1.css">
	    <link rel="stylesheet" href="./assetsForModal/css/bootstrap.min.css">     
	    <link rel="stylesheet" href="./assetsForModal/css/style.css"> 
		<link rel="stylesheet" type="text/css" href="./assetsForTheme/fonts/fontawesome/font-awesome.min.css">
		<link rel="stylesheet" type="text/css" href="./assetsForTheme/fonts/pe-icon/pe-icon.css">
		<link rel="stylesheet" type="text/css" href="./assetsForTheme/vendors/bootstrap/grid.css">
		<link rel="stylesheet" type="text/css" href="./assetsForTheme/vendors/magnific-popup/magnific-popup.min.css">
		<link rel="stylesheet" type="text/css" href="./assetsForTheme/vendors/swiper/swiper.css">
		<link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Montserrat:300,400,500,600,700|Open+Sans:400,700">
		<link rel="stylesheet" type="text/css" id="app-stylesheet" href="./assetsForTheme/css/main.css">
		
		<fmt:setLocale value="${sessionScope.locale}" />
		<c:if test="${empty sessionScope.locale}">
        	<fmt:setLocale value="ru" />
    	</c:if>
		<fmt:setBundle basename="resources.localization.app"/>
		<fmt:message key="confirm" var="confirm"/>
		<fmt:message key="home" var="home"/>
		<fmt:message key="contact" var="contact"/>
		<fmt:message key="log_in_page" var="log_in_page"/>
		<fmt:message key="log_out" var="log_out"/>
		<fmt:message key="workpage" var="workpage"/>
		<fmt:message key="registration_page" var="registration_page"/>
		<fmt:message key="action_message" var="action_message"/>
		<fmt:message key="error" var="msg_error"/>
		<fmt:message key="successful.send" var="successful_send"/>
		<fmt:message key="successful.update" var="successful_update"/>
		<fmt:message key="failed.update" var="failed_update"/>
		<fmt:message key="successful.finish_trip" var="successful_finish_trip"/>
		<fmt:message key="failed.finish_trip" var="failed_finish_trip"/>
		<fmt:message key="successful.start_trip" var="successful_start_trip"/>
		<fmt:message key="failed.start_trip" var="failed_start_trip"/>
		<fmt:message key="successful.add" var="successful_add"/>
		<fmt:message key="failed.add" var="failed_add"/>
		<fmt:message key="successful.delete" var="successful_delete"/>
		<fmt:message key="failed.delete" var="failed_delete"/>
		<fmt:message key="invalid_data" var="invalid_data"/>
		<fmt:message key="successful.confirm" var="successful_confirm"/>
		<fmt:message key="failed.confirm" var="failed_confirm"/>
		<fmt:message key="successful.registration" var="successful_registration"/>
		<fmt:message key="failed.registration" var="failed_registration"/>
		<fmt:message key="incorrect_data" var="incorrect_data"/>
		<fmt:message key="incorrect_date" var="incorrect_date"/>
		<fmt:message key="logist_in_trip" var="logist_in_trip"/>
		<fmt:message key="inconsistency_data" var="inconsistency_data"/>
		<fmt:message key="failed_delete_order_in_use" var="failed_delete_order_in_use"/>
		<fmt:message key="already_in_use" var="already_in_use"/>
		<fmt:message key="date_already_in_use" var="date_already_in_use"/>
		<fmt:message key="overload_trip" var="overload_trip"/>
		<fmt:message key="invalid_entry" var="invalid_entry"/>
		<fmt:message key="similar_phone" var="similar_phone"/>
		<fmt:message key="similar_login" var="similar_login"/>
		<fmt:message key="similar_email" var="similar_email"/>
		<fmt:message key="passport_already_in_use" var="passport_already_in_use"/>
		<fmt:message key="email_already_confirm" var="email_already_confirm"/>
		<fmt:message key="license_plate_already_in_use" var="license_plate_already_in_use"/>
		<fmt:message key="empty_trip_to_finish" var="empty_trip_to_finish"/>
		<fmt:message key="message_send_failed" var="message_send_failed"/>
		<fmt:message key="trip_started" var="trip_started"/>
	</head>
	
	<body>
		<div class="page-wrap" id="root">		
			
			<header class="header header--fixed">
				<div class="header__inner">
					<div class="header__logo"><a href="index.jsp"><img src="./assets/img/logo2.png" alt="" style="width: 122px;"/></a></div>
					<div class="navbar-toggle" id="fs-button">
						<div class="navbar-icon"><span></span></div>
					</div>
				</div>
				
				<div class="fullscreenmenu__module" trigger="#fs-button">
					<nav class="overlay-menu">
						<ul class="wil-menu-list">
							<li><a href="index.jsp">${home}</a>
							</li>
							<li><a href="controller?command=contact_page">${contact}</a>
							</li>
							<c:if test="${empty sessionScope.user}">
								<li><a href="controller?command=log_in_page">${log_in_page}</a>
								</li>
								<li><a href="controller?command=registration_page">${registration_page}</a>
								</li>
							</c:if>
							<c:if test="${not empty sessionScope.user}">
								<li><a href="controller?command=workspace">${workpage}</a>
								</li>
								<li><a href="controller?command=log_out">${log_out}</a>
								</li>
							</c:if>
						</ul>
					</nav>
				</div>
			</header>
			
			<c:if test="${not empty param.message || not empty message}">
				<c:set scope="request" var="local_message" value="${param.message}${message}" />
			    <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
			      <div class="modal-dialog modal-dialog-centered" role="document">
			        <div class="modal-content rounded-0">
			          <div class="modal-body p-4 px-5">
			            <div class="main-content text-center">          
			                <a href="#" class="close-btn" data-dismiss="modal" aria-label="Close">
			                    <span aria-hidden="true"><span>Х</span></span>
			                </a>
			                <form action="#">
			                  <p class="page-title__text">${action_message}</p>			                 
			                  <c:choose>
							  	<c:when test="${local_message eq 'successful.send'}">
							    	<p class="page-title__text">${successful_send}</p>
							    </c:when>
							    <c:when test="${local_message eq 'error'}">
							    	<p class="page-title__text">${msg_error}</p>
							    </c:when>
							    <c:when test="${local_message eq 'successful.update'}">
							    	<p class="page-title__text">${successful_update}</p>
							    </c:when>
							    <c:when test="${local_message eq 'failed.update'}">
							    	<p class="page-title__text">${failed_update}</p>
							    </c:when>
							    <c:when test="${local_message eq 'successful.finish_trip'}">
							    	<p class="page-title__text">${successful_finish_trip}</p>
							    </c:when>
							    <c:when test="${local_message eq 'failed.finish_trip'}">
							    	<p class="page-title__text">${failed_finish_trip}</p>
							    </c:when>
							    <c:when test="${local_message eq 'successful.start_trip'}">
							    	<p class="page-title__text">${successful_start_trip}</p>
							    </c:when>
							    <c:when test="${local_message eq 'failed.start_trip'}">
							    	<p class="page-title__text">${failed_start_trip}</p>
							    </c:when>
							    <c:when test="${local_message eq 'successful.add'}">
							    	<p class="page-title__text">${successful_add}</p>
							    </c:when>
							    <c:when test="${local_message eq 'failed.add'}">
							    	<p class="page-title__text">${failed_add}</p>
							    </c:when>
							     <c:when test="${local_message eq 'successful.delete'}">
							    	<p class="page-title__text">${successful_delete}</p>
							    </c:when>
							    <c:when test="${local_message eq 'failed.delete'}">
							    	<p class="page-title__text">${failed_delete}</p>
							    </c:when>
							    <c:when test="${local_message eq 'invalid_data'}">
							    	<p class="page-title__text">${invalid_data}</p>
							    </c:when>
							    <c:when test="${local_message eq 'successful.confirm'}">
							    	<p class="page-title__text">${successful_confirm}</p>
							    </c:when>
							    <c:when test="${local_message eq 'failed.confirm'}">
							    	<p class="page-title__text">${failed_confirm}</p>
							    </c:when>
							    <c:when test="${local_message eq 'successful.registration'}">
							    	<p class="page-title__text">${successful_registration}</p>
							    </c:when>
							    <c:when test="${local_message eq 'failed.registration'}">
							    	<p class="page-title__text">${failed_registration}</p>
							    </c:when>
							    <c:when test="${local_message eq 'incorrect_date'}">
							    	<p class="page-title__text">${incorrect_date}</p>
							    </c:when>
							    <c:when test="${local_message eq 'incorrect_data'}">
							    	<p class="page-title__text">${incorrect_data}</p>
							    </c:when>
							    <c:when test="${local_message eq 'logist_in_trip'}">
							    	<p class="page-title__text">${logist_in_trip}</p>
							    </c:when>
							    <c:when test="${local_message eq 'inconsistency_data'}">
							    	<p class="page-title__text">${inconsistency_data}</p>
							    </c:when>
							    <c:when test="${local_message eq 'failed_delete_order_in_use'}">
							    	<p class="page-title__text">${failed_delete_order_in_use}</p>
							    </c:when>
							    <c:when test="${local_message eq 'already_in_use'}">
							    	<p class="page-title__text">${already_in_use}</p>
							    </c:when>
							    <c:when test="${local_message eq 'date_already_in_use'}">
							    	<p class="page-title__text">${date_already_in_use}</p>
							    </c:when>
							    <c:when test="${local_message eq 'overload_trip'}">
							    	<p class="page-title__text">${overload_trip}</p>
							    </c:when>
							     <c:when test="${local_message eq 'invalid_entry'}">
							    	<p class="page-title__text">${invalid_entry}</p>
							    </c:when>
							     <c:when test="${local_message eq 'similar_email'}">
							    	<p class="page-title__text">${similar_email}</p>
							    </c:when>
							     <c:when test="${local_message eq 'similar_login'}">
							    	<p class="page-title__text">${similar_login}</p>
							    </c:when>
							     <c:when test="${local_message eq 'similar_phone'}">
							    	<p class="page-title__text">${similar_phone}</p>
							    </c:when>
							     <c:when test="${local_message eq 'passport_already_in_use'}">
							    	<p class="page-title__text">${passport_already_in_use}</p>
							    </c:when>
							     <c:when test="${local_message eq 'email_already_confirm'}">
							    	<p class="page-title__text">${email_already_confirm}</p>
							    </c:when>
							     <c:when test="${local_message eq 'license_plate_already_in_use'}">
							    	<p class="page-title__text">${license_plate_already_in_use}</p>
							    </c:when>
							     <c:when test="${local_message eq 'empty_trip_to_finish'}">
							    	<p class="page-title__text">${empty_trip_to_finish}</p>
							    </c:when>
							    <c:when test="${local_message eq 'message_send_failed'}">
							    	<p class="page-title__text">${message_send_failed}</p>
							    </c:when>
							     <c:when test="${local_message eq 'trip_started'}">
							    	<p class="page-title__text">${trip_started}</p>
							    </c:when>									    
							    <c:otherwise>
      								<p class="page-title__text">${param.message}${message}</p>
   								</c:otherwise>						    
							  </c:choose>
			                  <div class="d-flex">
			                    <div class="mx-auto">
			                    <a href="#" class="md-btn md-btn--outline-primary" data-dismiss="modal" aria-label="Close">${confirm}</a>
			                    </div>
			                  </div>
			                </form>  
			            </div>
			          </div>
			        </div>
			      </div>
			    </div>
			</c:if>