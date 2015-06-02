			<div class="container">
				<c:url var="billsUrl" value="#" />
				<c:url var="categoriesUrl" value="/ui/category-ui" />
				<c:url var="customersUrl" value="/ui/customer-ui" />
				<div class="row">
					<div class="span12">
						<h1>Pos Demo</h1>
						<div class="navbar">
						  <div class="navbar-inner">
						    <ul class="nav">
						      <li class="dropdown">
						      	<a class="dropdown-toggle" data-toggle="dropdown" href="#">
						      		File <b class="caret"></b>
						      	</a>
						      	<ul class="dropdown-menu">
									<li><a href="${categoriesUrl}">Categories</a></li>
									<li><a href="${customersUrl}">Customers</a></li>
						      	</ul>
						      </li>
						      
						      <li><a href="${billsUrl}">Bills</a></li>
						    </ul>
						  </div>
						</div>
					</div>
				</div>
			</div>