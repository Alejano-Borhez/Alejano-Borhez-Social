<#ftl output_format="HTML">

<!doctype html>

<#include "build_modules/head.ftl"/>

<body>
<#if dto??>


<div class="container-fluid-full">
    <div class="row-fluid">
            <#include "build_modules/header.ftl"/>
    </div>

    <div class="row-fluid" style="padding-top: 44px; padding-bottom: 44px;">
            <#include "build_modules/menu.ftl"/>
<!-- start: Content -->
    <div id="content" class="span6">
            <h1> Gallery of a user: ${dto.user.firstName} ${dto.user.lastName} </h1>

            <#include "app_modules/gallery.ftl"/>
    </div>

<!-- end: Content -->
    </div>
    </div>


</div>


</body>
<#else>
<div>
<#include "app_modules/invalidtoken.ftl"/>
</div>

</#if>
<#include "build_modules/footer.ftl"/>



</html>