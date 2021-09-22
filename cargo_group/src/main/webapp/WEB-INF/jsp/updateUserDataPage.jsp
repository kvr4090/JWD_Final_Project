<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<%@include file="header.jsp"%>

<fmt:message key="update_personal_data" var="update_data"/>
<fmt:message key="password" var="password"/>
<fmt:message key="email" var="email"/>
<fmt:message key="phone" var="phone"/>
<fmt:message key="password_placeholder" var="password_placeholder"/>
<fmt:message key="back" var="back"/>
<fmt:message key="accept" var="accept"/>

<title>${update_data}</title>

    <div class="registration-form">
        <form action = "controller" method = "get">
		<input type = "hidden" name = "command" value = "update_user_data"/>

            <div class="form-group">
            <c:out value="${password}"></c:out>
                <input type="password" class="form-control item" name="password" pattern="^(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,20}$" placeholder="${password_placeholder}" required>
            </div>
            <div class="form-group">
            <c:out value="${email}"></c:out>
                <input type="email" class="form-control item" name="email" pattern=".+@.+\\..+{5,40}$" value = "${sessionScope.user.email}">
            </div>
            <div class="form-group">
            <c:out value="${phone}"></c:out>
                <input type="text" class="form-control item" name="phone" pattern="^[0-9]{11,12}+$" value = "${sessionScope.user.phone}" required>
            </div>
            <div class="form-group">
                <button type="submit" class="md-btn md-btn--outline-primary" style = "margin-left: 120px; margin-top: 20px;">${accept}</button>
            </div>
             <div class="form-group">
             	<a class="md-btn md-btn--outline-primary" style = "margin-left: 190px;" href="controller?command=back">${back}</a>
            </div>
        </form>
    </div>
    
<%@include file="footer.jsp"%>	