<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <%@ include file="header.jsp"%>
    <title>NUR - Home</title>
</head>
<body>

    <p class="h2">Welcome, ${domainUserName}! This is the Watson User Home Page.</p>
    <hr/>
    <div class="col-lg-6">
        <h3 class="sub-header">Your current access details</h3>
        <table class="table">
            <thead>
            <tr>
                <th>Watson Instance</th>
                <th>Status</th>
                <th>Last Login Date</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>

        <h3 class="sub-header">Your open access requests</h3>
        <table class="table">
            <thead>
            <tr>
                <th>Watson Instance</th>
                <th>Status</th>
                <th>Request Date</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="request" items="${openRequests}">
                <tr>
                <td>${request.watsonInstance}</td>
                <td>${request.status}</td>
                <td><fmt:formatDate value="${request.requestDate}" pattern="MM/dd/yyyy"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <h3 class="sub-header">Requests awaiting your approval</h3>
        <table class="table">
            <thead>
            <tr>
                <th>Domain Username</th>
                <th>Watson Instance</th>
                <th>Request Date</th>
                <th>Action</th>
            </tr>
            </thead>
            <c:forEach var="request" items="${requestsAwaitingApproval}">
                <tr>
                    <td>${request.domainUserName}</td>
                    <td>${request.watsonInstance}</td>
                    <td><fmt:formatDate value="${request.requestDate}" pattern="MM/dd/yyyy"/></td>
                    <td><a href="review/${request.requestId}">Review</a></td>
                </tr>
            </c:forEach>
            <tbody>
            </tbody>
        </table>
    </div>
    <a href="access/request">Request Access</a>
</body>
</html>