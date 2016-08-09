<#list dto.users as user>
<li>
    <div class="row-fluid">
        <div class="span5">
            <a href="<@spring.url "/user/${user.login}"/>">
                <img class="img-circle" width="50" height="50" alt="${user.firstName} ${user.lastName}" src="${(user.images[0].url)!"img/vk.jpg"}">
            </a>
            <strong>Name:</strong> <a href="<@spring.url "/user/${user.login}"/>">${user.firstName} ${user.lastName}</a><br>
            <strong>Since:</strong> ${user.createdDate?string('dd.MMMM')} <br>
            <strong>Friends:</strong> ${user.totalFriends} <br>
        </div>
        <div class="span4">
            <#include "actions.ftl"/>
        </div>
    </div>

</li>
<#else>
<li>
    <strong> No users yet </strong>
</li>
</#list>