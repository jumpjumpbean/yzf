<%
    String contextPath = request.contextPath;
%>
<!doctype html>
<html>
  <head>
    <title>登录</title>
     <meta name="layout" content="main_h">
    <meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
  </head>
  
  <body>
  <div class="container" style="padding-top: 40px;">
      <div calss="row">
          <div class="hidden-xs col-sm-1 col-md-3"></div>
          <div class="col-xs-12 col-sm-10 col-md-5">
              <div class="panel panel-default">
                  <div class="panel-heading text-center" style="height:80px;background: #76C68c;">
                    <h3><img class="pull-right" src="${resource(dir: 'img', file: 'logo.png')}"><strong>业之峰工程管理平台</strong></h3>
                  </div>
                  <div class="panel-body">
                      <div style="color: red">${flash.message}</div>
                      <form role="form" action="${contextPath}/j_spring_security_check" method="POST" id="login_form">
                          <div class="form-group">
                              <div class="input-group">
                                  <span class="input-group-addon">帐号</span>
                                  <input type="text" class="form-control" placeholder="account@xxx.com" name="j_username" id="loginUsername"
                                         onkeydown="onKeyDownFun(this)"
                                         onblur="onBlurFun(this)"
                                         onchange="$(this).val( $.trim( $(this).val() ));"/>
                              </div>
                          </div>
                          <div class="form-group">
                              <div class="input-group">
                                  <span class="input-group-addon">密码</span>
                                  <input type="password" class="form-control" placeholder="Password" value="" name="j_password" id="loginPassword"
                                         onkeydown="onKeyDownFun(this)"
                                         onblur="onBlurFun(this)"
                                         onkeypress="if(event.keyCode==13) {login();return false;}"/>
                              </div>
                          </div>
                          <div class="form-group">
                              <label class="checkbox">
                                  <input type="checkbox" class="pull-left">保持登录</label>
                          </div>
                          <div class="form-group button_holder">
                              <a href="javascript:;" class="btn login-btn button" onclick="login();">登录</a>
                          </div>
                      </form>
                  </div>
              </div>
          </div>
          <div class="hidden-xs col-sm-1 col-md-4"></div>
      </div>
  </div>

<script type="text/javascript">
    function onBlurFun(elem){
        var item = $(elem);
        if(item.val() == ""){
            item.parent().find(".label").removeClass("input-focus");
        }
    }
    function onFocusFun(elem){
        var item = $(elem);
        if(item.val() != ""){
            item.parent().find(".label").removeClass("input-focus").addClass("input-focus");
        }
    }
    function onKeyDownFun(elem){
        var item = $(elem);
        if(item.val() == ""){
            item.parent().find(".label").removeClass("input-focus").addClass("input-focus");
        }
    }
    function login(){
        document.getElementById("login_form").submit();
    }
</script>
</body>
</html>
