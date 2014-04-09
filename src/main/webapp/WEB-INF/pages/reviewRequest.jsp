<html>
<head>
    <%@ include file="header.jsp"%>
    <title>NUR - Review Request</title>
</head>
<body>
    <form action="proceed/${request.requestId}" method="post" class="form-horizontal" role="form">
        <h3 class="sub-header">Requester's information:</h3>
        <div class="form-group">
            <label class="col-sm-2 control-label">Domain Username:</label>
            <div class="col-sm-10">
                <p class="form-control-static">${request.domainUserName}</p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">Email:</label>
            <div class="col-sm-10">
                <p class="form-control-static">${request.email}</p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">Watson Instance:</label>
            <div class="col-sm-10">
                <p class="form-control-static">${request.watsonInstance}</p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">Request Date:</label>
            <div class="col-sm-10">
                <p class="form-control-static">${request.requestDate}</p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">Comments:</label>
            <div class="col-sm-10">
                <p class="form-control-static">${request.comments}</p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">Proceed:</label>
            <div class="col-sm-2">
                <select name="proceedAction" class="form-control">
                    <option value="AP">Approve</option>
                    <option value="DE">Deny</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">Comments:</label>
            <div class="col-sm-10">
                <textarea name="comments" class="form-control" rows="3"></textarea>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Submit</button>
            </div>
        </div>
    </form>
</body>
</html>
