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
            <h1> <@spring.message code="users.all"/>: </h1>
        <div class="row-fluid sortable">

        <div class="span6" id="users_list">
            <ul class="dashboard-list">
                <#include "/app_modules/user_cell.ftl"/>
                <h2> <@spring.message code="users.total"/>: ${dto.totalUsers} </h2>
            </ul>

        </div>

        </div>
    </div>

<!-- end: Content -->
    </div>
    </div>


</div>

<#else>
<div>
<#include "app_modules/invalidtoken.ftl"/>
</div>

</#if>
</body>

<#include "build_modules/footer.ftl"/>



</html>