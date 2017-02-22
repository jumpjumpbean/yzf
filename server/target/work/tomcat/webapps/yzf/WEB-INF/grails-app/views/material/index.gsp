<%@ page import="co.yzf.Material" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>材料列表</title>
	</head>
	<body>
		<div id="list-material" class="content container panel panel-default" role="main">
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-12">
                <h3>材料列表</h3>
                <g:if test="${flash.message}">
                    <div class="alert alert-warning" role="status">${flash.message}</div>
                </g:if>
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>材料名</th>
                            <th>材料类型</th>
                            <th>材料品牌</th>
                            <th>材料型号</th>
                            <th>计量单位</th>
                            <th>供应商</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${materialInstanceList}" status="i" var="materialInstance">
                        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                            <td>${fieldValue(bean: materialInstance, field: "name")}</td>
                            <td>${fieldValue(bean: materialInstance, field: "type")}</td>
                            <td>${fieldValue(bean: materialInstance, field: "brand")}</td>
                            <td>${fieldValue(bean: materialInstance, field: "modelno")}</td>
                            <td>${fieldValue(bean: materialInstance, field: "unit")}</td>
                            <td>${fieldValue(bean: materialInstance, field: "supplier")}</td>
                            <td>
                                <a class="btn btn-default" href="${createLink(uri: '/')}material/edit/${materialInstance.id}">编辑</a>
                                <a class="btn btn-danger" href="${createLink(uri: '/')}material/delete/${materialInstance.id}">删除</a>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
                <div class="pagination">
                    <g:paginate total="${materialInstanceCount ?: 0}" />
                </div>
                </div>
            </div>
		</div>
	</body>
</html>
