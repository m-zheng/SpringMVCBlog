<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Blog | Login</title>
        <!-- Fonts -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css" integrity="sha384-XdYbMnZ/QjLh6iI4ogqCTaIjrFk87ip+ekIjefZch0Y+PvJ8CDYtEs1ipDmPorQ+" crossorigin="anonymous">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato:100,300,400,700">
        <!-- Styles -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome-animation/0.0.10/font-awesome-animation.css" type="text/css" />
        <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />" type="text/css" />
        <style>
            body {
            font-family: 'Lato';
            }
            .fa-btn {
            margin-right: 6px;
            }
        </style>
    </head>
    <body id="app-layout">
    	<%@ include file="../layouts/nav.jsp" %>

		<div class="container">
			<div class="row">
				<div class="col-md-8 col-md-offset-2">
					<div class="panel panel-default">
						<div class="panel-heading">Login</div>
						<div class="panel-body">
	
		                   <c:if test="${requestScope.permissionErrorMessage.length()>0}">
			                   <div class="alert alert-danger">
			                       <strong>${requestScope.permissionErrorMessage}</strong>
			                   </div>
		                   </c:if>
		                   
		                   <c:if test="${requestScope.pageNotExistErrorMessage.length()>0}">
			                   <div class="alert alert-danger">
			                       <strong>${requestScope.pageNotExistErrorMessage}</strong>
			                   </div>
		                   </c:if>
	
							<sf:form class="form-horizontal" role="form" action="${pageContext.request.contextPath}/login" method="POST" modelAttribute="user">
	
								<div class="form-group <c:if test="${requestScope.inputUsernameErrorMessage.length()>0}">has-error</c:if>">
									<sf:label path="username" class="col-md-4 control-label">Username</sf:label>
	
									<div class="col-md-6">
										<sf:input path="username" type="text" class="form-control" name="username" value="${user.username}" />
										<c:if
											test="${requestScope.inputUsernameErrorMessage.length()>0}">
											<span class="help-block"> <strong>${requestScope.inputUsernameErrorMessage}</strong>
											</span>
										</c:if>
									</div>
								</div>
	
								<div
									class="form-group <c:if test="${requestScope.inputPasswordErrorMessage.length()>0 ||requestScope.wrongPasswordErrorMessage.length()>0}">has-error</c:if>">
									<sf:label path="password" for="password" class="col-md-4 control-label">Password</sf:label>
	
									<div class="col-md-6">
										<sf:input path="password" type="password" class="form-control" name="password" />
										<c:if test="${requestScope.inputPasswordErrorMessage.length()>0}">
											<span class="help-block"> <strong>${requestScope.inputPasswordErrorMessage}</strong>
											</span>
										</c:if>
	
										<c:if test="${requestScope.wrongPasswordErrorMessage.length()>0}">
											<span class="help-block"> <strong>${requestScope.wrongPasswordErrorMessage}</strong>
											</span>
										</c:if>
									</div>
								</div>
	
	
								<div class="form-group">
									<div class="col-md-6 col-md-offset-4">
										<button type="submit" class="btn btn-primary">
											<i class="fa fa-btn fa-sign-in"></i> Login
										</button>
	
									</div>
								</div>
							</sf:form>
						</div>
					</div>
				</div>
			</div>
		</div>

   		<%@ include file="../layouts/footer.jsp" %>
	
        <!-- JavaScripts -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.3/jquery.min.js" integrity="sha384-I6F5OKECLVtK/BL+8iSLDEHowSAfUo76ZL9+kGAgTRdiByINKJaqTPH/QVNS1VDb" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
        <script src="<c:url value="/resources/js/scroll.js" />"></script>
        
    </body>
</html>