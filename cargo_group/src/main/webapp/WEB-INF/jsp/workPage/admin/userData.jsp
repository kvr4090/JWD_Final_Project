<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@include file="/WEB-INF/jsp/header.jsp"%>

<fmt:message key="update_data_title" var="title"/>
<fmt:message key="user_data" var="data"/>
<fmt:message key="name" var="name"/>
<fmt:message key="surname" var="surname"/>
<fmt:message key="birthdate" var="birthdate"/>
<fmt:message key="post" var="post"/>
<fmt:message key="passport_number" var="number"/>
<fmt:message key="passport_identification_number" var="i_number"/>
<fmt:message key="passport_date_of_expiry" var="expiry"/>
<fmt:message key="passport_date_of_issue" var="issue"/>
<fmt:message key="passport_authority" var="authority"/>
<fmt:message key="drive_license" var="license"/>
<fmt:message key="recruitment_date" var="rec_date"/>
<fmt:message key="termination_date" var="ter_date"/>
<fmt:message key="active_status" var="active"/>
<fmt:message key="inactive_status" var="inactive"/>
<fmt:message key="role" var="role"/>
<fmt:message key="status" var="status"/>
<fmt:message key="current" var="current"/>
<fmt:message key="accept" var="accept"/>
<fmt:message key="back" var="back"/>
<fmt:message key="new_password" var="new_password"/>
<fmt:message key="password" var="password"/>

<title>${title}</title>	

<div class="registration-form">
	<form action = "controller" method = "post">
	<input type = "hidden" name = "command" value = "update_user"/>
	<input type = "hidden" name = "user" value = "${user.id}"/>
	<div class="page-title">
    	<h2 class="work__title">${data}:</h2>
    </div>
    
    <div class="form-group">
    	<c:out value="${role}"></c:out>
       	<div class="select2">
			<select id="standard-select" name = "role">
				<option value="${user.role}" selected>${user.role} (Current)</option>
			    <c:forEach var="role" items="${roles}">  		    	
				  	<option value="${role}">${role}</option>
				</c:forEach>											
			</select>
			<span class="focus"></span>
		</div>
	            
		<c:out value="${status}"></c:out>
	    <div class="select2">	             
			<select id="standard-select" name = "isActive">	
						  	
			 	<c:if test="${user.isActive eq 'true'}">
			  		<option value="true" selected>${active}</option>   							
					<option value="false">${inactive}</option>				  	
				</c:if>	
				
			  	<c:if test="${user.isActive eq 'false'}">
			  		<option value="true">${active}</option>   							
					<option value="false" selected>${inactive}</option>			  	
				</c:if>		    
											
			</select>
			<span class="focus"></span>
		</div>
	            
        <c:out value="${name}"></c:out>
        <input type="text" class="form-control item" name="name" pattern="^[a-zA-Zа-яА-Я\s]{1,20}$" value="${personalData.name}" required>
	            
        <c:out value="${surname}"></c:out>
        <input type="text" class="form-control item" name="surname" pattern="^[a-zA-Zа-яА-Я\s]{1,20}$" value="${personalData.surname}" required>
	            
        <c:out value="${birthdate}"></c:out>
        <input type="date" class="form-control item" name="birthdate" value="${personalData.birthdate}" required>
            
        <c:out value="${post}"></c:out>
        <input type="text" class="form-control item" name="post" pattern="^[a-zA-Zа-яА-Я\s]{1,20}$" value="${personalData.post}" required>
	            
        <c:out value="${number}"></c:out>
        <input type="text" class="form-control item" name="passport_number" pattern="^([A-Z]{2})([\d]{7})$" value="${personalData.passportNumber}" required>
	            
        <c:out value="${i_number}"></c:out>
        <input type="text" class="form-control item" name="passport_identification_number" pattern="^([\d]{7})([A-Z])([\d]{3})([A-Z]{2})([0-9])$" value="${personalData.passportIdentificationNumber}" required>
	            
        <c:out value="${expiry}"></c:out>
        <input type="date" class="form-control item" name="passport_date_of_expiry" value="${personalData.passportDateOfExpiry}" required>
	            
        <c:out value="${issue}"></c:out>
        <input type="date" class="form-control item" name="passport_date_of_issue" value="${personalData.passportDateOfIssue}" required>
	            
        <c:out value="${authority}"></c:out>
        <input type="text" class="form-control item" name="passport_authority" pattern="^[a-zA-Zа-яА-Я0-9\s]{3,50}$" value="${personalData.passportAuthority}" required>
	            
        <c:out value="${license}"></c:out>
	       <div class="select2">
			  <select id="standard-select" name = "drive_license">
			    <option value="${personalData.driveLicense}">${personalData.driveLicense} (${current})</option>
				<option value="C">C</option>   							
				<option value="CE">CE</option>								
			  </select>
			  <span class="focus"></span>
	      </div>
	            
	    <c:out value="${rec_date}"></c:out>
	    <input type="date" class="form-control item" name="recruitment_date" value="${personalData.recruitmentDate}" required>
	            
	    <c:out value="${ter_date}"></c:out>
	    <input type="date" class="form-control item" name="termination_date" value="${personalData.terminationDate}">
    </div>
    
    <div class="form-group">
       <button type="submit" class="md-btn md-btn--outline-primary" style = "margin-left: 100px;">${accept}</button>
    </div>
        <a class="md-btn md-btn--outline-primary" style = "margin-left: 150px;" href="controller?command=back">${back}</a>
    </form>
</div>
    
<div class="registration-form">
	<form action = "controller" method = "post">
	<input type = "hidden" name = "command" value ="update_password"/>
	<input type = "hidden" name = "user" value = "${user.id}"/>
		<h2 class="work__title">${new_password}:</h2>
		<div class="form-group">
			<c:out value="${password}"></c:out>
	        <input type="password" class="form-control item" name="password" pattern="^(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,20}$" required>
	    </div>       
		<div class="form-group">
            <button type="submit" class="md-btn md-btn--outline-primary" style = "margin-left: 100px;">${accept}</button>
        </div>
	</form>
</div>
    
<%@include file="/WEB-INF/jsp/footer.jsp"%>