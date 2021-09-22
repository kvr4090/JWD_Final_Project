<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
    
<%@include file="/WEB-INF/jsp/header.jsp"%> 

<fmt:message key="page_contact_title" var="title"/>
<fmt:message key="title_contact_1" var="slog1"/>
<fmt:message key="title_contact_2" var="slog2"/>
<fmt:message key="title_address" var="title_address"/>
<fmt:message key="address" var="address"/>
<fmt:message key="call_us" var="call_us"/>
<fmt:message key="phone_number" var="phone_number"/>
<fmt:message key="email_address" var="email_address"/>
<fmt:message key="contact_name" var="contact_name"/>
<fmt:message key="contact_email" var="contact_email"/>
<fmt:message key="subject" var="subject"/>
<fmt:message key="your_message" var="your_message"/>
<fmt:message key="send_message" var="send"/>
<fmt:message key="email" var="email"/>

<title>${title}</title>		

<div class="wil-content">

	<section class="awe-section">
		<div class="container">
			<div class="page-title pb-40">
				<h2 class="page-title__title">${slog1}</h2>
				<p class="page-title__text">${slog2}</p>
				<div class="page-title__divider"></div>
			</div>			
		</div>
	</section>

	<section class="awe-section bg-gray">
		<div class="container">
			<div class="row">
				<div class="col-md-4 col-lg-3 ">
					<div class="contact">
						<div class="contact__icon"><i class="pe-7s-note"></i></div>
						<h3 class="contact__title">${title_address}</h3>
						<div class="contact__text">${address}</div>
					</div>

					<div class="contact">
						<div class="contact__icon"><i class="pe-7s-back"></i></div>
						<h3 class="contact__title">${call_us}</h3>
						<div class="contact__text">${phone_number}</div>
					</div>

					<div class="contact">
						<div class="contact__icon"><i class="pe-7s-voicemail"></i></div>
						<h3 class="contact__title">${email}</h3>
						<div class="contact__text"><a href="mailto:mailservicetransportsystem@gmail.com">${email_address}</a></div>
					</div>						
				</div>
				
				<div class="col-md-7 col-lg-8 col-xs-offset-0 col-sm-offset-0 col-md-offset-0 col-lg-offset-1 ">
					<div class="form-wrapper">

						<form action = "controller" method = "post">
						<input type = "hidden" name = "command" value = "send_question"/>

							<div class="form-item form-item--half">
								<input class="form-control" type="text" name="name" pattern="^[a-zA-Zа-яА-Я\s]{3,20}+$" placeholder="${contact_name}" required/>
							</div>						
	
							<div class="form-item form-item--half">
								<input class="form-control" type="email" name="email" pattern=".+@.+\..+{5,40}$" placeholder="${contact_email}" required/>
							</div>
	
							<div class="form-item">
								<input class="form-control" type="text" name="subject" pattern="^[a-zA-Zа-яА-Я\s]{3,20}+$" placeholder="${subject}" required/>
							</div>
	
							<div class="form-item">
								<textarea class="form-control" name="message" placeholder="${your_message}"  style="min-height:200px;" required></textarea>
							</div>
	
							<div class="form-group">        							
	   							<button type="submit" class="md-btn md-btn--primary md-btn--lg">${send}</button>
							</div>
						</form>						
					</div>
				</div>
			</div>
		</div>
	</section>			
</div>
			
<%@include file="/WEB-INF/jsp/footer.jsp"%>			