<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main_h">
		<title>装修进度</title>
	</head>
	<body>
		<div id="create-feedback" class="content container" role="main">
			<g:if test="${flash.message}">
			    <div class="alert alert-warning" role="status">${flash.message}</div>
			</g:if>
            <g:if test="${process != null}">
                <div class="row">
                    <div class="col-xs-12 col-sm-12 col-md-12">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                装修进度
                            </div>

                            <div class="panel-body">
                                <h4>当前阶段</h4>
                                <div class="row">
                                    <div class="col-md-12">${process?.period.name}</div>
                                </div>
                                <h4>当前进度</h4>
                                <div class="row">
                                    <div class="col-md-12">${ps?.step.name}</div>
                                </div>
                                <h4>计划周期</h4>
                                <div class="row">
                                    <div class="col-md-12">
                                        <g:formatDate date="${process?.planStartDate}" format="yyyy-MM-dd"></g:formatDate> ~
                                        <g:formatDate date="${process?.planEndDate}" format="yyyy-MM-dd"></g:formatDate>
                                    </div>
                                </div>
                                <h4>实际开始日期</h4>
                                <div class="row">
                                    <div class="col-md-12">
                                        <g:formatDate date="${process?.realStartDate}" format="yyyy-MM-dd"></g:formatDate>
                                    </div>
                                </div>
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
		</div>
        <script src="<%=request.contextPath%>/js/bootstrap.js" type="text/javascript"></script>
        <script src="<%=request.contextPath%>/js/jquery-ui-1.10.4.min.js" type="text/javascript"></script>
        <script src="<%=request.contextPath%>/js/yzf.js" type="text/javascript"></script>
	</body>
</html>
