<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/WEB-INF/jsp/header.jsp"%>

<fmt:message key="users_title" var="title"/>
<fmt:message key="status" var="status"/>
<fmt:message key="active_status" var="active"/>
<fmt:message key="inactive_status" var="inactive"/>
<fmt:message key="role" var="role"/>
<fmt:message key="action" var="action"/>
<fmt:message key="placeholder_search" var="search"/>
<fmt:message key="login" var="login"/>
<fmt:message key="details" var="details"/>
<fmt:message key="count_items" var="count_items"/>
<fmt:message key="back" var="back"/>

<title>${title}</title>	

<div class="wil-content">
	<section class="awe-section bg-gray">
		<div class="container" style = "margin-left: 190px;margin-right: 56px;">
		    <h3>${title}</h3>
			<div class="container">
				<div class="header_wrap">
				    <div class="tb_search">
						<input type="text" id="search_input_all" onkeyup="FilterkeyWord_all_table()" placeholder="${search}" style="width: 140px;" class="form-control">
				    </div>
			   </div>
				<table class="table table-striped table-class" id= "table-id">
					<thead>
						<tr>
							<th>${login}</th>
						    <th>${status}</th>
						    <th>${role}</th>
						    <th>${action}</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="user" items="${listUsers}">
						  <tr>
						  	<td>${user.login}</td>
						     <c:choose>
							    <c:when test="${user.isActive eq 'true'}">
							       <td>${active}</td>
							    </c:when>
							    <c:when test="${user.isActive eq 'false'}">
							       <td>${inactive}</td>
							    </c:when>
							</c:choose>
						    <td>${user.role}</td>	                                 
						    <td><p class="page-title__text"><a href="controller?command=user_data&user=${user.id}">${details}</a></p></td>	                                 
						  </tr>
						</c:forEach>
					<tbody>
				</table>
				<p>${count_items}</p>
			    <div class="num_rows">					
					<div class="form-group">
					 	<select class  ="form-control" name="state" id="maxRows" style="height: 33.5px; width: 80px;">		 
							<option value="10">10</option>
							<option value="15">15</option>
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="70">70</option>
							<option value="100">100</option>
						</select>							 		
					</div>
				</div>
				<div class='pagination-container'>
					<nav>
					  <ul class="pagination"></ul>
					</nav>
				</div>
		      	<div class="rows_count" style="width: 300px;"></div>			
			</div>
		</div>
		<div class="form-group">
        	 <a class="md-btn md-btn--outline-primary" style = "margin-left: 600px;" href="controller?command=back">${back}</a>
        </div>  
	</section>				
</div>

<%@include file="/WEB-INF/jsp/footer.jsp"%>