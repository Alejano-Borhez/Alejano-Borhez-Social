<#ftl output_format="HTML">

<!doctype html>

<#include "build_modules/head.ftl"/>

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
					<#if cause??>
					<h2><mark> You've entered incorrect login or password </mark> </h2>
					<#else>
					<h2>Login to your account</h2>
					</#if>
                            <#include "app_modules/loginform.ftl"/>

                    <div class="row-fluid">
                        <h3>Forgot Password?</h3>
                        <p>
                            No problem, <a href="<@spring.url "/admin/password/requestNew"/>">click here</a> to get a new password.
                        </p>
                        <br>

                        <h3>New to Simple-Social?</h3>
                        <div class="button-login">
                         <a class="btn btn-default" href="user/add">Sign Up</a>
                        </div>
					</div>



				</div><!--/span-->
			</div><!--/row-->
			

	</div><!--/.fluid-container-->
	
		</div><!--/fluid-row-->


</body>

<#include "build_modules/footer.ftl"/>



</html>
