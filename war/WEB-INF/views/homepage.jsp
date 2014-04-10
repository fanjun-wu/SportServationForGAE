<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
<head>
	<jsp:include page="/WEB-INF/views/includes/header.jsp" />
	<title>Home</title>
</head>
<body>
	<jsp:include page="/WEB-INF/views/includes/logo.jsp" />
<h1>
	Demo Spring on GAE
</h1>

<p>
	The following controllers are available:
	<ul>
		<li><a href="/main/companyList">CompanyController</a></li>
		<li><a href="/main/hallList">HallController</a></li>
		<li><a href="/main/adminList">AdminController</a></li>
		<li><a href="/main/courtList">CourtController</a></li>
		<li><a href="/main/capabilityList">CapabilityController</a></li>
		<li><a href="/main/reservationList">ReservationController</a></li>
		<li><a href="/main/timeIntervalList">TimeIntervalController</a></li>
		<li><a href="/main/subscriberList">SubscriberController</a></li>
		
	</ul>
</p>

<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
</body>
</html>
