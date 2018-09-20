<!-- Comment -->
<c:forEach var="comment" items="${commentList}">
	<div class="media" data-toggle="modal" data-target="#myModal-<c:out value="${comment.getCommentId()}" />">
	    <div class="media-body">
	        <h4 class="media-heading"><c:out value="${comment.getUser().getUsername()}" />
	            <small>
					<c:choose>
					  <c:when test="${comment.getModifiedDateTimeInStringFormate() == null}">
				  		  <c:out value="${comment.getCreatedDateTimeInStringFormate()}" />
					  </c:when>
					  <c:otherwise>
						  <c:out value="${comment.getModifiedDateTimeInStringFormate()}" />
					  </c:otherwise>
					</c:choose>
	            </small>
	        </h4>
	        <p><c:out value="${comment.getContent()}" /></p>
	    </div>
	</div>
	
	<%@ include file="../comment/edit.jsp" %>
	
</c:forEach>