<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>德州业之峰</title>
	</head>
	<body>
		<div id="page-body" role="main" class="container">
            <div class="row">
                <div class="col-xs-12 col-sm-6 col-md-8">
                    <g:if test="${flash.message != null}">
                        <div class="alert alert-warning" role="status">${flash.message}</div>
                    </g:if>
                    <g:form class="form-horizontal" controller="user" action="addUser" name="addUserFrm" method="post">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h4>添加用户</h4>
                        </div>
                        <div class="panel-body">
                            <div class="form-group">
                                <label class="col-sm-3 control-label" for="uname">用户姓名</label>
                                <div class="col-sm-9"><input type="text" class="form-control" name="uname" id="uname" placeholder="请输入用户姓名"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label" for="account">登录帐号</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" name="account" id="account" placeholder="请输入帐号">
                                    <p><small>为了便于记忆，可以使用合同号、电话，电子邮件等作为登录帐号</small></p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label" for="password">初始密码</label>
                                <div class="col-sm-9"><input type="password" class="form-control" name="password" id="password" placeholder="请输入初始密码"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label" for="address">地址</label>
                                <div class="col-sm-9"><input type="text" class="form-control" name="address" id="address" placeholder="请输入地址"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label" for="telephone">联系电话</label>
                                <div class="col-sm-9"><input type="text" class="form-control" name="telephone" id="telephone" placeholder="请输入电话"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label" for="description">个人备注</label>
                                <div class="col-sm-9"><input type="text" class="form-control" name="description" id="description" placeholder="请输入个人备注"></div>
                            </div>
                        </div>
                        <div class="panel-footer">
                            <div class="form-group">
                                <div class="col-md-6 text-right">
                                    <button id="register_sub" type="button" class="btn btn-primary" onclick="$('#addUserFrm').submit()">添加用户</button>
                                    <button type="button" class="btn btn-link">取消</button>
                                </div>
                                <div class="col-md-6"></div>
                            </div>
                        </div>
                    </div>
                    </g:form>
                </div>
            </div>
		</div>
	</body>
</html>
