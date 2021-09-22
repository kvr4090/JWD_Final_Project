<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/WEB-INF/jsp/header.jsp"%>

<fmt:message key="requirement_title" var="title"/>
<fmt:message key="add" var="add"/>
<fmt:message key="back" var="back"/>
<fmt:message key="requirement" var="requirement"/>
<fmt:message key="load_capacity" var="load_capacity"/>
<fmt:message key="volume" var="volume"/>
<fmt:message key="height" var="height"/>
<fmt:message key="length" var="length"/>
<fmt:message key="pallets" var="pallets"/>
<fmt:message key="width" var="width"/>
<fmt:message key="placeholder_capacity" var="placeholder__capacity"/>
<fmt:message key="placeholder_volume" var="placeholder_volume"/>
<fmt:message key="placeholder_dimensions" var="placeholder_dimensions"/>
<fmt:message key="placeholder_pallets" var="placeholder_pallets"/>

<title>${title}</title>
    
<div class="registration-form">
	<form action = "controller" method = "post">
		<input type = "hidden" name = "command" value = "add_new_requirement"/>
        	<div class="page-title">
            	<h2 class="work__title">${requirement}</h2>
            </div>
       		<div class="form-group">
      			<c:out value="${load_capacity}"></c:out>
                <input type="text" class="form-control item" name="weight" pattern="^[0-9\.]{1,4}$" placeholder = "${placeholder__capacity}" value = "" required>
                <c:out value="${volume}"></c:out>
                <input type="text" class="form-control item" name="volume" pattern="^[0-9\.]{1,5}$" placeholder = "${placeholder_volume}" value = "" required>
                <c:out value="${pallets}"></c:out>
                <input type="text" class="form-control item" name="pallets_quantity" pattern="^[0-9]{1,2}$" placeholder = "${placeholder_pallets}" value = "" required>
                <c:out value="${length}"></c:out>
                <input type="text" class="form-control item" name="length" pattern="^[0-9\.]{1,4}$" placeholder = "${placeholder_dimensions}" value = "" required>
                <c:out value="${width}"></c:out>
                <input type="text" class="form-control item" name="width" pattern="^^[0-9\.]{1,4}$" placeholder = "${placeholder_dimensions}" value = "" required>
                <c:out value="${height}"></c:out>
                <input type="text" class="form-control item" name="height" pattern="[0-9\.]{1,4}$" placeholder = "${placeholder_dimensions}" value = "" required>
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