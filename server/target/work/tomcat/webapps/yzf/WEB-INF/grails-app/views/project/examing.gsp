<%@ page import="co.yzf.Project" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
        <title>阶段审核</title>
	</head>
	<body>
		<div class="content container" role="main">
			<g:if test="${flash.message}">
			<div class="alert alert-warning" role="status">${flash.message}</div>
			</g:if>
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">审核操作</h3>
                        </div>
                        <div class="panel-body">
                            <button type="button" class="btn btn-success" data-loading-text="处理中..." onclick="updateStatus($(this),5)">审核通过</button>
                            <button type="button" class="btn btn-warning" data-loading-text="处理中..." onclick="updateStatus($(this),4)">审核失败</button>
                            <input type="hidden" id="procstepid" value="${procstepid}">
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-12">
                    <g:if test="${material != null}">
                    <g:render template="/templates/procinfo" model="['process':process,'material':material]" />
                    </g:if>
                    <g:render template="/templates/piclist" model="['picList':picList,'editflag':0]" />
                </div>
            </div>
		</div>
	</body>
</html>
