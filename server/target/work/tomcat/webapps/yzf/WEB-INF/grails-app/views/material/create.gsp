<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>添加材料</title>
	</head>
	<body>
		<div id="create-material" class="content container" role="main">
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
                    <g:form url="[resource:materialInstance, action:'save']" class="form-horizontal" id="newMtrFrm">
                    <div class="panel panel-info">
                            <div class="panel-heading">
                                <h4>添加材料</h4>
                            </div>
                            <fieldset class="form">
                                <g:render template="form"/>
                            </fieldset>
                            <div class="panel-footer">
                                <div class="form-group">
                                    <div class="col-md-6 text-right">
                                        <fieldset class="buttons">
                                            <button type="button" onclick="checkMtype('#newMtrFrm');" class="save btn btn-primary">添加材料</button>
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
