<html>
<head>
    <%@ include file="header.jsp"%>
    <title>NUR - Welcome</title>
</head>
<body>
    <div class="container">

        <form class="form-signin" role="form" action="login" method="post">
            <h2 class="form-signin-heading">Please sign in</h2>
            <input type="text" name="domainUserName" class="form-control" placeholder="DomainUserName" required autofocus>
            <input type="password" name="password" class="form-control" placeholder="Password" required>
            <label class="checkbox">
                <input type="checkbox" value="remember-me"> Remember me
            </label>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
        </form>

    </div> <!-- /container -->
</body>
</html>
