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
        <title>Blog | Welcome</title>
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
    	
		<!-- Page Header -->
		<header class="intro-header" style="background-image: url('<c:url value="/resources/img/home-bg.jpg" />')">
		    <div class="container">
		        <div class="row">
		            <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1">
		                <div class="site-heading">
		                    <h1>What's happening?</h1>
		                    <hr class="small">
		                    <span class="subheading text-capitalize">Let's share wonderful things.</span>
		                </div>
		            </div>
		        </div>
		    </div>
		</header>
		
		<!-- Main Content -->
		<div class="container">
		    <div class="row">
		        <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1">
					<c:forEach var="post" items="${postList}">
						<div class="post-preview">
			               <a href="${pageContext.request.contextPath}/post/<c:out value="${post.getPostId()}" />">
			                   <h2 class="post-title">
			                       <c:out value="${post.getSubject()}" />
			                   </h2>
			                   <h3 class="post-subtitle">
			                       <c:out value="${post.getContent()}" />
			                   </h3>
			               </a>
			                <p class="post-meta">
			                    Posted by 
			                    <strong class="text-capitalize"><c:out value="${post.getUser().getUsername()}" /></strong>
			                    on
								<c:choose>
								  <c:when test="${post.getModifiedDateTimeInStringFormate() == null}">
							  		  <c:out value="${post.getCreatedDateTimeInStringFormate()}" />
								  </c:when>
								  <c:otherwise>
									  <c:out value="${post.getModifiedDateTimeInStringFormate()}" />
								  </c:otherwise>
								</c:choose>
			                </p>
							<c:choose>
							  <c:when test="${likeDetails.get(post.getPostId())[0] == 1}">
									<form method="POST" action="${pageContext.request.contextPath}/post/${post.getPostId()}/downvote">
									    <button style="border:1px solid white; background-color: transparent;">
									        <div class="list-inline text-left">
									               <i class="fa fa-thumbs-down fa-wrench faa-wrench animated-hover" aria-hidden="true"></i>
									            <span>Likes |  ${likeDetails.get(post.getPostId())[1]}</span>
									        </div>
									    </button>
									</form>
							  </c:when>
							  <c:otherwise>
						            <form method="POST" action="${pageContext.request.contextPath}/post/${post.getPostId()}/upvote">
						                <button style="border:1px solid white; background-color: transparent;">
						                    <div class="list-inline text-left">
					                            <i class="fa fa-thumbs-up fa-wrench faa-wrench animated-hover" aria-hidden="true"></i>
						                        <span>Likes |  ${likeDetails.get(post.getPostId())[1]}</span>
						                    </div>
						                </button>
						            </form>
							  </c:otherwise>
							</c:choose>
						</div>
						<hr>
					</c:forEach>
		            
		            <!-- Pager -->
		            <ul class="pager">
		                <li class="next">
		                    <a id="back-to-top">Back to top</a>
		                </li>
		            </ul>
		        </div>
		    </div>
		</div>
		<hr>
    	
   		<%@ include file="../layouts/footer.jsp" %>
	
        <!-- JavaScripts -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.3/jquery.min.js" integrity="sha384-I6F5OKECLVtK/BL+8iSLDEHowSAfUo76ZL9+kGAgTRdiByINKJaqTPH/QVNS1VDb" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
        <script src="<c:url value="/resources/js/scroll.js" />"></script>
        
    </body>
</html>