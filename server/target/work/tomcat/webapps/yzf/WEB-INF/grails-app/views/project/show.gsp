<%@ page import="co.yzf.Project" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title><g:fieldValue bean="${projectInstance}" field="address"/></title>
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'morris.css')}" type="text/css">
	</head>
	<body>
    <div class="section prj-base">
		<div id="show-project" class="content container inner" role="main">
            <g:if test="${flash.message}">
                <script type="application/javascript">
                    $(function(){
                       showMessage("${flash.message}","nomal",3000);
                    });
                </script>
            </g:if>
            <div class="panel panel-default">
                <div class="panel-heading"><h4>工程基本信息</h4></div>
                <ul class="list-unstyled list-group">
                    <g:if test="${projectInstance?.user}">
                    <li class="list-group-item row rmm">
                        <span id="user-label" class="col-xs-3 col-sm-3 col-md-2">客户绑定帐号</span>
                        <span class="col-xs-9 col-sm-9 col-md-10" aria-labelledby="user-label">${projectInstance?.user?.encodeAsHTML()}</span>
                    </li>
                    </g:if>

                    <li class="list-group-item row rmm">
                        <span id="customer-label" class="col-xs-3 col-sm-3 col-md-2">客户名称：</span>
                        <span class="col-xs-9 col-sm-9 col-md-10" aria-labelledby="customer-label"><g:fieldValue bean="${projectInstance}" field="customer"/></span>
                    </li>

                    <li class="list-group-item row rmm">
                        <span id="contractNo-label" class="col-xs-3 col-sm-3 col-md-2">合同编号：</span>
                        <span class="col-xs-9 col-sm-9 col-md-10" aria-labelledby="contractNo-label"><g:fieldValue bean="${projectInstance}" field="contractNo"/></span>
                    </li>

                    <li class="list-group-item row rmm">
                        <span id="address-label" class="col-xs-3 col-sm-3 col-md-2">工程地址：</span>
                        <span class="col-xs-9 col-sm-9 col-md-10" aria-labelledby="address-label"><g:fieldValue bean="${projectInstance}" field="address"/></span>
                    </li>

                    <li class="list-group-item row rmm">
                        <span id="planStartDate-label" class="col-xs-3 col-sm-3 col-md-2">预计开始日期：</span>
                        <span class="col-xs-9 col-sm-9 col-md-10" aria-labelledby="planStartDate-label"><g:formatDate date="${projectInstance?.planStartDate}" format="yyyy-MM-dd" /></span>
                    </li>

                    <li class="list-group-item row rmm">
                        <span id="planEndDate-label" class="col-xs-3 col-sm-3 col-md-2">预计结束日期：</span>
                        <span class="col-xs-9 col-sm-9 col-md-10" aria-labelledby="planEndDate-label"><g:formatDate date="${projectInstance?.planEndDate}" format="yyyy-MM-dd" /></span>
                    </li>

                    <li class="list-group-item row rmm">
                        <span id="realStartDate-label" class="col-xs-3 col-sm-3 col-md-2">实际开始日期：</span>
                        <span class="col-xs-9 col-sm-9 col-md-10" aria-labelledby="realStartDate-label"><g:formatDate date="${projectInstance?.realStartDate}" format="yyyy-MM-dd"/></span>
                    </li>

                    <li class="list-group-item row rmm">
                        <span id="realEndDate-label" class="col-xs-3 col-sm-3 col-md-2">实际结束日期：</span>
                        <span class="col-xs-9 col-sm-9 col-md-10" aria-labelledby="realEndDate-label"><g:formatDate date="${projectInstance?.realEndDate}" format="yyyy-MM-dd" /></span>
                    </li>

                    <li class="list-group-item row rmm">
                        <span id="responser-label" class="col-xs-3 col-sm-3 col-md-2">工程监理：</span>
                        <span class="col-xs-9 col-sm-9 col-md-10" aria-labelledby="responser-label">${projectInstance?.responser?.uname}</span>
                    </li>
                    <li class="list-group-item row rmm">
                        <span id="qc-label" class="col-xs-3 col-sm-3 col-md-2">工程质检：</span>
                        <span class="col-xs-9 col-sm-9 col-md-10" aria-labelledby="qc-label">${projectInstance?.qc?.uname}</span>
                    </li>

                    <li class="list-group-item row rmm">
                        <span id="totalCost-label" class="col-xs-3 col-sm-3 col-md-2">项目总款项：</span>
                        <span class="col-xs-9 col-sm-9 col-md-10" aria-labelledby="totalCost-label"><g:fieldValue bean="${projectInstance}" field="totalCost"/></span>
                    </li>

                    <li class="list-group-item row rmm">
                        <span id="imprest-label" class="col-xs-3 col-sm-3 col-md-2">预付款：</span>
                        <span class="col-xs-9 col-sm-9 col-md-10" aria-labelledby="imprest-label"><g:fieldValue bean="${projectInstance}" field="imprest"/></span>
                    </li>

                    <li class="list-group-item row rmm">
                        <span id="payment-label" class="col-xs-3 col-sm-3 col-md-2">已付款：</span>
                        <span class="col-xs-9 col-sm-9 col-md-10" aria-labelledby="payment-label"><g:fieldValue bean="${projectInstance}" field="payment"/></span>
                    </li>

                    <li class="list-group-item row rmm">
                        <span id="status-label" class="col-xs-3 col-sm-3 col-md-2">工程施工进度：</span>
                        <span class="col-xs-9 col-sm-9 col-md-10" aria-labelledby="status-label">
                            <g:if test="${projectInstance.status == 1}">
                                项目还未启动
                            </g:if>
                            <g:elseif test="${projectInstance.status == 2}">
                                施工中
                            </g:elseif>
                            <g:else>
                                结束
                            </g:else>
                        </span>
                    </li>
                </ul>
                <div class="panel-footer">
                    <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_SUPER">
                        <g:link class="btn btn-primary" action="edit" resource="${projectInstance}">编辑订单基本信息</g:link>
                        <g:if test="${projectInstance?.status == 1}">
                            <g:link class="btn btn-success" controller="process" action="schedule" id="${projectInstance?.id}">项目启动</g:link>
                        </g:if>
                        <a class="btn btn-default" href="<%=request.contextPath%>/project/addMaterial/${projectInstance?.id}">编辑工程材料</a>
                    </sec:ifAnyGranted>
                    <a class="btn btn-default" href="<%=request.contextPath%>/project/viewMaterial/${projectInstance?.id}">查看工程材料</a>
                </div>
            </div>
		</div>
    </div>
    <div class="section prj-tml">
        <div class="container inner">
            <div class="panel panel-default">
                <div class="panel-heading"><h4>施工进度</h4></div>
                <g:if test="${projectInstance.status == 1}">
                    <div class="panel-body">
                    <p>该项目还未启动</p>
                    </div>
                </g:if>
                <g:else>
                <div class="panel-body tm-line">
                    <div class="row">
                    <ul class="nav nav-tabs" role="tablist">
                        <g:each in="${processList}" var="process" status="j">
                            <g:if test="${process.status == 2}">
                                <li class="active">
                                    <div class="point"><a data-toggle="tooltip" data-placement="top" data-original-title="<g:formatDate date="${process?.realStartDate}" format="M/d" />"></a></div>
                                    <a href="#p${process.id}" role="tab" data-toggle="tab">${process.period.name}</a>
                                </li>
                            </g:if>
                            <g:elseif test="${process.status == 3}">
                                <li>
                                    <div class="point"><a data-toggle="tooltip" data-placement="top" data-original-title="<g:formatDate date="${process?.realStartDate}" format="M/d" />"></a></div>
                                    <a href="#p${process.id}" role="tab" data-toggle="tab">${process.period.name}</a>
                                </li>
                            </g:elseif>
                            <g:else>
                                <li><div class="point"></div><a href="#p${process.id}" role="tab" data-toggle="tab">${process.period.name}</a></li>
                            </g:else>
                        </g:each>
                    </ul>
                    <div class="tab-content">
                        <g:each in="${processList}" var="process" status="j">
                            <g:if test="${process.status == 2}">
                                <div class="tab-pane active" id="p${process.id}">
                                    <div class="panel-body">
                                    <p>装修进度操作</p>
                                    <ul class="list-unstyled list-inline" id="curProcStep">
                                    <g:set var="allProcStep" value="${co.yzf.ProcessStep.findAllByProcess(process)}"></g:set>
                                    <g:if test="${process.period.hasMaterial == 1}">
                                        <g:each in="${allProcStep}" var="procstep" status="i">
                                            <g:set var="stepId" value="${procstep.step.id}"/>
                                            <g:if test="${procstep.status == 3}">
                                                <g:if test="${stepId == 5}">
                                                    <li role="presentation"><strong>施工阶段：</strong><a href="javascript:;" class="done"><span class="glyphicon glyphicon-ok text-success"></span>${procstep?.step?.name}</a></li>
                                                </g:if>
                                                <g:elseif test="${stepId == 1}">
                                                    <li role="presentation"><strong>进料阶段：</strong><a href="javascript:;" class="done"><span class="glyphicon glyphicon-ok text-success"></span>${procstep?.step?.name}</a></li>
                                                </g:elseif>
                                                <g:else>
                                                    <li role="presentation"><a href="javascript:;" class="done"><span class="glyphicon glyphicon-ok text-success"></span>${procstep?.step?.name}</a></li>
                                                </g:else>
                                            </g:if>
                                            <g:else>
                                                <g:if test="${stepId == 1}">
                                                    <g:if test="${curUser.id == process.charger?.id}">
                                                        <li role="presentation"><strong>进料阶段：</strong><a data-step="${stepId}" data-sgroup="${procstep.sgroup}" data-status="${procstep.status}" role="menuitem" href="<%=request.contextPath%>/process/uploadpic/${procstep.id}">${procstep?.step?.name}</a></li>
                                                    </g:if>
                                                    <g:else>
                                                        <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_SUPER">
                                                            <li role="presentation"><strong>进料阶段：</strong><a data-step="${stepId}" data-sgroup="${procstep.sgroup}" data-status="${procstep.status}" role="menuitem" href="<%=request.contextPath%>/process/uploadpic/${procstep.id}">${procstep?.step?.name}</a></li>
                                                        </sec:ifAnyGranted>
                                                        <sec:ifNotGranted roles="ROLE_ADMIN,ROLE_SUPER">
                                                            <li role="presentation"><strong>进料阶段：</strong><a role="menuitem" href="javascript:;" onclick="alert('你无权进行此步操作')"><span class='glyphicon glyphicon-ban-circle text-danger'></span>${procstep?.step?.name}</a></li>
                                                        </sec:ifNotGranted>
                                                    </g:else>
                                                </g:if>
                                                <g:if test="${stepId == 2}">
                                                    <g:if test="${allProcStep[i-1].status == 3}">
                                                        <g:if test="${curUser.id == projectInstance.qc?.id}">
                                                            <li role="presentation"><a data-step="${stepId}" data-sgroup="${procstep.sgroup}" data-status="${procstep.status}" role="menuitem" href="<%=request.contextPath%>/process/examing/${procstep.id}">${procstep?.step?.name}</a></li>
                                                        </g:if>
                                                        <g:else>
                                                            <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_SUPER">
                                                                <li role="presentation"><a data-step="${stepId}" data-sgroup="${procstep.sgroup}" data-status="${procstep.status}" role="menuitem" href="<%=request.contextPath%>/process/examing/${procstep.id}">${procstep?.step?.name}</a></li>
                                                            </sec:ifAnyGranted>
                                                            <sec:ifNotGranted roles="ROLE_ADMIN,ROLE_SUPER">
                                                                <li role="presentation"><a role="menuitem" href="javascript:;" onclick="alert('你无权进行此步操作')"><span class='glyphicon glyphicon-ban-circle text-danger'></span>${procstep?.step?.name}</a></li>
                                                            </sec:ifNotGranted>
                                                        </g:else>
                                                    </g:if>
                                                    <g:else>
                                                        <li role="presentation"><a class="undo" href="javascript:;" onclick="alert('还没进展到此步')"><span class='glyphicon glyphicon-ban-circle text-danger'></span>${procstep?.step?.name}</a></li>
                                                    </g:else>
                                                </g:if>
                                                <g:if test="${stepId == 3}">
                                                    <g:if test="${allProcStep[i-1].status == 3}">
                                                        <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_SUPER">
                                                            <li role="presentation"><a data-step="${stepId}" data-sgroup="${procstep.sgroup}" data-status="${procstep.status}" role="menuitem" href="<%=request.contextPath%>/process/picedit/${procstep.id}">${procstep?.step?.name}</a></li>
                                                        </sec:ifAnyGranted>
                                                        <sec:ifNotGranted roles="ROLE_ADMIN,ROLE_SUPER">
                                                            <li role="presentation"><a role="menuitem" href="javascript:;" onclick="alert('你无权进行此步操作')"><span class='glyphicon glyphicon-ban-circle text-danger'></span>${procstep?.step?.name}</a></li>
                                                        </sec:ifNotGranted>
                                                    </g:if>
                                                    <g:else>
                                                        <li role="presentation"><a class="undo" href="javascript:;" onclick="alert('还没进展到此步')"><span class='glyphicon glyphicon-ban-circle text-danger'></span>${procstep?.step?.name}</a></li>
                                                    </g:else>
                                                </g:if>
                                                <g:if test="${stepId == 4}">
                                                    <g:if test="${allProcStep[i-1].status == 3}">
                                                        <g:if test="${curUser.id == projectInstance.user?.id}">
                                                            <li role="presentation"><a data-step="${stepId}" data-sgroup="${procstep.sgroup}" data-status="${procstep.status}" role="menuitem" href="<%=request.contextPath%>/process/verify/${procstep.id}">${procstep?.step?.name}</a></li>
                                                        </g:if>
                                                        <g:else>
                                                            <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_SUPER">
                                                                <li role="presentation"><a data-step="${stepId}" data-sgroup="${procstep.sgroup}" data-status="${procstep.status}" role="menuitem" href="<%=request.contextPath%>/process/verify/${procstep.id}">${procstep?.step?.name}</a></li>
                                                            </sec:ifAnyGranted>
                                                            <sec:ifNotGranted roles="ROLE_ADMIN,ROLE_SUPER">
                                                                <li role="presentation"><a role="menuitem" href="javascript:;" onclick="alert('你无权进行此步操作')"><span class='glyphicon glyphicon-ban-circle text-danger'></span>${procstep?.step?.name}</a></li>
                                                            </sec:ifNotGranted>
                                                        </g:else>
                                                    </g:if>
                                                    <g:else>
                                                        <li role="presentation"><a class="undo" href="javascript:;" onclick="alert('还没进展到此步')"><span class='glyphicon glyphicon-ban-circle text-danger'></span>${procstep?.step?.name}</a></li>
                                                    </g:else>
                                                </g:if>
                                                <g:if test="${stepId == 5}">
                                                    <g:if test="${allProcStep[i-1].status == 3}">
                                                        <g:if test="${curUser.id == process.charger?.id}">
                                                            <li role="presentation"><strong>施工阶段：</strong><a data-step="${stepId}" data-sgroup="${procstep.sgroup}" data-status="${procstep.status}" role="menuitem" href="<%=request.contextPath%>/process/uploadpic/${procstep.id}">${procstep?.step?.name}</a></li>
                                                        </g:if>
                                                        <g:else>
                                                            <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_SUPER">
                                                                <li role="presentation"><strong>施工阶段：</strong><a data-step="${stepId}" data-sgroup="${procstep.sgroup}" data-status="${procstep.status}" role="menuitem" href="<%=request.contextPath%>/process/uploadpic/${procstep.id}">${procstep?.step?.name}</a></li>
                                                            </sec:ifAnyGranted>
                                                            <sec:ifNotGranted roles="ROLE_ADMIN,ROLE_SUPER">
                                                                <li role="presentation"><strong>施工阶段：</strong><a role="menuitem" href="javascript:;" onclick="alert('你无权进行此步操作')"><span class='glyphicon glyphicon-ban-circle text-danger'></span>${procstep?.step?.name}</a></li>
                                                            </sec:ifNotGranted>
                                                        </g:else>
                                                    </g:if>
                                                    <g:else>
                                                        <li role="presentation"><strong>施工阶段：</strong><a class="undo" href="javascript:;" onclick="alert('还没进展到此步')"><span class='glyphicon glyphicon-ban-circle text-danger'></span>${procstep?.step?.name}</a></li>
                                                    </g:else>
                                                </g:if>
                                                <g:if test="${stepId == 6}">
                                                    <g:if test="${allProcStep[i-1].status == 3}">
                                                        <g:if test="${curUser.id == projectInstance.qc?.id}">
                                                            <li role="presentation"><a data-step="${stepId}" data-sgroup="${procstep.sgroup}" data-status="${procstep.status}" role="menuitem" href="<%=request.contextPath%>/process/examing/${procstep.id}">${procstep?.step?.name}</a></li>
                                                        </g:if>
                                                        <g:else>
                                                            <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_SUPER">
                                                                <li role="presentation"><a data-step="${stepId}" data-sgroup="${procstep.sgroup}" data-status="${procstep.status}" role="menuitem" href="<%=request.contextPath%>/process/examing/${procstep.id}">${procstep?.step?.name}</a></li>
                                                            </sec:ifAnyGranted>
                                                            <sec:ifNotGranted roles="ROLE_ADMIN,ROLE_SUPER">
                                                                <li role="presentation"><a role="menuitem" href="javascript:;" onclick="alert('你无权进行此步操作')"><span class='glyphicon glyphicon-ban-circle text-danger'></span>${procstep?.step?.name}</a></li>
                                                            </sec:ifNotGranted>
                                                        </g:else>
                                                    </g:if>
                                                    <g:else>
                                                        <li role="presentation"><a class="undo" href="javascript:;" onclick="alert('还没进展到此步')"><span class='glyphicon glyphicon-ban-circle text-danger'></span>${procstep?.step?.name}</a></li>
                                                    </g:else>
                                                </g:if>
                                                <g:if test="${stepId == 7}">
                                                    <g:if test="${allProcStep[i-1].status == 3}">
                                                        <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_SUPER">
                                                            <li role="presentation"><a data-step="${stepId}" data-sgroup="${procstep.sgroup}" data-status="${procstep.status}" role="menuitem" href="<%=request.contextPath%>/process/picedit/${procstep.id}">${procstep?.step?.name}</a></li>
                                                        </sec:ifAnyGranted>
                                                        <sec:ifNotGranted roles="ROLE_ADMIN,ROLE_SUPER">
                                                            <li role="presentation"><a role="menuitem" href="javascript:;" onclick="alert('你无权进行此步操作')"><span class='glyphicon glyphicon-ban-circle text-danger'></span>${procstep?.step?.name}</a></li>
                                                        </sec:ifNotGranted>
                                                    </g:if>
                                                    <g:else>
                                                        <li role="presentation"><a class="undo" href="javascript:;" onclick="alert('还没进展到此步')"><span class='glyphicon glyphicon-ban-circle text-danger'></span>${procstep?.step?.name}</a></li>
                                                    </g:else>
                                                </g:if>
                                                <g:if test="${stepId == 8}">
                                                    <g:if test="${allProcStep[i-1].status == 3}">
                                                        <g:if test="${curUser.id == projectInstance.user?.id}">
                                                            <li role="presentation"><a data-step="${stepId}" data-sgroup="${procstep.sgroup}" data-status="${procstep.status}" role="menuitem" href="<%=request.contextPath%>/process/verify/${procstep.id}">${procstep?.step?.name}</a></li>
                                                        </g:if>
                                                        <g:else>
                                                            <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_SUPER">
                                                                <li role="presentation"><a data-step="${stepId}" data-sgroup="${procstep.sgroup}" data-status="${procstep.status}" role="menuitem" href="<%=request.contextPath%>/process/verify/${procstep.id}">${procstep?.step?.name}</a></li>
                                                            </sec:ifAnyGranted>
                                                            <sec:ifNotGranted roles="ROLE_ADMIN,ROLE_SUPER">
                                                                <li role="presentation"><a role="menuitem" href="javascript:;" onclick="alert('你无权进行此步操作')"><span class='glyphicon glyphicon-ban-circle text-danger'></span>${procstep?.step?.name}</a></li>
                                                            </sec:ifNotGranted>
                                                        </g:else>
                                                    </g:if>
                                                    <g:else>
                                                        <li role="presentation"><a class="undo" href="javascript:;" onclick="alert('还没进展到此步')"><span class='glyphicon glyphicon-ban-circle text-danger'></span>${procstep?.step?.name}</a></li>
                                                    </g:else>
                                                </g:if>
                                            </g:else>
                                        </g:each>
                                    </g:if>
                                    <g:else>
                                        <g:each in="${allProcStep}" var="procstep" status="i">
                                            <g:set var="stepId" value="${procstep.step.id}"/>
                                            <g:if test="${procstep.status == 3}">
                                                <li role="presentation"><g:if test="${stepId == 9}"><strong>施工阶段：</strong></g:if><a class="done" href="javascript:;"><span class="glyphicon glyphicon-ok text-success"></span>${procstep?.step?.name}</a></li>
                                            </g:if>
                                            <g:else>
                                                <g:if test="${stepId == 9}">
                                                    <g:if test="${curUser.id == process.charger?.id}">
                                                        <li role="presentation"><strong>施工阶段：</strong><a data-step="${stepId}" data-sgroup="${procstep.sgroup}" data-status="${procstep.status}" role="menuitem" href="<%=request.contextPath%>/process/uploadpic/${procstep.id}">${procstep?.step?.name}</a></li>
                                                    </g:if>
                                                    <g:else>
                                                        <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_SUPER">
                                                            <li role="presentation"><strong>施工阶段：</strong><a data-step="${stepId}" data-sgroup="${procstep.sgroup}" data-status="${procstep.status}" role="menuitem" href="<%=request.contextPath%>/process/uploadpic/${procstep.id}">${procstep?.step?.name}</a></li>
                                                        </sec:ifAnyGranted>
                                                        <sec:ifNotGranted roles="ROLE_ADMIN,ROLE_SUPER">
                                                            <li role="presentation"><strong>施工阶段：</strong><a role="menuitem" href="javascript:;" onclick="alert('你无权进行此步操作')"><span class='glyphicon glyphicon-ban-circle text-danger'></span>${procstep?.step?.name}</a></li>
                                                        </sec:ifNotGranted>
                                                    </g:else>
                                                </g:if>
                                                <g:if test="${stepId == 10}">
                                                    <g:if test="${allProcStep[i-1].status == 3}">
                                                        <g:if test="${curUser.id == projectInstance.qc?.id}">
                                                            <li role="presentation"><a data-step="${stepId}" data-sgroup="${procstep.sgroup}" data-status="${procstep.status}" role="menuitem" href="<%=request.contextPath%>/process/examing/${procstep.id}">${procstep?.step?.name}</a></li>
                                                        </g:if>
                                                        <g:else>
                                                            <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_SUPER">
                                                                <li role="presentation"><a data-step="${stepId}" data-sgroup="${procstep.sgroup}" data-status="${procstep.status}" role="menuitem" href="<%=request.contextPath%>/process/examing/${procstep.id}">${procstep?.step?.name}</a></li>
                                                            </sec:ifAnyGranted>
                                                            <sec:ifNotGranted roles="ROLE_ADMIN,ROLE_SUPER">
                                                                <li role="presentation"><a role="menuitem" href="javascript:;" onclick="alert('你无权进行此步操作')"><span class='glyphicon glyphicon-ban-circle text-danger'></span>${procstep?.step?.name}</a></li>
                                                            </sec:ifNotGranted>
                                                        </g:else>
                                                    </g:if>
                                                    <g:else>
                                                        <li role="presentation"><a class="undo" href="javascript:;" onclick="alert('还没进展到此步')"><span class='glyphicon glyphicon-ban-circle text-danger'></span>${procstep?.step?.name}</a></li>
                                                    </g:else>
                                                </g:if>
                                                <g:if test="${stepId == 11}">
                                                    <g:if test="${allProcStep[i-1].status == 3}">
                                                        <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_SUPER">
                                                            <li role="presentation"><a data-step="${stepId}" data-sgroup="${procstep.sgroup}" data-status="${procstep.status}" role="menuitem" tabindex="-1" href="<%=request.contextPath%>/process/picedit/${procstep.id}">${procstep?.step?.name}</a></li>
                                                        </sec:ifAnyGranted>
                                                        <sec:ifNotGranted roles="ROLE_ADMIN,ROLE_SUPER">
                                                            <li role="presentation"><a role="menuitem" href="javascript:;" onclick="alert('你无权进行此步操作')"><span class='glyphicon glyphicon-ban-circle text-danger'></span>${procstep?.step?.name}</a></li>
                                                        </sec:ifNotGranted>
                                                    </g:if>
                                                    <g:else>
                                                        <li role="presentation"><a class="undo" href="javascript:;" onclick="alert('还没进展到此步')"><span class='glyphicon glyphicon-ban-circle text-danger'></span>${procstep?.step?.name}</a></li>
                                                    </g:else>
                                                </g:if>
                                                <g:if test="${stepId == 12}">
                                                    <g:if test="${allProcStep[i-1].status == 3}">
                                                        <g:if test="${curUser.id == projectInstance.user?.id}">
                                                            <li role="presentation"><a data-step="${stepId}" data-sgroup="${procstep.sgroup}" data-status="${procstep.status}" role="menuitem" href="<%=request.contextPath%>/process/verify/${procstep.id}">${procstep?.step?.name}</a></li>
                                                        </g:if>
                                                        <g:else>
                                                            <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_SUPER">
                                                                <li role="presentation"><a data-step="${stepId}" data-sgroup="${procstep.sgroup}" data-status="${procstep.status}" role="menuitem" href="<%=request.contextPath%>/process/verify/${procstep.id}">${procstep?.step?.name}</a></li>
                                                            </sec:ifAnyGranted>
                                                            <sec:ifNotGranted roles="ROLE_ADMIN,ROLE_SUPER">
                                                                <li role="presentation"><a role="menuitem" href="javascript:;" onclick="alert('你无权进行此步操作')"><span class='glyphicon glyphicon-ban-circle text-danger'></span>${procstep?.step?.name}</a></li>
                                                            </sec:ifNotGranted>
                                                        </g:else>
                                                    </g:if>
                                                    <g:else>
                                                        <li role="presentation"><a class="undo" href="javascript:;" onclick="alert('还没进展到此步')"><span class='glyphicon glyphicon-ban-circle text-danger'></span>${procstep?.step?.name}</a></li>
                                                    </g:else>
                                                </g:if>
                                            </g:else>
                                        </g:each>
                                    </g:else>
                                    </ul>
                                    </div><hr/>
                                    <div class="panel-body">
                                        <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_SUPER">
                                            <g:if test="${process.status < 8}">
                                                <a class="btn btn-primary" href="javascript:;" onclick="changeQc('${process.id}','${process?.charger?.id}')">更换监理</a>
                                            </g:if>
                                        </sec:ifAnyGranted>
                                    </div>
                                </div>
                            </g:if>
                            <g:elseif test="${process.status == 3}">
                                <div class="tab-pane" id="p${process.id}">
                                    <div class="panel-body">
                                        <p>工程状态：已完结</p>
                                        <p>验收结果：${process.memo}</p>
                                        <p>装修详情：</h4><a class="btn btn-default" target="_blank" href="<%=request.contextPath%>/process/pics/${process.id}">查看装修图片</a></p>
                                    </div>
                                </div>
                            </g:elseif>
                            <g:else>
                                <div class="tab-pane" id="p${process.id}"><div class="panel-body">此阶段还没开始</div></div>
                            </g:else>
                        </g:each>
                    </div>

                    </div>
                    <div class="row" id="imgContainer">
                        <div style="padding-left: 20px;padding-top: 120px;">
                            <h5><strong>施工时间对比轴（<span class="text-danger">红点</span>：计划结束时间，蓝点：实际结束时间）,鼠标移上去查看详情</strong></h5>
                            <div id="tline${projectInstance?.id}" style="max-height: 140px;"></div>
                        </div>
                    </div>
                </div>
                </g:else>
            </div>
        </div>
    </div>
    <g:if test="${projectInstance.status != 1}">
    <div class="modal fade" id="uploadDlg">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">上传图片</h4>
                </div>
                <div class="modal-body">
                    <form>
                        <input type="hidden" id="upProcessId" name="processIdw" value="999">
                        <div class="row">
                            <label for="upfile" class="col-sm-3 control-label">选择图片</label>
                            <div class="col-sm-9"><input type="file" id="upfile" name="upfile" class="form-control" multiple="true"></div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" data-loading-text="上传中..." id="btnUpload" onclick="startUpload($(this))">上传照片</button>
                    <button type="button" class="btn btn-danger" data-loading-text="申请中..." onclick="applyExamine($(this))">申请审核</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div>
    <div id="changeQcContainer"></div>
    <script type="application/javascript">
        $(function(){
            //初始化时间控件
            //initDatepicker($("#planStartDate"),$("#planEndDate"),1);
            initStep();
            //多文件上传
            $("#upfile").uploadify({
                'auto'     : false,
                'buttonText' : '选择图片',
                height        : 30,
                width         : 120,
                'uploadLimit' : 20,
                'fileSizeLimit' : '4MB',
                swf           : '${resource(dir: 'img', file: 'uploadify.swf')}',
                uploader      : contextPath+'process/upload',
                'onUploadComplete' : function(file) {
                    $("#btnUpload").button('reset');
                }
            });
            //显示进度日期
            $(".point a").tooltip('show');
            //画工期对比图
            drawTimeLine("${projectInstance?.id}");
        });
