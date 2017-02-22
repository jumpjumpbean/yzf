<%@ page import="co.yzf.Project" %>
<div class="panel-body">
<g:if test="${projectInstance?.id != null}">
<div class="form-group fieldcontain ${hasErrors(bean: projectInstance, field: 'user', 'error')} ">
	<label for="user" class="col-sm-3 control-label">
		客户关联帐号
	</label>
    <div class="col-sm-7">
        <g:select id="user" name="user.id" from="${co.yzf.UserRole.executeQuery("select user from UserRole ur where ur.role.authority='ROLE_CUSTOMER'")}" optionKey="id" value="${projectInstance?.user?.id}" class="form-control" noSelection="['null': '']"/>
    </div>
    <div class="col-sm-2">
        <a class="btn btn-link" href="<%=request.contextPath%>/user/addUser">创建用户</a>
    </div>
</div>
</g:if>
<div class="form-group fieldcontain ${hasErrors(bean: projectInstance, field: 'customer', 'error')} ">
	<label for="customer" class="col-sm-3 control-label">
		客户姓名
	</label>
    <div class="col-sm-9"><g:textField name="customer" class="form-control" maxlength="63" value="${projectInstance?.customer}"/></div>
</div>
<div class="form-group fieldcontain ${hasErrors(bean: projectInstance, field: 'contractNo', 'error')} ">
	<label for="contractNo" class="col-sm-3 control-label">
		合同编号
	</label>
    <div class="col-sm-9"><g:textField name="contractNo" class="form-control" maxlength="32" value="${projectInstance?.contractNo}"/></div>
</div>
<div class="form-group fieldcontain ${hasErrors(bean: projectInstance, field: 'address', 'error')} ">
	<label for="address" class="col-sm-3 control-label">
		工程地址
	</label>
    <div class="col-sm-9"><g:textField name="address" class="form-control" maxlength="127" value="${projectInstance?.address}"/></div>
</div>
<div class="form-group fieldcontain ${hasErrors(bean: projectInstance, field: 'planStartDate', 'error')} ">
	<label for="planStartDate" class="col-sm-3 control-label">
		计划开始日期
	</label>
    <div class="col-sm-9"><input type="text" id="planStartDate" name="planStartDate" class="form-control" value="<g:formatDate date="${projectInstance?.planStartDate}" format="yyyy-MM-dd" />" readonly></div>
</div>
<div class="form-group fieldcontain ${hasErrors(bean: projectInstance, field: 'planEndDate', 'error')} ">
	<label for="planEndDate" class="col-sm-3 control-label">
		计划结束日期
	</label>
    <div class="col-sm-9"><input type="text" id="planEndDate" name="planEndDate" class="form-control" value="<g:formatDate date="${projectInstance?.planEndDate}" format="yyyy-MM-dd" />" readonly></div>
</div>
<div class="form-group fieldcontain ${hasErrors(bean: projectInstance, field: 'responser', 'error')} ">
	<label for="responser" class="col-sm-3 control-label">
		工程监理
	</label>
    <div class="col-sm-9"><g:select id="responser" name="responser.id" from="${co.yzf.UserRole.executeQuery("select user from UserRole ur where ur.role.authority='ROLE_SUPERVISOR'",[])}" optionKey="id" value="${projectInstance?.responser?.id}" class="form-control" noSelection="['null': '']"/></div>
</div>
<div class="form-group fieldcontain ${hasErrors(bean: projectInstance, field: 'qc', 'error')} ">
    <label for="qc" class="col-sm-3 control-label">
        工程质检
    </label>
    <div class="col-sm-9"><g:select id="qc" name="qc.id" from="${co.yzf.User.executeQuery("select user from UserRole ur where ur.role.authority='ROLE_QC'",[])}" optionKey="id" value="${projectInstance?.qc?.id}" class="form-control" noSelection="['null': '']"/></div>
</div>
<div class="form-group fieldcontain ${hasErrors(bean: projectInstance, field: 'totalCost', 'error')} required">
    <label for="totalCost" class="col-sm-3 control-label">
        工程总款
        <span class="required-indicator">*</span>
    </label>
    <div class="col-sm-9"><g:field name="totalCost" type="number" class="form-control" value="${projectInstance.totalCost}" required=""/></div>
</div>
<div class="form-group fieldcontain ${hasErrors(bean: projectInstance, field: 'imprest', 'error')} required">
	<label for="imprest" class="col-sm-3 control-label">
		预付款
		<span class="required-indicator">*</span>
	</label>
    <div class="col-sm-9"><g:field name="imprest" type="number" class="form-control" value="${projectInstance.imprest}" required=""/></div>
</div>
<div class="form-group fieldcontain ${hasErrors(bean: projectInstance, field: 'payment', 'error')} required">
	<label for="payment" class="col-sm-3 control-label">
		已经支付款额
		<span class="required-indicator">*</span>
	</label>
    <div class="col-sm-9"><g:field name="payment" type="number" class="form-control" value="${projectInstance.payment}" required=""/></div>
</div>
</div>
<script type="application/javascript">
    $(function() {
        initDatepicker($("#planStartDate"),$("#planEndDate"),2);
        initDatepicker($("#realStartDate"),$("#realEndDate"),2);
    });
</script>