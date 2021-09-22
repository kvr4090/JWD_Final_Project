<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/WEB-INF/jsp/header.jsp"%>

<fmt:message key="all_shippers_title" var="title"/>
<fmt:message key="our_clients" var="clients"/>
<fmt:message key="count_items" var="count_items"/>
<fmt:message key="client_name" var="client_name"/>
<fmt:message key="email" var="email"/>
<fmt:message key="contact_person" var="contact_name"/>
<fmt:message key="contact_phone" var="contact_phone"/>
<fmt:message key="note" var="note"/>
<fmt:message key="back" var="back"/>
<fmt:message key="placeholder_search" var="search"/>

<title>${title}</title>

<div class="wil-content">
	<section class="awe-section bg-gray">
		<div class="container" style = "margin-left: 190px;margin-right: 56px;">
        <h3>${clients}</h3>
		<div class="container">
      		<div class="header_wrap">
		        <div class="tb_search">
					<input type="text" id="search_input_all" onkeyup="FilterkeyWord_all_table()" style="width: 140px;" placeholder="${search}" class="form-control">
		        </div>
      		</div>
			<table class="table table-striped table-class" id= "table-id">
				<thead>
					<tr>
						<th>${client_name}</th>
					    <th>${email}</th>
					    <th>${contact_name}</th>
					    <th>${contact_phone}</th>
					    <th>${note}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="shipper" items="${listShipper}">
						<tr>
							<td>${shipper.name}</td>
							<td>${shipper.email}</td>
							<td>${shipper.contactPersonName} ${shipper.contactPersonSurname}</td>
							<td>${shipper.contactPhone}</td>
							<td>${shipper.note}</td>
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