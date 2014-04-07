<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@ include file="header.jsp"%>
    <title>NUR - Home</title>
</head>
<body>

    <p class="h2">Welcome, ${domainUserName}! This is the Watson User Adminstration Page.</p>
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
            <c:forEach var="openRequest" items="${openAccessRequests}">
                <tr>
                <td>${openRequest.watsonInstance}</td>
                <td>${openRequest.finalResult}</td>
                <td>${openRequest.requestDate}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <h3 class="sub-header">Access requests awating your approval</h3>
        <table class="table">
            <thead>
            <tr>
                <th>Watson Instance</th>
                <th>Status</th>
                <th>Request Date</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
    <a href="access/request">Request Access</a>
</body>
</html>