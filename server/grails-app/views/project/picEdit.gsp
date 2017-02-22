<%@ page import="co.yzf.Project" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
        <title>照片整理</title>
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
                            <h3 class="panel-title">照片整理</h3>
                        </div>
                        <div class="panel-body">
                            <button type="button" class="btn btn-danger" data-loading-text="处理中..." onclick="deletePic($(this))"><span class="glyphicon glyphicon-trash text-primary"></span>删除选中的图片</button>
                            <button type="button" class="btn btn-success" data-loading-text="处理中..." onclick="updateStatus($(this),6)">整理完毕</button>
                            <p class="text-danger">小提示：点击图片可以选中，再次点击取消选中</p>
                            <input type="hidden" id="procstepid" value="${procstepid}">
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-12">
                    <g:render template="/templates/piclist" model="['picList':picList,'editflag':1]" />
                </div>
            </div>
		</div>
	</body>
</html>
