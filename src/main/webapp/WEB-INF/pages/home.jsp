<html>
<head>
    <title>NUR</title>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <link rel="stylesheet" href="resources/css/bootstrap.min.css"/>
</head>
<body>

    Welcome, ${domainUserName}! This is the Watson User Adminstration Page.
    <br/><br/>
    &gt;If you are a WATSON USER, you can use this page to view your current access status or you can request access to a particular regional Watson instance.
    <br/>
    &gt;If you are the MANAGER of a Watson User, you can view and approve access requests created by your employees.
    <br/>
    &gt;If you are a WATSON REGIONAL REP or a WATSON SUPERUSER (APJ region only), you will be able to review and approve access requests to the Watson instance corresponding to your geographical region.
    <br/>
    <br/>

    <table>
        <tr>
            <td colspan="3">Your current access details</td>
        </tr>
        <tr>
            <td>Watson Instance</td>
            <td>Status</td>
            <td>Last Access Date</td>
        </tr>
        <tr>
            <td colspan="3">Your open access requests</td>
        </tr>
        <tr>
            <td>Watson Instance</td>
            <td>Status</td>
            <td>Request Date</td>
        </tr>
        <tr>
            <td colspan="3">Access requests awaiting your approval</td>
        </tr>
        <tr>
            <td>Watson Instance</td>
            <td>Status</td>
            <td>Request Date</td>
        </tr>
    </table>
    <a href="access/request">Request Access</a>
</body>
</html>