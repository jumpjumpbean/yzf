<%@ page import="co.yzf.Project" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
        <title>修改工程订单</title>
	</head>
	<body>
		<div id="edit-project" class="content container" role="main">
			<g:if test="${flash.message}">
			<div class="alert alert-warning" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${projectInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${projectInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
            <div class="row">
                <div class="col-xs-12 col-sm-6 col-md-8">
                <g:form url="[resource:projectInstance, action:'update']" method="PUT" class="form-horizontal">
                    <g:hiddenField name="version" value="${projectInstance?.version}" />
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h4>修改工程订单</h4>
                        </div>
                        <fieldset class="form">
                            <g:render template="form"/>
                        </fieldset>
                        <div class="panel-footer">
                            <div class="form-group">
                                <div class="col-md-6 text-right">
                                <fieldset class="buttons">
                                    <g:actionSubmit class="btn btn-primary" action="update" value="更新订单基本信息" />
                                    <g:link action="show" resource="${projectInstance}" class="btn btn-default">取消编辑</g:link>
                                </fieldset>
                                </div>
                            </div>
                        </div>
                    </div>
                </g:form>
                </div>
            </div>
		</div>
	</body>
</html>
