
        <div class="span6 well" id="friends_list">
            <ul class="dashboard-list">
                <h2> <@spring.message code = "user.friends"/>: <a href="<@spring.url "/friend/unknown"/>" title="<@spring.message code ="user.addfriends"/>"> <i class="icon-plus"></i></a> </h2>
                <#include "/app_modules/user_cell.ftl"/>
                <h2> <@spring.message code="friends.total"/>: ${(dto.totalUsers)!"0"} </h2>
            </ul>
        </div>






