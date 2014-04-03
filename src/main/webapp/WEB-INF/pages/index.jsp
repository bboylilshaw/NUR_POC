<html>
<head>
    <%@ include file="header.jsp"%>
    <title>NUR - Welcome</title>
</head>
<body>
    <div class="container">

        <form class="form-signin" role="form" action="login" method="post">
            <h2 class="form-signin-heading">Login NUR:</h2>
            <input type="email" name="email" class="form-control" placeholder="Email" required autofocus>
            <input type="password" name="password" class="form-control" placeholder="Password" required>
            <label class="checkbox">
                <input type="checkbox" value="remember-me"> Remember me
            </label>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
        </form>

    </div> <!-- /container -->
</body>
</html>
