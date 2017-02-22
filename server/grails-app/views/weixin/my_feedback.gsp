<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main_h">
		<title>问题反馈</title>
	</head>
	<body>
		<div id="create-feedback" class="content container" role="main">
			<g:if test="${flash.message}">
			    <div class="alert alert-warning" role="status">${flash.message}</div>
			</g:if>
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-12" id="discontent">
                <g:form url="[action:'my_wxsave']" class="form-horizontal" id="feedbackFrm">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            问题反馈
                        </div>
                        <fieldset class="form">
                            <g:render template="form"/>
                        </fieldset>
                        <div class="panel-footer">
                            <div class="form-group">
                                <div class="col-md-6 text-right">
                                <fieldset class="buttons">
                                    <button type="button" onclick="submitWxFb()" class="save btn btn-primary">提交问题</button>
                                </fieldset>
                                </div>
                            </div>
                        </div>
                    </div>
                </g:form>
                </div>
            </div>
		</div>
        <script src="<%=request.contextPath%>/js/bootstrap.js" type="text/javascript"></script>
        <script src="<%=request.contextPath%>/js/jquery-ui-1.10.4.min.js" type="text/javascript"></script>
        <script src="<%=request.contextPath%>/js/jquery.form.js" type="text/javascript"></script>
        <script src="<%=request.contextPath%>/js/yzf.js" type="text/javascript"></script>
	</body>
</html>
