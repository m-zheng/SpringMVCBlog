        <nav class="navbar navbar-default navbar-static-top">
            <div class="container">
                <div class="navbar-header">
                    <!-- Collapsed Hamburger -->
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#app-navbar-collapse">
                    <span class="sr-only">Toggle Navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    </button>
                    <!-- Branding Image -->
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/">
                    Blog
                    </a>
                </div>
                <div class="collapse navbar-collapse" id="app-navbar-collapse">
                    <!-- Left Side Of Navbar -->
					<c:if test="${sessionScope.loggedUser != null}">
	                    <ul class="nav navbar-nav">
	                        <li><a href="${pageContext.request.contextPath}/dashboard">Dashboard</a></li>
	                    </ul>
					</c:if>
					
                    <!-- Right Side Of Navbar -->
                    <ul class="nav navbar-nav navbar-right">
                        <!-- Authentication Links -->
						<c:choose>
							<c:when test="${sessionScope.loggedUser == null}">
								<li><a href="${pageContext.request.contextPath}/login">Login</a></li>
								<li><a href="${pageContext.request.contextPath}/register">Register</a></li>
							</c:when>
							<c:otherwise>
				                 <li class="dropdown">
				                     <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
				                     ${sessionScope.loggedUser.getUsername()} <span class="caret"></span>
				                     </a>
				                     <ul class="dropdown-menu" role="menu">
				                         <li><a href="${pageContext.request.contextPath}/logout"><i class="fa fa-btn fa-sign-out"></i>Logout</a></li>
				                     </ul>
				                 </li>
							</c:otherwise>
						</c:choose>
                    </ul>
                </div>
            </div>
        </nav>