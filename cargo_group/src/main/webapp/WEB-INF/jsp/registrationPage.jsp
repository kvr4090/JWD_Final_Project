<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="header.jsp"%> 

<fmt:message key="login" var="login"/>
<fmt:message key="password" var="password"/>
<fmt:message key="email" var="email"/>
<fmt:message key="phone" var="phone"/>
<fmt:message key="registrate" var="registrate"/>
<fmt:message key="back" var="back"/>
<fmt:message key="enter_data" var="enter_data"/>
<fmt:message key="registration_page" var="reg_page"/>
    
<title>${reg_page}</title>

 <div class="registration-form">
    <form action = "controller" method = "post">
	<input type = "hidden" name = "command" value = "registration_user"/>
         <div class="page-title">
          	<h2 class="work__title">${enter_data}</h2>
         </div>
         <div class="form-group">
             <input type="text" class="form-control item" pattern="^[a-zA-Z][a-zA-Z0-9-_]{3,19}$" name="login" placeholder="${login}" required>
         </div>
         <div class="form-group">
             <input type="password" class="form-control item" name="password" pattern="^(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,20}$" placeholder="${password}" required>
         </div>
         <div class="form-group">
             <input type="email" class="form-control item" name="email" pattern="[^\s><]{1,30}@[^\s><]{1,10}\.[^\s><]{1,10}" placeholder="${email}">
         </div>
         <div class="form-group">
             <input type="text" class="form-control item" name="phone" pattern="^[0-9]{11,12}$" placeholder="${phone}" required>
         </div>
         <div class="form-group">
             <button type="submit" class="md-btn md-btn--outline-primary" style = "margin-left: 145px; margin-top: 20px;" >${registrate}</button>
         </div>
         <div class="form-group">
             <a class="md-btn md-btn--outline-primary" style = "margin-left: 190px;" href="controller?command=back">${back}</a>
         </div>
     </form>
         
 </div>
    
<%@include file="footer.jsp"%>   