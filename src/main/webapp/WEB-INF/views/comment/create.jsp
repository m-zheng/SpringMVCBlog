<!-- Post Comments -->
<!-- Comments Form -->
<div class="well">
    <h4>Leave a Comment:</h4>
    <c:if test="${requestScope.commentContentErrorMsg.length()>0}">
	    <div class="row">
	        <div class ="col-lg-12">
	            <div class="alert alert-danger">
	                <strong>Danger!</strong> ${requestScope.commentContentErrorMsg}
	            </div>
	        </div>
	    </div>
    </c:if>
    
    <sf:form action="${pageContext.request.contextPath}/post/${postInDB.getPostId()}" method="POST" modelAttribute="comment">
        <div class="form-group">
            <sf:label path="content">Content:</sf:label>
            <sf:textarea path="content" class="form-control" name="comment" value="" rows="3" style="resize: vertical;" />
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </sf:form>
</div>
<hr>


