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
        <title>Blog | Edit a new post</title>
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
				<div class="col-lg-12 margin-tb">
					<div class="pull-left">
						<h2>Edit post</h2>
					</div>
					<div class="pull-right">
						<a class="btn btn-primary"
							href="${pageContext.request.contextPath}/dashboard"> Back
						</a>
					</div>
				</div>
			</div>
		
		
		    <c:if test="${requestScope.postSubjectErrorMsg.length()>0}">
		    <div class="row">
		        <div class ="col-lg-12">
		            <div class="alert alert-danger">
		                <strong>Danger!</strong> ${requestScope.postSubjectErrorMsg}
		            </div>
		        </div>
		    </div>
		    </c:if>
		    
		    <c:if test="${requestScope.postContentErrorMsg.length()>0}">
		    <div class="row">
		        <div class ="col-lg-12">
		            <div class="alert alert-danger">
		                <strong>Danger!</strong> ${requestScope.postContentErrorMsg}
		            </div>
		        </div>
		    </div>
		    </c:if>
		    
		
		    <div class="row">
		        <div class="col-lg-12">
		            <sf:form action="${pageContext.request.contextPath}/post/${postInDB.postId}/edit" method="POST" modelAttribute="postInDB">
		                <div class="form-group">
		                    <sf:label path="subject">Subject:</sf:label>
		                    <sf:input path="subject" type="text" class="form-control" name="subject" value="" />
		                </div>
		                
		                <div class="form-group">
		                    <sf:label path="content">Content:</sf:label>
		                    <sf:textarea path="content" type="text" class="form-control" name="content" value="" style = "height: 500px; resize: vertical"/>
		                </div>
		                <button type="submit" class="btn btn-primary">Submit</button>
		            </sf:form>
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