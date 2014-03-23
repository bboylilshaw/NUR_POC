<!DOCTYPE html>
<html>
<head>
    <title>NUR</title>
</head>
<body>
<h1>NUR</h1>

<form action="${pageContext.request.contextPath}/access/request" method="post">
    <select name="instance">
        <option value="apwatson">AP Watson</option>
        <option value="eubwatson">EUB Watson</option>
        <option value="euwatson">EU Watson</option>
        <option value="lawatson">LA Watson</option>
        <option value="nawatson">NA Watson</option>
    </select>
    DomainUserName: <input type="text" name="domainUserName"/>
    Comments: <input type="text" name="comments"/>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>