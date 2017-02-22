<%@ page import="co.yzf.Project" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>工程订单列表</title>
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'morris.css')}" type="text/css">
	</head>
	<body>
		<div id="list-project" class="content container" role="main">
			<g:if test="${flash.message}">
				<div class="alert alert-warning" role="status">${flash.message}</div>
			</g:if>
            <div class="row">
                <div class="hidden-xs col-sm-2 col-md-2">
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
                <div class="col-sm-10 col-md-10">
                    <div class="panel panel-info">
                        <div class="panel-body">
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th>订单号</th>
                                    <th>客户姓名</th>
                                    <th>项目地址</th>
                                    <th>监理</th>
                                    <th>当前阶段</th>
                                    <th>状态</th>
                                    <th>进度</th>
                                </tr>
                                </thead>
                                <tbody>
                                    <g:if test="${projectInstanceList != null && projectInstanceList.size() > 0}">
                                        <g:each in="${projectInstanceList}" var="projectInstance">
                                        <g:set var="proc" value="${co.yzf.Process.findByProjectAndStatus(projectInstance,2)}"/>
                                        <tr>
                                            <td><a href="<%=request.contextPath%>/project/show/${projectInstance.id}">${projectInstance?.contractNo}</a></td>
                                            <td>${projectInstance?.customer}</td>
                                            <td>${projectInstance?.address}</td>
                                            <td>${projectInstance?.responser?.uname}</td>
                                            <td>
                                                <g:if test="${projectInstance.status==1}">
                                                    没启动
                                                </g:if>
                                                <g:elseif test="${projectInstance.status==3}">
                                                    已完成
                                                </g:elseif>
                                                <g:else>
                                                     <g:if test="${proc == null}">未知</g:if><g:else>${proc?.period?.name}</g:else>
                                                </g:else>
                                            </td>
                                            <td>
                                                <g:if test="${projectInstance.status==1 || projectInstance.status==3}">-</g:if>
                                                <g:elseif test="${proc?.planEndDate > new java.util.Date()}">
                                                    正常
                                                </g:elseif>
                                                <g:else>
                                                    <span class="text-danger">延期</span>
                                                </g:else>
                                            </td>
                                            <td>
                                                <g:if test="${projectInstance.status==2}">
                                                    <a href="javascript:;" onclick="drawTimeLine('${projectInstance.id}',true)" class="btn btn-sm btn-warning">查看</a>
                                                </g:if>
                                                <g:else>
                                                    -
                                                </g:else>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="7" style="display: none;">
                                                <div id="tline${projectInstance.id}" style="height:100px;max-width:900px"></div>
                                            </td>
                                        </tr>
                                        </g:each>
                                    </g:if>
                                    <g:else>
                                        <tr><td colspan="7"><p>没有符合条件的记录</p></td></tr>
                                    </g:else>
                                </tbody>
                            </table>
                        </div>
                        <div class="panel-footer">
                            <div class="pagination">
                                <g:paginate total="${projectInstanceCount ?: 0}" />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
		</div>
    <div class="modal fade" id="tlDlg">
        <div class="modal-dialog" style="width: 100%">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">施工时间轴</h4>
                </div>
                <div class="modal-body">
                    <h5><strong>施工时间对比轴（<span class="text-danger">红点</span>：计划结束时间，蓝点：实际结束时间）,鼠标移上去查看详情</strong></h5>
                    <div id="tline" style="max-height:100px;"></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
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
    <script src="<%=request.contextPath%>/js/raphael-min.js" type="text/javascript"></script>
    <script src="<%=request.contextPath%>/js/morris.js" type="text/javascript"></script>
	</body>
</html>
