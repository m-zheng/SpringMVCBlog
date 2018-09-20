<div class="modal fade" id="myModal-<c:out value="${comment.getCommentId()}" />" tabindex="-1" role="dialog" aria-labelledby="myModalLabel-<c:out value="${comment.getCommentId()}" />">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel-<c:out value="${comment.getCommentId()}" />">Comment Dashboard</h4>
            </div>
            
			<c:choose>
		  		<c:when test="${comment.getUser().getUserId() == sessionScope.loggedUser.getUserId() }">
		            <div class="modal-body">
		                <ul class="nav nav-pills">
		                    <li class="active"><a data-toggle="pill" href="#edit-comment-<c:out value="${comment.getCommentId()}" />">Edit</a></li>
		                    <li><a data-toggle="pill" href="#delete-comment-<c:out value="${comment.getCommentId()}" />">Delete</a></li>
		                </ul>
		                <div class="tab-content">
		                    <div id="edit-comment-<c:out value="${comment.getCommentId()}" />" class="tab-pane fade in active">
		                        <sf:form method="POST" action="${pageContext.request.contextPath}/comment/${comment.getCommentId()}/edit" modelAttribute="comment${comment.getCommentId()}InDB">
		                            <div class="form-group">
		                                <br>
		                                 <sf:label path="content">Content:</sf:label>
		                                 <sf:textarea path="content" class="form-control" name = "comment_content" style = "height: 250px; resize: vertical;" value=""/>
		                            </div>
		                            <button type="submit" class="btn btn-primary">Submit</button>
		                        </sf:form>
		                    </div>
		                    <div id="delete-comment-<c:out value="${comment.getCommentId()}" />" class="tab-pane fade">
		                        <form method="POST" action="${pageContext.request.contextPath}/comment/<c:out value="${comment.getCommentId()}" />/delete">
		                            <div class="form-group">
		                                <br>
		                                <p><strong>Warning!!!</strong> This action will delete the comment.</p>
		                            </div>
		                            <button type="submit" class="btn btn-primary" onclick="return confirm('Are you sure?')">Delete</button>
		                        </form>
		                    </div>
		                </div>
		            </div>
            	</c:when>
            	<c:otherwise>
		            <div class="modal-body">
		                <div class="alert alert-danger">
		                    <p><strong>Sorry!</strong> You have no permission to do this.</p>
		                </div>
		            </div>
           		</c:otherwise>
            </c:choose>
            
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

