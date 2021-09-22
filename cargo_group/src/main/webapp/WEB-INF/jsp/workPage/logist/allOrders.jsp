<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/WEB-INF/jsp/header.jsp"%>

<fmt:message key="all_orders_title" var="title"/>
<fmt:message key="placeholder_search" var="search"/>
<fmt:message key="available_orders" var="available_orders"/>
<fmt:message key="count_items" var="count_items"/>
<fmt:message key="order_note" var="note"/>
<fmt:message key="order_date" var="date"/>
<fmt:message key="client" var="client"/>
<fmt:message key="from" var="from"/>
<fmt:message key="where" var="where"/>
<fmt:message key="weight" var="weight"/>
<fmt:message key="volume" var="volume"/>
<fmt:message key="pallets" var="pallets"/>
<fmt:message key="delete_selected_orders" var="delete_selected_orders"/>
<fmt:message key="back" var="back"/>

<title>${title}</title>

<div class="wil-content">
	<form action = "controller" method = "post">
		<input type = "hidden" name = "command" value = "delete_order"/>			
			<section class="awe-section bg-gray">
				<div class="container" style = "margin-left: 190px;margin-right: 56px;">
        		<h3>${available_orders}</h3>

				<div class="container">
     				<div class="header_wrap"><br>
	     				<div class="tb_search">
							<input type="text" id="search_input_all" onkeyup="FilterkeyWord_all_table()" placeholder="${search}" class="form-control"style="width: 180px;">
	        			</div>      
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
							    <th>${volume }</th>
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
                <button type="submit" class="md-btn md-btn--outline-primary" style = "margin-left: 500px;">${delete_selected_orders}</button>
            </div>
            <div class="form-group">
                 <a class="md-btn md-btn--outline-primary" style = "margin-left: 600px;" href="controller?command=back">${back}</a>
            </div>           
		</section>
	</form>
</div>

<%@include file="/WEB-INF/jsp/footer.jsp"%>