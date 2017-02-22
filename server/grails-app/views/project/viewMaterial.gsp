<%@ page import="co.yzf.Project" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
        <title>查看工程材料</title>
	</head>
	<body>
		<div id="edit-project" class="content container" role="main">
			<g:if test="${flash.message}">
			<div class="alert alert-warning" role="status">${flash.message}</div>
			</g:if>
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-12">
                    <div class="panel panel-info">
                        <div class="panel-body">
                        <div>
                            <h4>工程材料清单</h4>
                        </div>
                        <hr/>
                        <g:if test="${pmList != null && pmList.size() > 0}">
                            <table class="table table-hover" id="pm_list_tbl">
                                <thead>
                                <tr>
                                    <th class="col-md-2">材料类型</th>
                                    <th class="col-md-2">材料品牌</th>
                                    <th class="col-md-2">材料型号</th>
                                    <th class="col-md-2">所属阶段</th>
                                    <th class="col-md-1">数量</th>
                                    <th class="col-md-3">备注</th>
                                </tr>
                                </thead>
                                <tbody>
                                <g:each in="${pmList}" var="pm">
                                    <tr>
                                        <td>${pm?.material?.type}</td>
                                        <td>${pm?.material?.brand}</td>
                                        <td>${pm?.material?.modelno?:'-'}</td>
                                        <td>${pm?.process?.period?:'-'}</td>
                                        <td>${pm?.cnt}<small>${pm?.material?.unit}</small></td>
                                        <td>${pm?.memo}</td>
                                    </tr>
                                </g:each>
                                </tbody>
                            </table>
                        </g:if>
                        <g:else>
                            <p>暂未添加材料</p>
                        </g:else>
                        </div>
                        <div class="panel-footer text-right hidden-print">
                            <a class="btn btn-primary" href="javascript:window.print();">打印</a>
                            <a href="<%=request.contextPath%>/project/show/${projectId}" class="btn btn-link">返回</a>
                        </div>
                    </div>
                </div>
            </div>
		</div>
        <input type="hidden" id="projectId" value="${projectId}">
	</body>
</html>
