<#ftl output_format="HTML">

<!doctype html>

<#include "build_modules/head.ftl"/>

<body>


<div class="container-fluid-full">
    <div class="row-fluid">
            <#include "build_modules/header.ftl"/>
    </div>

    <div class="row-fluid" style="padding-top: 44px; padding-bottom: 44px;" >
            <#include "build_modules/menu.ftl"/>
<!-- start: Content -->
    <div id="content" class="span6">
            <h1>
              <@spring.message code = "user.friends"/>
              <small> ${dto.user.firstName} ${dto.user.lastName} </small>
              <span class="badge">${dto.totalUsers}</span>
              <span> <a href="nofriends" title="<@spring.message code ="user.addfriends"/>"> <i class="icon-plus"></i></a> </span>
            </h1>
        <div class = "row-fluid">
            <#include "app_modules/friend_cell.ftl"/>
        </div>
        <div class = "row-fluid">
            <div class = "span2">
                <a class="btn btn-primary btn-block align-center" href="nofriends"><@spring.message code="friend.add"/></a>
            </div>
        </div>
    </div>

<!-- end: Content -->
    </div>


</div>


</body>

<#include "build_modules/footer.ftl"/>



</html>