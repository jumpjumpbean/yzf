<%@ page import="co.yzf.Project" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
        <title>编辑工程材料</title>
	</head>
	<body>
		<div id="edit-project" class="content container" role="main">
			<g:if test="${flash.message}">
			<div class="alert alert-warning" role="status">${flash.message}</div>
			</g:if>
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-12">
                <g:form url="[resource:projectInstance, action:'update']" method="PUT" class="form-horizontal">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h4>编辑工程材料</h4>
                        </div>
                        <div class="panel-body">
                        <g:if test="${pmList == null}">
                            <table class="table table-hover" id="pm_list_tbl">
                                <thead>
                                <tr>
                                    <th class="col-md-2">材料类型</th>
                                    <th class="col-md-4">材料品牌和型号</th>
                                    <th class="col-md-2">所属阶段</th>
                                    <th class="col-md-1">数量</th>
                                    <th class="col-md-3">备注</th>
                                    <th class="col-md-2">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <g:each in="${allTypeList}" var="type" status="i">
                                    <tr id="mtr_${i}">
                                        <td>${type}</td>
                                        <td><g:select id="mtrs_${i}" name="material" from="${co.yzf.Material.findAllByType(type)}" optionKey="id" class="form-control" noSelection="['': '不需要此类材料']"/></td>
                                        <td><g:select id="prcs_${i}" name="process" from="${allProcs}" optionKey="id" optionValue="period" class="form-control" /></td>
                                        <td><input id="cnt_${i}" class="form-control input-sm" type="number" value="0"></td>
                                        <td><input id="memo_${i}" class="form-control input-sm" maxlength="100" value=""></td>
                                        <td>
                                            <button type="button" data-loading-text="保存中..." class="btn btn-primary btn-sm" onclick="addPM('${i}',$(this))">保存</button>
                                        </td>
                                    </tr>
                                </g:each>
                                </tbody>
                            </table>
                            <hr/>
                            <div class="text-right">
                                <button type="button" class="btn btn-primary" onclick="continueAdd()">继续追加</button>
                                <a href="<%=request.contextPath%>/project/show/${projectId}" class="btn btn-link">返回</a>
                                <input type="hidden" id="totalCnt" value="${allTypeList.size()}">
                            </div>
                        </g:if>
                        <g:else>
                            <table class="table table-hover" id="pm_list_tbl">
                                <thead>
                                <tr>
                                    <th class="col-md-1">材料类型</th>
                                    <th class="col-md-3">材料品牌和型号</th>
                                    <th class="col-md-2">所属阶段</th>
                                    <th class="col-md-1">数量</th>
                                    <th class="col-md-3">备注</th>
                                    <th class="col-md-2">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                    <g:each in="${pmList}" var="pm" status="i">
                                        <tr id="mtr_${i}">
                                            <g:render template="savem" model="['pm':pm,'indx':i]"/>
                                        </tr>
                                    </g:each>
                                </tbody>
                            </table>
                            <hr/>
                            <div class="text-right">
                                <button type="button" class="btn btn-primary" onclick="continueAdd()">继续追加</button>
                                <a href="<%=request.contextPath%>/project/show/${projectId}" class="btn btn-link">返回</a>
                                <input type="hidden" id="totalCnt" value="${pmList.size()}">
                            </div>
                        </g:else>
                        </div>
                    </div>
                </g:form>
                </div>
            </div>
		</div>
        <input type="hidden" id="projectId" value="${projectId}">
        <table style="display: none;">
            <tr id="bakTr">
                <td>${type}</td>
                <td><g:select id="mtrs_@indx@" name="material" from="${allMaterial}" optionKey="id" class="form-control" noSelection="['': '不需要此类材料']"/></td>
                <td><g:select id="prcs_@indx@" name="process" from="${allProcs}" optionKey="id" optionValue="period" class="form-control" /></td>
                <td><input id="cnt_@indx@" class="form-control input-sm" type="number" value="0"></td>
                <td><input id="memo_@indx@" class="form-control input-sm" maxlength="100" value=""></td>
                <td>
                    <button type="button" data-loading-text="保存中..." class="btn btn-primary btn-sm" onclick="addPM('@indx@',$(this))">保存</button>
                </td>
            </tr>
        </table>
	</body>
</html>
