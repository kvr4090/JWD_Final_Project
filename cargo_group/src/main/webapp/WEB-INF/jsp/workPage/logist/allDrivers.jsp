<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/WEB-INF/jsp/header.jsp"%>

<fmt:message key="all_drivers_title" var="title"/>
<fmt:message key="placeholder_search" var="search"/>
<fmt:message key="our_drivers" var="our_drivers"/>
<fmt:message key="drive_license" var="drive_license"/>
<fmt:message key="status" var="status"/>
<fmt:message key="truck" var="truck"/>
<fmt:message key="name_surname" var="name_surname"/>
<fmt:message key="phone" var="phone"/>
<fmt:message key="status_work" var="work"/>
<fmt:message key="status_fired" var="fired"/>
<fmt:message key="count_items" var="count_items"/>
<fmt:message key="back" var="back"/>
<fmt:message key="add" var="add"/>

<title>${title}</title>

<div class="wil-content">
	<section class="awe-section bg-gray">
	  	<div class="container" style = "margin-left: 190px;margin-right: 56px;">
        	<h3>${our_drivers}</h3>
			<div class="container">
      			<div class="header_wrap">
			        <div class="tb_search">
<input type="text" id="search_input_all" onkeyup="FilterkeyWord_all_table()" style="width: 140px;" placeholder="${search}" class="form-control">
			        </div>
      			</div>
      			
				<table class="table table-striped table-class" id= "table-id">
					<thead>
						<tr>
							<th>${name_surname}</th>
					        <th>${drive_license}</th>
					        <th>${phone}</th>
					        <th>${status}</th>
					        <th>${truck}</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="user" items="${listUsers}">
							<c:forEach var="personalData" items="${listPersonalData}">        					
								<c:if test="${(user.id eq personalData.userId) && (not empty personalData.driveLicense)}">
									<tr>
										<td>${personalData.name} ${personalData.surname}</td>
										<td>${personalData.driveLicense}</td>
										<td>${user.phone}</td>
										<c:choose>
											<c:when test="${empty personalData.terminationDate}">
												<td>${work}</td>
											</c:when>
											<c:when test="${not empty personalData.terminationDate}">
												<td>${fired}</td>
											</c:when>
										</c:choose>
										<c:if test="${user.vehicleId eq '0'}">
<td><p class="page-title__text"><a href="controller?command=driver_vehicle&userId=${user.id}">${add}</a></p></td>										
										</c:if>
										<c:forEach var="vehicle" items="${listVehicle}">											
											<c:if test="${user.vehicleId eq vehicle.id}">
<td><p class="page-title__text"><a href="controller?command=driver_vehicle&vehicle=${vehicle.licensePlate}&userId=${user.id}">${vehicle.licensePlate}</a></p></td>	
											</c:if>														
										</c:forEach>	        						
									</tr>        						
								</c:if>                                  						                            
						    </c:forEach>                        
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