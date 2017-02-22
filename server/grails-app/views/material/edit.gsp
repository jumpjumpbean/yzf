<%@ page import="co.yzf.Material" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>编辑材料</title>
	</head>
	<body>
		<div id="edit-material" class="content container" role="main">
			<g:if test="${flash.message}">
			<div class="alert alert-warning" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${materialInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${materialInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
            <div class="row">
                <div class="col-xs-12 col-sm-6 col-md-8">
                    <g:form url="[resource:materialInstance, action:'update']" method="PUT" class="form-horizontal" id="updMtrFrm">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <h4>编辑材料</h4>
                            </div>
                            <g:hiddenField name="version" value="${materialInstance?.version}" />
                            <fieldset class="form">
                                <g:render template="form"/>
                            </fieldset>
                            <div class="panel-footer">
                                <div class="form-group">
                                    <div class="col-md-6 text-right">
                                        <fieldset class="buttons">
                                            <button type="button" onclick="checkMtype('#updMtrFrm');" class="save btn btn-primary">更新材料</button>
                                            <g:link action="index" class="btn btn-default">取消编辑</g:link>
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
