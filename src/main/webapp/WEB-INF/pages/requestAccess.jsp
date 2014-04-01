<html>
<head>
    <title>NUR</title>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <link rel="stylesheet" href="resources/css/bootstrap.min.css"/>
</head>
<body>
<h1>NUR - Request Access</h1>

<form action="access/request" method="post">
    <select name="instance">
        <option value="apwatson">AP Watson</option>
        <option value="eubwatson">EUB Watson</option>
        <option value="euwatson">EU Watson</option>
        <option value="lawatson">LA Watson</option>
        <option value="nawatson">NA Watson</option>
    </select>
    <br/>
    Comments: <input type="text" name="comments"/>
    <br/>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>