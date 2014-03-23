<!DOCTYPE html>
<html>
<head>
    <title>NUR</title>
</head>
<body>
    <form action="/NUR_POC/manager/proceed/${requestId}" method="post">
        Requester's information:
        <table>
            <tr>
                <td>Requester Domain Name:</td>
                <td>${domainUserName}</td>
            </tr>
            <tr>
                <td>Requester Email:</td>
                <td>${email}</td>
            </tr>
            <tr>
                <td>Instance:</td>
                <td>${instance}</td>
            </tr>
            <tr>
                <td>Comments:</td>
                <td>${comments}</td>
            </tr>
            <tr>
                <td>Request Date:</td>
                <td>${date}</td>
            </tr>
        </table>
        <select name="proceedAction">
            <option value="AP">Approve</option>
            <option value="DE">Deny</option>
        </select>
        Comments:
        <input type="text" name="comments" />
        <input type="submit" value="Proceed"/>
    </form>
</body>
</html>
