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
        <title>Blog | Show a new post</title>
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

		<!-- Page Content -->
		<div class="container">
		    <div class="row">
		        <!-- Post Content Column -->
		        <div class="col-lg-8">
		            <!-- Post Subject -->
		            <h1>${postInDB.getSubject()}</h1>
		            <!-- Author -->
		            <p class="lead">
		                Posted by ${postInDB.getUser().getUsername()}
		            </p>
		            
					<c:choose>
					  <c:when test="${isLikedByLoggedUser}">
							<form method="POST" action="${pageContext.request.contextPath}/post/${postInDB.getPostId()}/downvote">
							    <button style="border:1px solid white; background-color: transparent;">
							        <div class="list-inline text-left">
							               <i class="fa fa-thumbs-down fa-wrench faa-wrench animated-hover" aria-hidden="true"></i>
							            <span>Likes |  ${numberOfLike}</span>
							        </div>
							    </button>
							</form>
					  </c:when>
					  <c:otherwise>
				            <form method="POST" action="${pageContext.request.contextPath}/post/${postInDB.getPostId()}/upvote">
				                <button style="border:1px solid white; background-color: transparent;">
				                    <div class="list-inline text-left">
			                            <i class="fa fa-thumbs-up fa-wrench faa-wrench animated-hover" aria-hidden="true"></i>
				                        <span>Likes |  ${numberOfLike}</span>
				                    </div>
				                </button>
				            </form>
					  </c:otherwise>
					</c:choose>
		            <hr>
		            <!-- Date/Time -->
		            <p>
		                <span class="glyphicon glyphicon-time"></span>
		                Posted on
		                ${postInDB.getCreatedDateTimeInStringFormate()}
		            </p>
		            <hr>
		            <!-- Post Content -->
		            <p class="lead"> ${postInDB.getContent()}</p>
		            <hr>
		            
		            <%@ include file="../comment/create.jsp" %>
		            <%@ include file="../comment/index.jsp" %>
		        </div>
		        <div class="col-lg-4">
		            <div class="pull-right">
						<c:choose>
							<c:when test="${sessionScope.loggedUser == null}">
								<a class="btn btn-primary" href="${pageContext.request.contextPath}/">
							</c:when>
							<c:otherwise>
								<a class="btn btn-primary" href="${pageContext.request.contextPath}/dashboard">
							</c:otherwise>
						</c:choose>
		                Back
		                </a>
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