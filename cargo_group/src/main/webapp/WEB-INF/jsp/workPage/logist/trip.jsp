<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/WEB-INF/jsp/header.jsp"%>

<fmt:message key="trip_title" var="title"/>
<fmt:message key="client" var="client"/>
<fmt:message key="from" var="from"/>
<fmt:message key="where" var="where"/>
<fmt:message key="note" var="note"/>
<fmt:message key="back" var="back"/>

<title>${title}</title>

<div class="wil-content">
	<form action = "controller" method = "post">
	<input type = "hidden" name = "command" value = "add_trip"/>			
		<section class="awe-section bg-gray">
		<div class="container" style = "margin-left: 190px;margin-right: 56px;">
			<h3>${title}</h3>
			<table class="table table-striped table-class" id= "table-id">
				<thead>
					<tr>
					    <th>${client}</th>
					    <th>${from}</th>
					    <th>${where}</th>
					    <th>${note}</th>
					</tr>
				</thead>
				<tbody>						
					<c:forEach var="order" items="${listOrder}">
						<c:forEach var="shipper" items="${listShipper}">
							<c:if test="${order.shipperId eq shipper.id}">
								<tr>
									<td>${shipper.name}</td>
									<td>${order.routeStartPoint}</td>
									<td>${order.routeEndPoint}</td>						 
									<td>${order.note}</td>
								</tr>    
							</c:if>
						</c:forEach>
					</c:forEach>					
				<tbody>
			</table> 
		</div>		
		<div class="form-group">
			 <a class="md-btn md-btn--outline-primary" style = "margin-left: 600px;" href="controller?command=back">${back}</a>
		</div>	            
		</section>
	</form>
</div>

<%@include file="/WEB-INF/jsp/footer.jsp"%>