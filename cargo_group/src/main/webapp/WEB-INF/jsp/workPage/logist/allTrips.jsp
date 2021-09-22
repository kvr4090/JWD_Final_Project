<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/WEB-INF/jsp/header.jsp"%>

<fmt:message key="all_trips_title" var="title"/>
<fmt:message key="current_trips" var="trips"/>
<fmt:message key="trip_start_date" var="start_date"/>
<fmt:message key="driver_data" var="driver"/>
<fmt:message key="vehicle_plate" var="vehicle_plate"/>
<fmt:message key="delete" var="delete"/>
<fmt:message key="delete_trip" var="delete_trip"/>
<fmt:message key="placeholder_search" var="placeholder_search"/>
<fmt:message key="note_trip" var="note_trip"/>
<fmt:message key="details" var="details"/>
<fmt:message key="count_items" var="count_items"/>
<fmt:message key="back" var="back"/>

<title>${title}</title>

<div class="wil-content">
	<input type = "hidden" name = "command" value = "add_trip"/>			
	<section class="awe-section bg-gray">
		<div class="container" style = "margin-left: 190px;margin-right: 56px;">
		        <h3>${trips}</h3>
		<div class="container">
		    <div class="header_wrap"><br>
		    	<div class="tb_search">
					<input type="text" id="search_input_all" onkeyup="FilterkeyWord_all_table()" placeholder="${placeholder_search}" class="form-control"style="width: 180px;">
		    	</div>       
		    </div>     
			<table class="table table-striped table-class" id= "table-id">
				<thead>
					<tr>
					    <th>${start_date}</th>
					    <th>${driver}</th>
					    <th>${vehicle_plate}</th>
					    <th>${note_trip}</th>
					    <th>${delete}</th>
					</tr>
			  </thead>
				<tbody>	
					<c:forEach var="trip" items="${listTrip}">
						<c:forEach var="vehicle" items="${listVehicle}">	
							<c:if test="${trip.vehicleId eq vehicle.id}">
								<c:forEach var="user" items="${listUsers}">
									<c:if test="${user.vehicleId eq vehicle.id }">
										<c:forEach var="personalData" items="${listPersonalData}">
											<c:if test="${user.id eq personalData.userId}">
												<tr>
												    <td>${trip.startDate}</td>
												    <td>${personalData.surname} ${personalData.name}</td>
												    <td>${vehicle.licensePlate}</td>
												    <td><p class="page-title__text"><a href="controller?command=trip&trip=${trip.id}">${details}</a></p></td>
												    <td><p class="page-title__text"><a href="controller?command=delete_trip&trip=${trip.id}&vehicle=${trip.vehicleId}">${delete_trip}</a></p></td>
												</tr>    
											</c:if>
										</c:forEach>
									</c:if>
								</c:forEach>
							</c:if>			
						</c:forEach>
					</c:forEach>
				<tbody>
			</table>
			<div class='pagination-container'>
				<nav>
					<ul class="pagination"></ul>
				</nav>
			</div>
			<div class="rows_count" style="width: 300px;"></div>
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
		</div>
		</div>
		<div class="form-group">
			 <a class="md-btn md-btn--outline-primary" style = "margin-left: 600px;" href="controller?command=back">${back}</a>
		</div>
	</section>
</div>

<%@include file="/WEB-INF/jsp/footer.jsp"%>