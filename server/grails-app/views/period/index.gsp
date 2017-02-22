
<%@ page import="co.yzf.Period" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>工程阶段列表</title>
	</head>
	<body>
		<div id="list-period" class="content container panel panel-default" role="main">
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-12">
                    <h3>工程阶段列表</h3>
                    <g:if test="${flash.message}">
                        <div class="alert alert-warning" role="status">${flash.message}</div>
                    </g:if>
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>发生先后顺序</th>
                                <th>工程阶段名</th>
                                <th>备注</th>
                                <th>是否有材料</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        <g:each in="${periodInstanceList}" status="i" var="periodInstance">
                            <tr>
                                <td>${fieldValue(bean: periodInstance, field: "orderNo")}</td>
                                <td>${fieldValue(bean: periodInstance, field: "name")}</td>
                                <td>${fieldValue(bean: periodInstance, field: "memo")}</td>
                                <td><g:if test="${periodInstance?.hasMaterial == 1}">是</g:if><g:else>否</g:else></td>
                                <td>
                                    <a class="btn btn-default" href="${createLink(uri: '/')}period/edit/${periodInstance.id}">编辑</a>
                                    <a class="btn btn-danger" href="${createLink(uri: '/')}period/delete/${periodInstance.id}">删除</a>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                    <div class="pagination">
                        <g:paginate total="${periodInstanceCount ?: 0}" />
                    </div>
                </div>
            </div>
		</div>
	</body>
</html>
