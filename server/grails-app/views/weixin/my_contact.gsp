<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main_h">
		<title>我的客服</title>
	</head>
	<body>
		<div id="create-feedback" class="content container" role="main">
			<g:if test="${flash.message}">
			    <div class="alert alert-warning" role="status">${flash.message}</div>
			</g:if>
            <g:if test="${qc != null}">
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-12">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            我的客服
                        </div>

                        <div class="panel-body">
                            <h4>质检</h4>
                            <div class="row">
                                <div class="col-md-12">${qc?.uname}(电话：${qc?.telephone})</div>
                            </div>
                            <h4>监理</h4>
                            <div class="row">
                                <div class="col-md-12">${supervisor?.uname}(电话：${supervisor?.telephone})</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </g:if>
            <g:else>
                <p>没有查到与此项目相关的客服信息</p>
            </g:else>
		</div>
        <script src="<%=request.contextPath%>/js/bootstrap.js" type="text/javascript"></script>
        <script src="<%=request.contextPath%>/js/jquery-ui-1.10.4.min.js" type="text/javascript"></script>
        <script src="<%=request.contextPath%>/js/yzf.js" type="text/javascript"></script>
	</body>
</html>