//        function showSetTimeDlg(processId){
//            $("#hdProcessId").val(processId);
//            $("#setTimeDlg").modal({show:true,backdrop:'static'});
//        }
        function showUpfileDlg(processId){
            $("#upProcessId").val(processId);
            $("#uploadDlg").modal({show:true,backdrop:'static'});
        }
        function startUpload(btn){
            btn.button('loading');
            $('#upfile').uploadify('settings','uploader',contextPath+'process/upload?procstepid='+$("#upProcessId").val());
            $('#upfile').uploadify('upload','*')
        }
        function initStep(){
            var allStep = $("#curProcStep li a[data-status='1']:first");
            if(allStep && allStep.length > 0){
                var step = allStep.data("step");
                if(step < 5){
                    var disabledItems = $("#curProcStep li a[data-sgroup='1']");
                    if(disabledItems){
                        disabledItems.attr("href","javascript:;");
                        disabledItems.removeAttr("onclick");
                        var obj;
                        $.each(disabledItems,function(i,item){
                            obj = $(item);
                            obj.html("<span class='glyphicon glyphicon-ban-circle text-danger'></span>" + obj.html());
                            obj.addClass("disabled");
                        });
                    }
                }
            }
        }
    </script>
    <script src="<%=request.contextPath%>/js/jquery.uploadify.js" type="text/javascript"></script>
    <script src="<%=request.contextPath%>/js/raphael-min.js" type="text/javascript"></script>
    <script src="<%=request.contextPath%>/js/morris.js" type="text/javascript"></script>
    </g:if>
	</body>
</html>
