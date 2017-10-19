	<nav class="navbar fixed-top navbar-light">
        <a href="Overview"><img class="logo" src="https://i.imgur.com/XTgvOoN.png" alt="FrugalPlanr" /></a>
        <span align="right" class="row">
        	<span class="btn" style="color: white">Logged in as <strong><em><c:out value="${userName}" /></em></strong>.</span>
	        <form action="EditProfile">
	            <input type="submit" class="btn btn-light" value="Edit Profile">
	        </form>
	        <form action="Logout">
	            <input type="submit" class="btn btn-danger" value="Logout">
	        </form>
    	</span>
    </nav>