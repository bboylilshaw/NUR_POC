<html>
<head>
    <title>NUR</title>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <link rel="stylesheet" href="resources/css/bootstrap.min.css"/>
</head>
<body>
    <h1>NUR</h1>

    <form action="login" method="post">
        DomainUserName: <input type="text" name="domainUserName"/><br/>
        Password: <input type="password" name="password"/><br/>
        <input type="submit" value="Submit"/>
    </form>
</body>
</html>
