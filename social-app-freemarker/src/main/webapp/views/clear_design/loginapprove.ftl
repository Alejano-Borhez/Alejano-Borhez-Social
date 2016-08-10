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
                <#if dto??>
                    <h2> Welcome ${(dto.user.firstName)!""} ${(dto.user.lastName)!""} ! </h2>

                    <h2>Now you can log in to your account</h2>


                <#else>
                    <h2> Sorry, something went wrong. Please try to log in again </h2>
                </#if>
                    <#include "app_modules/loginform.ftl"/>

                </div>
			</div>


	</div><!--/.fluid-container-->

</div><!--/fluid-row-->

</body>

<#include "build_modules/footer.ftl"/>

</html>
