<%@ page import="co.yzf.Project" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
        <title>阶段验收</title>
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
                            <h3 class="panel-title">验收操作</h3>
                        </div>
                        <div class="panel-body">
                            <div class="radio">
                                <label>
                                    <input type="radio" name="attitude" value="4" checked>
                                    非常满意，通过验收
                                </label>
                            </div>
                            <div class="radio">
                                <label>
                                    <input type="radio" name="attitude" value="3">
                                    满意，通过验收
                                </label>
                            </div>
                            <div class="radio">
                                <label>
                                    <input type="radio" name="attitude" value="2">
                                    通过验收
                                </label>
                            </div>
                            <div class="radio">
                                <label>
                                    <input type="radio" name="attitude" value="1">
                                    拒绝通过验收
                                </label>
                            </div>
                            <hr>
                            <div>
                                <label class="col-xs-12 col-sm-12 col-md-2">你的意见或建议：</label>
                                <div class="col-xs-12 col-sm-12 col-md-10">
                                <input type="text" id="memo" class="form-control" maxlength="200">
                                <p class="text-danger">如果验收不通过，请给出你的意见，以方便我们改善</p>
                                </div>
                            </div>
                        </div>
                        <div class="panel-footer">
                            <button type="button" class="btn btn-primary" data-loading-text="处理中..." onclick="verify($(this))">提交验收结果</button>
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
