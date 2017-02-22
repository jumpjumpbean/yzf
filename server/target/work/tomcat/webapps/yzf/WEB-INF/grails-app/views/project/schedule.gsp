<%@ page import="co.yzf.Project" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
        <title>项目启动</title>
	</head>
	<body>
		<div class="content container" role="main">
			<g:if test="${flash.message}">
			    <div class="alert alert-warning" role="status">${flash.message}</div>
			</g:if>
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-12">
                    <g:form controller="project" action="start" method="post" name="scheduleFrm">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">制定项目实施计划</h3>
                            <input type="hidden" name="projectId" value="${projectId}">
                        </div>
                        <ul class="list-group" id="schedule">
                            <li class="list-group-item row" style="margin-left:0;margin-right: 0;">
                                <div class="col-xs-12 col-sm-4 col-md-3"><strong>阶段名</strong></div>
                                <div class="col-xs-6 col-sm-4 col-md-3"><strong>计划开始日期</strong></div>
                                <div class="col-xs-6 col-sm-4 col-md-3"><strong>计划结束日期</strong></div>
                            </li>
                            <g:each in="${processList}" var="process">
                            <li class="list-group-item row" style="margin-left:0;margin-right: 0;">
                                <div class="col-xs-12 col-sm-4 col-md-3">${process.period}</div>
                                <div class="col-xs-6 col-sm-4 col-md-3">
                                    <input type="hidden" name="processId" value="${process.id}">
                                    <input class="form-control" name="pStartDate" type="text">
                                </div>
                                <div class="col-xs-6 col-sm-4 col-md-3">
                                    <input class="form-control" name="pEndDate"  type="text">
                                </div>
                            </li>
                            </g:each>
                        </ul>
                        <div class="panel-footer">
                            <a class="btn btn-primary" href="javascript:;" onclick="checkDate()">项目启动</a>
                            <a class="btn btn-default" href="<%=request.contextPath%>/project/show/${projectId}">返回</a>
                        </div>
                    </div>
                    </g:form>
                </div>
            </div>
		</div>
    <script type="application/javascript">
        $(function(){
            var allstart = $("#schedule input[name='pStartDate']");
            var allEnd = $("#schedule input[name='pEndDate']");
            $.each(allstart,function(i,item){
                initDatepicker($(item),$(allEnd[i]),2);
            });
        });
        function checkDate(){
            var allDate = $("#schedule input.form-control");
            var isvalid = true;
            var lastVal = "0000-01-01";
            $("#schedule div").removeClass("has-error");
            $.each(allDate,function(i,item){
              var itm = $(item);
              var itmv = itm.val();
              if(itmv == ""){
                  itm.parent().addClass("has-error");
                  isvalid = false;
              }else if(itmv < lastVal){
                  itm.parent().addClass("has-error");
                  isvalid = false;
              }
              lastVal = itmv;
            });
            if(!isvalid){
                showMessage("计划时间不能为空，并且不能小于上一个阶段的时间","error","keep");
            }else{
                $("#scheduleFrm").submit();
            }
        }
    </script>
	</body>
</html>
