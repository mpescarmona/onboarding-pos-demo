			<div class="container">
				<c:url var="recipesUrl" value="/ui/recipes" />
				<c:url var="categoriesUrl" value="/ui/categories" />
				<c:url var="measuresUrl" value="/ui/measures" />
				<c:url var="toolsUrl" value="/ui/tools" />
				<c:url var="occasionsUrl" value="/ui/occasions" />
				<c:url var="originsUrl" value="/ui/origins" />
				<c:url var="seasonsUrl" value="/ui/seasons" />
				<c:url var="usersUrl" value="/ui/users" />
				<c:url var="abbrevsUrl" value="/ui/abbrevs" />
				<div class="row">
					<div class="span12">
						<h1>Seriouschef</h1>
						<div class="navbar">
						  <div class="navbar-inner">
						    <ul class="nav">
						      <li class="dropdown">
						      	<a class="dropdown-toggle" data-toggle="dropdown" href="#">
						      		File <b class="caret"></b>
						      	</a>
						      	<ul class="dropdown-menu">
									<li><a href="${categoriesUrl}">Categories</a></li>
									<li><a href="${measuresUrl}">Measures</a></li>
									<li><a href="${toolsUrl}">Tools</a></li>
									<li><a href="${occasionsUrl}">Occasions</a></li>
									<li><a href="${originsUrl}">Origins</a></li>
									<li><a href="${seasonsUrl}">Seasons</a></li>
									<li><a href="${usersUrl}">Users</a></li>
									<li><a href="${abbrevsUrl}">Ingredients</a></li>
						      	</ul>
						      </li>
						      
						      <li><a href="${recipesUrl}">Recipes</a></li>
						    </ul>
						  </div>
						</div>
					</div>
				</div>
			</div>