<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="header.jsp"%>

<fmt:message key="login" var="login"/>
<fmt:message key="password" var="password"/>
<fmt:message key="log_in_page" var="title"/>
<fmt:message key="enter_data" var="enter_data"/>
<fmt:message key="log_in" var="log_in"/>
<fmt:message key="back" var="back"/>
     
<title>${title}</title>
	
 <div class="registration-form">
    <form action = "controller" method = "post">
	<input type = "hidden" name = "command"  value = "log_in"/>
        <div class="page-title">
           	<h2 class="work__title">${enter_data}</h2>
        </div>
        <div class="form-group">
            <input type="text" class="form-control item" pattern="^[a-zA-Z][a-zA-Z0-9-_]{3,19}$" name="login" placeholder="${login}" required>
        </div>
        <div class="form-group">
            <input type="password" class="form-control item" pattern="^(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,20}$" name="password" placeholder="${password}" required>
        </div>		
	   	<div class="form-group">
            <button type="submit" class="md-btn md-btn--outline-primary" style = "margin-left: 180px; margin-top: 20px;">${log_in}</button>
        </div>
        <div class="form-group">
           <a class="md-btn md-btn--outline-primary" style = "margin-left: 190px;" href="controller?command=back">${back}</a>
        </div>      	
    </form>
 </div>
  
<%@include file="footer.jsp"%>	