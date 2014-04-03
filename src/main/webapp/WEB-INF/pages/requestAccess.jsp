<html>
<head>
    <%@ include file="header.jsp"%>
    <title>NUR - Request Access</title>
</head>
<body>

    <h1>NUR - Request Access</h1>

    <form action="access/request" method="post" class="form-horizontal col-lg-6" role="form">
        <div class="form-group">
            <label for="instance" class="col-sm-2 control-label">Instance:</label>
            <div class="col-sm-10">
                <select id="instance" name="instance">
                    <option value="apwatson">AP Watson</option>
                    <option value="eubwatson">EUB Watson</option>
                    <option value="euwatson">EU Watson</option>
                    <option value="lawatson">LA Watson</option>
                    <option value="nawatson">NA Watson</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="comments" class="col-sm-2 control-label">Comments:</label>
            <div class="col-sm-10">
                <textarea id="comments" name="comments" class="form-control" rows="3"></textarea>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-primary">Submit</button>
            </div>
        </div>
    </form>
</body>
</html>