<%@ page import="co.yzf.Project" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>工程订单列表</title>
	</head>
	<body>
		<div id="list-project" class="content container" role="main">
			<g:if test="${flash.message}">
				<div class="alert alert-warning" role="status">${flash.message}</div>
			</g:if>
            <div class="row">
                <div class="hidden-xs hidden-sm col-md-2">
                    <div data-spy="affix" class="bs-docs-sidebar hidden-print affix-top">
                        <div class="panel panel-info">
                            <div class="panel-body">
                                <g:form name="filterFrm" controller="project" action="index">
                                <p>订单状态</p>
                                <input type="hidden" name="status" id="status" value="${status}"/>
                                <ul class="nav nav-pills nav-stacked" id="fstatus">
                                    <li data-val="0" class="active">
                                        <a href="javascript:;" onclick="changeStatus('0')">所有</a>
                                    </li>
                                    <li data-val="1">
                                        <a href="javascript:;" onclick="changeStatus('1')">未启动</a>
                                    </li>
                                    <li data-val="2">
                                        <a href="javascript:;" onclick="changeStatus('2')">进行中</a>
                                    </li>
                                    <li data-val="3">
                                        <a href="javascript:;" onclick="changeStatus('3')">已完成</a>
                                    </li>
                                </ul>
                                <p>签订日期</p>
                                <input name="dateCreated" type="hidden" id="dateCreated" value="${dateCreated}"/>
                                <ul class="nav nav-pills nav-stacked" id="fdate">
                                    <li data-val="" onclick="changeDate('')" class="active">
                                        <a >所有</a>
                                    </li>
                                    <g:each in="${months}" var="month">
                                    <g:set var="mth" value="${month.split(':')}"/>
                                    <li data-val="${mth[1]}">
                                        <a href="javascript:;" onclick="changeDate('${mth[1]}')">${mth[0]}</a>
                                    </li>
                                    </g:each>
                                </ul>
                                </g:form>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-9 col-md-9">
                    <ul class="list-unstyled">
                        <g:each in="${projectInstanceList}" var="projectInstance">
                        <li class="panel panel-info">
                            <div class="panel-heading">
                                <a href="<%=request.contextPath%>/project/show/${projectInstance.id}">${fieldValue(bean: projectInstance, field: "address")}</a>
                            </div>
                            <div class="panel-body">
                                <table class="table table-hover">
                                    <tr>
                                        <td style="border: 0;">客户姓名：${fieldValue(bean: projectInstance, field: "customer")}</td>
                                        <td style="border: 0;">合同编号：${fieldValue(bean: projectInstance, field: "contractNo")}</td>
                                    </tr>
                                    <tr>
                                        <td style="border: 0;">计划周期：<g:formatDate date="${projectInstance.planStartDate}" format="yyyy-MM-dd"/>～<g:formatDate date="${projectInstance.planEndDate}" format="yyyy-MM-dd"/></td>
                                        <td style="border: 0;">项目监理：${fieldValue(bean: projectInstance.responser, field: "uname")}</td>
                                    </tr>
                                    <tr>
                                        <td style="border: 0;">实际周期：<g:formatDate date="${projectInstance.realStartDate}" format="yyyy-MM-dd"/>～<g:formatDate date="${projectInstance.realEndDate}" format="yyyy-MM-dd"/></td>
                                        <td style="border: 0;">联系方式：${fieldValue(bean: projectInstance.responser, field: "telephone")}</td>
                                    </tr>
                                </table>
                            </div>
                        </li>
                        </g:each>
                        <li>
                            <div class="pagination">
                                <g:paginate total="${projectInstanceCount ?: 0}" />
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
		</div>
    <script type="application/javascript">
        $(function(){
            var status = $("#status").val();
            var fdate = $("#dateCreated").val();
            $("#fstatus li").removeClass("active");
            $("#fstatus li[data-val='"+status+"']").addClass("active");
            if(fdate){
                $("#fdate li").removeClass("active");
                $("#fdate li[data-val='"+fdate+"']").addClass("active");
            }
        });
        function changeStatus(status){
            var oldStatus = $("#status").val();
            if(oldStatus != status){
                $("#status").val(status);
                $("#filterFrm").submit();
            }
        }

        function changeDate(newDate){
            $("#dateCreated").val(newDate);
            $("#filterFrm").submit();
        }
    </script>
	</body>
</html>
