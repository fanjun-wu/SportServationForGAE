<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<jsp:include page="/WEB-INF/views/includes/header.jsp" />
	<body>
	<jsp:include page="/WEB-INF/views/includes/logo.jsp" />
	
	
		<div>

			<h1>Court List</h1>

			<table id="hovertable" class="hoverable">
				<thead>
				<tr>
					<th>Court Name</th>
					<th>Introduction</th>
					
				</tr>
				</thead>
				<c:forEach items="${courts}" var="court">
					<tr class="hoverable" id="id${court.id}" onClick="document.location.href = 'getCourt?courtId=${court.id}';">
						<td>${court.name}</td>
						<td>${court.introduction}</td>
						<%--  <c:if test="${empty court.capabilitiesName}">  --%>
							<%-- <td><a href="courtAddCapa?courtId=${court.id}">Set Capability</a></td> --%>
							<%-- <td><a href="addcoutcap?courtId=${court.id}">Set Capability</a></td> --%>
							
						 <%-- </c:if> --%>
						
						<c:choose>
     						 <c:when test="${empty court.capabilitiesName}">
     							 <td><a href="addcoutcap?courtId=${court.id}">Set Capability</a></td>
     						 </c:when>

     						 <c:otherwise>
      							 <td><a href="addcoutcap?courtId=${court.id}">Add More Capability</a></td>
     						 </c:otherwise>
						</c:choose>
						<td><a href="showCourtCapList?courtId=${court.id}">Capability List</a></td>
						
						
					</tr>
				</c:forEach>
			</table>
			<br/>
			<a href="newCourt">Add Court</a>

			<jsp:include page="/WEB-INF/views/includes/footer.jsp" />

		 </div>
	 </body>
 </html>