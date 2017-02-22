<%@ page import="co.yzf.Material" %>
<div class="panel-body">
<div class="fieldcontain ${hasErrors(bean: materialInstance, field: 'name', 'error')} form-group">
	<label for="name" class="col-sm-3 control-label">
		材料名称
	</label>
	<div class="col-sm-9"><g:textField name="name" maxlength="64" class="form-control" value="${materialInstance?.name}"/></div>
</div>

<div class="fieldcontain ${hasErrors(bean: materialInstance, field: 'type', 'error')} form-group">
	<label class="col-sm-3 control-label">
		材料种类
	</label>
    <div class="col-sm-5">
        <g:select id="types" name="types" from="${allTypeList}" value="${materialInstance?.type}" class="form-control" noSelection="['': '其他']"/>
        <p><small class="text-danger">如果选择【其他】则必须在后面指定材料类型，否则以选择项的内容为准</small></p>
    </div>
    <div class="col-sm-4">
        <g:textField id="typei" name="typei" maxlength="32" class="form-control" value=""/>
        <input type="hidden" name="type" id="rtype">
    </div>
</div>

<div class="fieldcontain ${hasErrors(bean: materialInstance, field: 'brand', 'error')} form-group">
    <label for="brand" class="col-sm-3 control-label">
        品牌
    </label>
    <div class="col-sm-9"><g:textField name="brand" maxlength="64" class="form-control" value="${materialInstance?.brand}"/></div>
</div>

<div class="fieldcontain ${hasErrors(bean: materialInstance, field: 'brand', 'error')} form-group">
    <label for="modelno" class="col-sm-3 control-label">
        型号
    </label>
    <div class="col-sm-9"><g:textField name="modelno" maxlength="63" class="form-control" value="${materialInstance?.modelno}"/></div>
</div>

<div class="fieldcontain ${hasErrors(bean: materialInstance, field: 'unit', 'error')} form-group">
	<label for="unit" class="col-sm-3 control-label">
		计量单位
	</label>
    <div class="col-sm-9"><g:textField name="unit" maxlength="16" class="form-control" value="${materialInstance?.unit}"/></div>
</div>

<div class="fieldcontain ${hasErrors(bean: materialInstance, field: 'supplier', 'error')} form-group">
	<label for="supplier" class="col-sm-3 control-label">
		供应商
	</label>
    <div class="col-sm-9"><g:textField name="supplier" maxlength="64" class="form-control" value="${materialInstance?.supplier}"/></div>
</div>
</div>
