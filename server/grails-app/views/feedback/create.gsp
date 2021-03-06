<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>问题反馈</title>
	</head>
	<body>
		<div id="create-feedback" class="content container" role="main">
			<g:if test="${flash.message}">
			<div class="alert alert-warning" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${feedbackInstance}">
			<ul class="alert alert-warning" role="alert">
				<g:eachError bean="${feedbackInstance}" var="error">
				<li><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
            <div class="row">
                <div class="col-xs-12 col-sm-6 col-md-8">
                <g:form url="[resource:feedbackInstance, action:'save']" class="form-horizontal" id="feedbackFrm">
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
                                    <button type="button" onclick="submitFb()" class="save btn btn-primary">提交问题</button>
                                </fieldset>
                                </div>
                            </div>
                        </div>
                </g:form>
                </div>
            </div>
		</div>
	</body>
</html>
