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
                    <#if e_mail??>
					<h2> Check your e-mail box <strong>${e_mail}</strong> for password reset instructions </h2>
					<#else>
					<h2> You entered incorrect e-mail. Please try again </h2>
					<form class="form-horizontal" name="email" action="reset" method="post">
						<fieldset>
							
							<div class="input-prepend" title="Enter your registered email">
								<span class="add-on"><i class="halflings-icon envelope"></i></span>
								<input class="input-large span10" name="email" id="email" type="email" placeholder="type email" required="true"/>
							</div>
							<div class="clearfix"></div>

							<div class="button-login">
								<button type="submit" class="btn btn-primary">Reset Password</button>
							</div>
							<div class="clearfix"></div>
					</form>
					<hr>
					</#if>

				</div><!--/span-->
			</div><!--/row-->
			

	</div><!--/.fluid-container-->
	
		</div><!--/fluid-row-->


</body>

<#include "/build_modules/footer.ftl"/>



</html>
