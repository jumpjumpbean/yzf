<%@ page import="co.yzf.Period" %>
<div class="panel-body">
<div class="form-group ${hasErrors(bean: periodInstance, field: 'orderNo', 'error')} ">
    <label for="orderNo" class="col-sm-3 control-label">
        发生先后顺序
    </label>
    <div class="col-sm-9"><g:textField name="orderNo" maxlength="5" class="form-control" value="${periodInstance?.orderNo}"/></div>
</div>
<div class="form-group ${hasErrors(bean: periodInstance, field: 'name', 'error')} ">
	<label for="name" class="col-sm-3 control-label">
		阶段名称
	</label>
    <div class="col-sm-9"><g:textField name="name" maxlength="32" class="form-control" value="${periodInstance?.name}"/></div>
</div>
<div class="form-group ${hasErrors(bean: periodInstance, field: 'name', 'error')}">
    <label for="name" class="col-sm-3 control-label">
        是否包含材料
    </label>
    <div class="col-sm-9"><g:select name="hasMaterial" from="${[0:'无',1:'是']}" optionKey="key" optionValue="value" value="${periodInstance?.hasMaterial}" class="form-control"/></div>
</div>
<div class="form-group ${hasErrors(bean: periodInstance, field: 'memo', 'error')} ">
	<label for="memo" class="col-sm-3 control-label">
        备注
	</label>
    <div class="col-sm-9"><g:textField name="memo" maxlength="127" class="form-control" value="${periodInstance?.memo}"/></div>
</div>
</div>

