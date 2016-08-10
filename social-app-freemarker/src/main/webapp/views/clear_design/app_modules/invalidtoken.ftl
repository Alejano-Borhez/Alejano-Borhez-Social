<#ftl output_format="HTML">

<!doctype html>

<#include "/build_modules/head.ftl"/>

        <style type="text/css">
             body { background: url(<@spring.url "/img/bg-login.jpg"/>) !important; }
		</style>

<body>
		<div class="container-fluid-full">
		<div class="row-fluid">
					
			<div class="row-fluid">
				<div class="login-box">
					<div class="icons">
						<a href="<@spring.url "/"/>"><i class="halflings-icon home"></i></a>
					</div>
					<h2>Sorry, your security token is invalid. Try to log in the system again</h2>

					<#include "loginform.ftl"/>

					<h3>Forgot Password?</h3>
					<p>
						No problem, <a href="<@spring.url "/admin/password/requestNew"/>">click here</a> to get a new password.
					</p>

				</div><!--/span-->
			</div><!--/row-->
			

	</div><!--/.fluid-container-->
	
</div><!--/fluid-row-->


</body>

<#include "/build_modules/footer.ftl"/>



</html>
