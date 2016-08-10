<#list dto.users as user>
<div class="row well" id="friend_cell_${user.login}" style="margin-left: 5px;">
    <div class="span2" ontablet="span6" ondesktop="span3">
        <!-- Friend's avatar -->
        <img class="grayscale" src="<#if user.images[0]??> ${(user.images[0].url)!"#"} <#else> <@spring.url "/img/vk.jpg"/> </#if>"></img>
    </div>
        <!-- Friends's credentials -->
    <div class="span3">
        <strong>Name:</strong> <a href="<@spring.url "/user/find/${user.login}"/>">${user.firstName} ${user.lastName}</a><br>
        <strong>Since:</strong> ${user.createdDate?string('dd.MMMM')} <br>
    </div>
    <div class="span2">

        <#include "actions.ftl"/>

    </div>

</div>
<#else>
<#switch mapping>
<#case "friends">
У вас пока нет друзей
<#break>
<#case "nofriends">
У вас нет незнакомых пользователей
<#break>
</#switch>
</#list>

