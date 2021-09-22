<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/WEB-INF/jsp/header.jsp"%>

<fmt:message key="new_shipper_title" var="title"/>
<fmt:message key="add" var="add"/>
<fmt:message key="back" var="back"/>
<fmt:message key="client_name" var="client_name"/>
<fmt:message key="email" var="email"/>
<fmt:message key="contact_person_name" var="name"/>
<fmt:message key="contact_person_surname" var="surname"/>
<fmt:message key="contact_phone" var="phone"/>
<fmt:message key="note" var="note"/>
<fmt:message key="your_message" var="your_message"/>

<title>${title}</title>

<div class="registration-form">
	<form action = "controller" method = "post">
	<input type = "hidden" name = "command" value = "add_shipper"/>
     	<div class="page-title">
        	<h2 class="work__title">${title}</h2>
        </div>          
       	<div class="form-group">
			<c:out value="${client_name}"></c:out>
            <input type="text" class="form-control item" name="company" pattern="^[0-9A-zА-я\s]{2,20}$"  required>
            <c:out value="${email}"></c:out>
            <input type="email" class="form-control item" name="email" pattern=".+@.+\..+{5,40}$"  value = "">
            <c:out value="${name}"></c:out>
            <input type="text" class="form-control item" name="name" pattern="^[A-zА-я]{2,20}$"  value = "" required>
            <c:out value="${surname}"></c:out>
            <input type="text" class="form-control item" name="surname" pattern="^[A-zА-я]{2,20}$"  value = "" required>
            <c:out value="${phone}"></c:out>
            <input type="text" class="form-control item" name="phone" pattern="^[0-9]{11,12}$"  value = "" required>
            <c:out value="${note}"></c:out>
            <textarea class="form-control" name="note" placeholder="${your_message}"  style="min-height:200px;"></textarea>
        </div>
        <div class="form-group">
        	<button type="submit" class="md-btn md-btn--outline-primary" style = "margin-left: 170px;">${add}</button>
        </div>
        <div class="form-group">
             <a class="md-btn md-btn--outline-primary" style = "margin-left: 190px;" href="controller?command=back">${back}</a>
        </div>
	</form> 
</div>
    
<%@include file="/WEB-INF/jsp/footer.jsp"%> 