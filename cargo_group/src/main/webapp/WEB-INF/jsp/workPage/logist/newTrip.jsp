<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/WEB-INF/jsp/header.jsp"%>

<fmt:message key="new_trip_title" var="title"/>
<fmt:message key="available_orders" var="orders"/>
<fmt:message key="new_trip_work_title" var="work_title"/>
<fmt:message key="count_items" var="count_items"/>
<fmt:message key="trip_date" var="trip_date"/>
<fmt:message key="order_date" var="order_date"/>
<fmt:message key="client" var="client"/>
<fmt:message key="from" var="from"/>
<fmt:message key="where" var="where"/>
<fmt:message key="weight" var="weight"/>
<fmt:message key="volume" var="volume"/>
<fmt:message key="pallets" var="pallets"/>
<fmt:message key="note" var="note"/>
<fmt:message key="back" var="back"/>
<fmt:message key="total" var="total"/>
<fmt:message key="note_trip" var="note_trip"/>
<fmt:message key="form_trip" var="form_trip"/>
<fmt:message key="placeholder_search" var="placeholder_search"/>

<title>${title}</title>
			<!-- Content-->
<div class="wil-content">
	<form action = "controller" method = "post">
	<input type = "hidden" name = "command" value = "add_trip"/>			
		<section class="awe-section bg-gray">
			<div class="container" style = "margin-left: 190px;margin-right: 56px;">
				<h3>${orders}</h3>
				<p class="page-title__text">${work_title}</p>
				<div class="select2">
					<select id="standard-select" name = "driverId">
						<c:forEach var="user" items="${listUsers}">
							<c:forEach var="personalData" items="${listPersonalData}">
								<c:if test="${user.id eq personalData.userId}">
									<c:forEach var="truck" items="${listVehicle}">
										<c:if test="${(truck.id eq user.vehicleId) && (truck.isAvailable eq 'true')}">
											<c:forEach var="truckType" items="${listVehicleType}">
												<c:if test="${truck.typeId eq truckType.id}">
													<c:if test="${(truck.trailerId eq '0') || (empty truck.trailerId)}">
														<option value="${user.id}">${personalData.name} ${personalData.surname} ${truck.licensePlate} ${truckType.type}</option>
													</c:if>      								
													<c:if test="${not empty truck.trailerId}">
														<c:forEach var="trailer" items="${listVehicle}">
														<c:if test="${trailer.id eq truck.trailerId}">
															<c:forEach var="trailerType" items="${listVehicleType}">
																<c:if test="${trailer.typeId eq trailerType.id}">
																	<option value="${user.id}">${personalData.name} ${personalData.surname} ${truck.licensePlate} ${truckType.type} ${trailerType.type}</option>   							
																</c:if>	
															</c:forEach>
														</c:if>		
														</c:forEach>
													</c:if>
												</c:if>
											</c:forEach>
										</c:if>
									</c:forEach>
								</c:if>
							</c:forEach>
						</c:forEach>
					</select>
					<span class="focus"></span>
				</div>
				<div class="container">
					<div class="header_wrap"><br>
						<div class="tb_search">
							<input type="text" id="search_input_all" onkeyup="FilterkeyWord_all_table()" placeholder="${placeholder_search}" class="form-control"style="width: 180px;">
						</div>
						<table class="table table-striped table-class" id= "table-id2">
							<thead>
							<tr>
								<th>${trip_date}<input type="date" name = "date" placeholder="2000-01-01" style="width: 160px;" required/></th>
								<th style="width: 200px;"><p>${count_items}</p></th>
							    <th><div class="num_rows">	
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
							     </div></th>
							</tr>
							</thead>
							</table>   
				    </div> 
					<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>      
					<table class="table table-striped table-class" id= "table-id">
					<thead>
						<tr>
							<th> </th>
						    <th>${order_date}</th>
						    <th>${client}</th>
						    <th>${from}</th>
						    <th>${where}</th>
						    <th>${weight}</th>
						    <th>${volume}</th>
						    <th>${pallets}</th>
						    <th>${note}</th>
						</tr>
					</thead>
					<tbody>	
						<c:forEach var="order" items="${listOrder}">
							<c:forEach var="shipper" items="${listShipper}">
								<c:if test="${order.shipperId eq shipper.id}">
									<c:forEach var="requirement" items="${listRequirement}">
										<c:if test="${order.requirementId eq requirement.id}">
											<tr>
												<td><input type="checkbox" name = "order${order.id}" value="${order.id}" /></td>
											    <td>${order.date}</td>
											    <td>${shipper.name}</td>
											    <td>${order.routeStartPoint}</td>
											    <td>${order.routeEndPoint}</td>
											    <td>${requirement.weight}</td>
											    <td>${requirement.volume}</td>
											    <td>${requirement.palletsQuantity}</td>
											    <td>${order.note}</td>
											</tr>    
										</c:if>
									</c:forEach>
								</c:if>
							</c:forEach>
						</c:forEach>				
					<tbody>
					</table>
					<script>
						$(document).ready(function() {
					
						  function calculateSum(){
						   var sumWeight=0;
						   var sumVolume=0;
						   var sumPallets=0;
						   
						      $('table tbody tr').each(function() {
						        var $tr = $(this);
					
						        if ($tr.find('input[type="checkbox"]').is(':checked')) {
						            
						        var $columns = $tr.find('td').next('td').next('td').next('td').next('td');
						          
						        var $Weight=parseInt($columns.next('td').html());						        
						        sumWeight+=$Weight;
						          
						        var $columns = $tr.find('td').next('td').next('td').next('td').next('td').next('td');
						          
						   		
						        var $Volume=parseInt($columns.next('td').html());					          
						        sumVolume+=$Volume;
						           
						        var $columns = $tr.find('td').next('td').next('td').next('td').next('td').next('td').next('td');
							          					   		
						        var $Pallets=parseInt($columns.next('td').html());						           
						        sumPallets+=$Pallets;						           
						        }
						      });					
						         $("#weight").val(sumWeight);
						         $("#volume").val(sumVolume);
						         $("#pallets").val(sumPallets);
						  }					
						    $('#sum').on('click', function() {						       
						      calculateSum();
						    });					
						    $("input[type='text']").keyup(function() {
						       calculateSum();					
						    });						    
						     $("input[type='checkbox']").change(function() {
						       calculateSum();					
						    });				
						  });
					</script>
					<div class='pagination-container'>
						<nav>
							<ul class="pagination"></ul>
						</nav>
					</div>
					<div class="rows_count" style="width: 300px;"></div>
					<p>${total}${weight}<input type="text" id="weight" disabled style="width: 80px;"/></p>
					<p>${total}${volume}<input type="text"  id="volume" disabled style="width: 80px;"/></p>
					<p>${pallets}<input type="text" id="pallets" disabled style="width: 80px;"/></p>
				</div>
				<p>${note_trip}</p>
				<div class="form-item">
					<textarea class="form-control" name="note" placeholder="${note}"  style="min-height:200px;"></textarea>
				</div>
			</div>
			<div class="form-group">
				<button type="submit" class="md-btn md-btn--outline-primary" style = "margin-left: 600px;">${form_trip}</button>
			</div>
			<div class="form-group">
		    	 <a class="md-btn md-btn--outline-primary" style = "margin-left: 650px;" href="controller?command=back">${back}</a>
			</div>           
		</section>
	</form>
</div>

<%@include file="/WEB-INF/jsp/footer.jsp"%>