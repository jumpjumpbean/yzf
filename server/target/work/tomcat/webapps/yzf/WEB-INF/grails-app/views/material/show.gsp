
<%@ page import="co.yzf.Material" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'material.label', default: 'Material')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-material" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-material" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="alert alert-warning" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list material">
			
				<g:if test="${materialInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="material.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${materialInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${materialInstance?.brand}">
				<li class="fieldcontain">
					<span id="brand-label" class="property-label"><g:message code="material.brand.label" default="Brand" /></span>
					
						<span class="property-value" aria-labelledby="brand-label"><g:fieldValue bean="${materialInstance}" field="brand"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${materialInstance?.type}">
				<li class="fieldcontain">
					<span id="type-label" class="property-label"><g:message code="material.type.label" default="Type" /></span>
					
						<span class="property-value" aria-labelledby="type-label"><g:fieldValue bean="${materialInstance}" field="type"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${materialInstance?.unit}">
				<li class="fieldcontain">
					<span id="unit-label" class="property-label"><g:message code="material.unit.label" default="Unit" /></span>
					
						<span class="property-value" aria-labelledby="unit-label"><g:fieldValue bean="${materialInstance}" field="unit"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${materialInstance?.supplier}">
				<li class="fieldcontain">
					<span id="supplier-label" class="property-label"><g:message code="material.supplier.label" default="Supplier" /></span>
					
						<span class="property-value" aria-labelledby="supplier-label"><g:fieldValue bean="${materialInstance}" field="supplier"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:materialInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${materialInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
