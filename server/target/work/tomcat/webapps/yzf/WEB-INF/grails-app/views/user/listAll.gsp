<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>用户列表</title>
</head>

<body>
<div id="page-body" role="main" class="container">
    <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-8">
            <ul class="list-unstyled">
                <g:each in="${allUser}" var="user">
                    <li class="panel panel-info">
                        <div class="media panel-body shadow">
                            <a class="pull-left" href="#">
                                <div><img class="media-object img-circle avatar" src="${resource(dir: 'img', file: 'avatar.png')}"></div>
                            </a>
                            <div class="media-body">
                                <h4 class="media-heading">
                                    ${user.uname}
                                    <g:if test="${user.id == curUser.id}">
                                        <g:link class="btn btn-warning btn-sm pull-right" action="edit">编辑</g:link>
                                    </g:if>
                                    <g:else>
                                        <sec:ifAnyGranted roles="ROLE_SUPER,ROLE_ADMIN">
                                            <g:link class="btn btn-warning btn-sm pull-right" action="edit" id="${user.id}">编辑</g:link>
                                        </sec:ifAnyGranted>
                                    </g:else>
                                </h4>
                                <table class="table table-hover">
                                    <tr>
                                        <td>电话（<span class="glyphicon glyphicon-phone-alt"></span>）：${user.telephone}</td>
                                        <td>住址（<span class="glyphicon glyphicon-home"></span>）：${user.address}</td>
                                    </tr>
                                    <tr>
                                        <td>帐号（<span class="glyphicon glyphicon-user"></span>）：${user.account}</td>
                                        <td>备注（<span class="glyphicon glyphicon-comment"></span>）：${user.description}</td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <div class="text-info">用户权限：-
                                            <g:each in="${user?.authorities}" var="auth">
                                                <g:if test="${auth?.authority == "ROLE_ADMIN"}">
                                                    <strong>管理员</strong>
                                                </g:if>
                                                <g:if test="${auth?.authority == "ROLE_CUSTOMER"}">
                                                    <strong>客户</strong>
                                                </g:if>
                                                <g:if test="${auth?.authority == "ROLE_REPO"}">
                                                    <strong>仓库管理员</strong>
                                                </g:if>
                                                <g:if test="${auth?.authority == "ROLE_QC"}">
                                                    <strong>质检</strong>
                                                </g:if>
                                                <g:if test="${auth?.authority == "ROLE_SUPERVISOR"}">
                                                    <strong>监理</strong>
                                                </g:if>
                                            </g:each>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </li>
                </g:each>
            </ul>
            <div id="paginate">
                <g:paginate controller="Task" action="${actionname }" params="${params}" next="下一页" prev="上一页" total="1"/>
            </div>
        </div>
    </div>
    </div>
</body>
</html>