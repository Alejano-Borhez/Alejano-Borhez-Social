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
					<#if dto??>
					<#if reset??>
					<h2> Hello ${dto.user.firstName} ${dto.user.lastName}! <br>
					<mark> Your new password did not match. </mark> <br>
					Enter your <strong> New </strong> password: </h2>
					<#else>
					<h2> Hello ${dto.user.firstName} ${dto.user.lastName}! You have reset your password. Enter your <strong> new </strong> password: </h2>
					</#if>
					<form class="form-horizontal" name="passwords" action="change" method="post">
						<fieldset>

							<div class="input-prepend" title="Enter your new password">
								<span class="add-on"><i class="halflings-icon lock"></i></span>
								<input class="input-large span10" name="password1" id="password1" type="password" placeholder="type password" required="true"/>
							</div>
							<div class="clearfix"></div>

                            <div class="input-prepend" title="Enter your new password">
                                <span class="add-on"><i class="halflings-icon lock"></i></span>
                                <input class="input-large span10" name="password2" id="password2" type="password" placeholder="repeat password" required="true"/>
                            </div>
                            <div class="clearfix"></div>
                            <div class="input">
                                <input class="input-large span10" name="token" id="token" type="hidden" value="${(token)!""}" required="true"/>
                            </div>

							<div class="button-login">
								<button type="submit" class="btn btn-primary">Change Password</button>
							</div>
							<div class="clearfix"></div>
					</form>

					<hr>
					<#else>
					<h2 class="text-danger"> Something went wrong. <strong> Try to visit our <a href="<@spring.url="login"/>">login page</a>.</strong> </h2>
					</#if>

				</div><!--/span-->
			</div><!--/row-->
			

	</div><!--/.fluid-container-->
	
		</div><!--/fluid-row-->


</body>

<#include "/build_modules/footer.ftl"/>



</html>
