<form class="form-horizontal" name="user" action="<@spring.url "/loginapprove"/>" method="post">
    <fieldset>

        <div class="input-prepend" title="Login">
            <span class="add-on"><i class="halflings-icon user"></i></span>
            <input class="input-large span10" name="login" id="login" type="text" placeholder="type login" required="true"/>
        </div>
        <div class="clearfix"></div>

        <div class="input-prepend" title="Password">
            <span class="add-on"><i class="halflings-icon lock"></i></span>
            <input class="input-large span10" name="password" id="password" type="password" placeholder="type password" required="true"/>
        </div>
        <div class="clearfix"></div>

        <div class="button-login">
            <button type="submit" class="btn btn-primary">Login</button>
        </div>
        <div class="clearfix"></div>
</form>
<hr>