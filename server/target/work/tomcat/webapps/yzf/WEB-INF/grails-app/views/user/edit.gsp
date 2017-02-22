<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>编辑${edtuser?.uname}资料</title>
	</head>
	<body>
		<div id="page-body" role="main" class="container">
            <div class="row">
                <div class="col-xs-12 col-sm-6 col-md-8">
                    <g:form class="form-horizontal" controller="user" action="save" name="editUserFrm" method="post">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h4>编辑${edtuser?.uname}资料</h4>
                            <input type="hidden" id="edtUserId" name="uid" value="${edtuser.id}">
                        </div>
                        <div class="panel-body">
                            <div class="form-group">
                                <label class="col-sm-3 control-label" for="uname">用户姓名</label>
                                <div class="col-sm-9"><input type="text" class="form-control" name="uname" id="uname" placeholder="请输入用户姓名" value="${edtuser?.uname}"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">登录帐号</label>
                                <div class="col-sm-9">
                                    ${edtuser?.account}
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">账号密码</label>
                                <div class="col-sm-9">
                                    <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#setPasswdDlg">重置登陆信息</button>
                                    <p><small class="text-danger">注意：此处改变会强制重置密码，如果忘记，请联系管理员</small></p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label" for="address">地址</label>
                                <div class="col-sm-9"><input type="text" class="form-control" name="address" id="address" placeholder="请输入地址" value="${edtuser?.address}"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label" for="telephone">联系电话</label>
                                <div class="col-sm-9"><input type="text" class="form-control" name="telephone" id="telephone" placeholder="请输入电话" value="${edtuser?.telephone}"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label" for="description">个人备注</label>
                                <div class="col-sm-9"><input type="text" class="form-control" name="description" id="description" placeholder="请输入个人备注" value="${edtuser?.description}"></div>
                            </div>
                            <sec:ifAnyGranted roles="ROLE_SUPER">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">用户权限</label>
                                <div class="col-sm-9" id="allUserAuthes">
                                    <g:each in="${roleList}" var="role">
                                        <g:if test="${role?.authority != "ROLE_USER"}">
                                        <div class="radio">
                                            <label>
                                                <g:if test="${role?.authority == "ROLE_ADMIN"}">
                                                    <input type="radio" data-role="ROLE_ADMIN" name="uauths" value="${role?.id}">
                                                    <strong>管理员</strong>
                                                </g:if>
                                                <g:if test="${role?.authority == "ROLE_CUSTOMER"}">
                                                    <input type="radio" data-role="ROLE_CUSTOMER" name="uauths" value="${role?.id}">
                                                    <strong>客户</strong>
                                                </g:if>
                                                <g:if test="${role?.authority == "ROLE_REPO"}">
                                                    <input type="radio" data-role="ROLE_REPO" name="uauths" value="${role?.id}">
                                                    <strong>仓库管理员</strong>
                                                </g:if>
                                                <g:if test="${role?.authority == "ROLE_QC"}">
                                                    <input type="radio" data-role="ROLE_QC" name="uauths" value="${role?.id}">
                                                    <strong>质检</strong>
                                                </g:if>
                                                <g:if test="${role?.authority == "ROLE_SUPERVISOR"}">
                                                    <input type="radio" data-role="ROLE_SUPERVISOR" name="uauths" value="${role?.id}">
                                                    <strong>监理</strong>
                                                </g:if>
                                            </label>
                                        </div>
                                        </g:if>
                                    </g:each>
                                </div>
                            </div>
                            </sec:ifAnyGranted>
                        </div>
                        <div class="panel-footer">
                            <div class="form-group">
                                <div class="col-md-6 text-right">
                                    <button id="register_sub" type="button" class="btn btn-primary" onclick="$('#editUserFrm').submit()">更新用户</button>
                                    <g:link class="btn btn-link" controller="user" action="listAll">取消</g:link>
                                </div>
                                <div class="col-md-6"></div>
                            </div>
                        </div>
                    </div>
                    </g:form>
                </div>
            </div>
		</div>
        <div class="modal fade" id="setPasswdDlg">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">重置密码</h4>
                        <input type="hidden" id="hdAccount" value="${edtuser?.account}">
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <label for="acntpop" class="col-sm-2 control-label">登陆账号</label>
                            <div class="col-sm-9">
                                <input type="text" id="acntpop" class="form-control" value="${edtuser?.account}">
                                <p><small>为了便于记忆，可以使用合同号、电话，电子邮件等作为登录帐号</small></p>
                            </div>
                        </div>
                        <div class="row">
                            <label for="pswdpop" class="col-sm-2 control-label">登陆密码</label>
                            <div class="col-sm-9"><input type="password" id="pswdpop" class="form-control"></div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" data-loading-text="处理中..." class="btn btn-primary" onclick="resetPswd($(this))">确定</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div>
        <input type="hidden" id="edtUserAuth" value="${edtuser.curAuth?.id}">
    <script type="application/javascript">
        $(function(){
            filterAuth();
        });
    </script>
	</body>
</html>
