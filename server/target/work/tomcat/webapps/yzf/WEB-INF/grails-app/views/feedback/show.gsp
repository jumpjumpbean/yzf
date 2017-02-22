
<%@ page import="co.yzf.Feedback" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>${feedbackInstance?.title}</title>
	</head>
	<body>
		<div id="show-feedback" class="content container" role="main">
			<g:if test="${flash.message}">
			<div class="alert alert-warning" role="status">${flash.message}</div>
			</g:if>
            <div class="row">
                <div class="col-xs-12 col-sm-10 col-md-8">
                    <ul class="list-group">
                        <li class="list-group-item">
                            <div class="list-group-item-heading">
                                <h4>
                                    ${feedbackInstance?.title}
                                    <small class="text-primary pull-right">${feedbackInstance?.user}发布于<g:formatDate date="${feedbackInstance?.dateCreated}" format="yyyy-MM-dd" /></small>
                                    <g:if test="${curUser?.id == feedbackInstance.user.id}">
                                        <p>&nbsp;
                                            <g:link action="edit" class="btn btn-default btn-sm pull-right" resource="${feedbackInstance}">编辑</g:link>
                                            <g:link action="delete" class="btn btn-warning btn-sm pull-right" resource="${feedbackInstance}" onclick="return confirm('确定要删除？');">删除</g:link>
                                        </p>
                                    </g:if>
                                </h4>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class="list-group-item-text" id="fdbcontent">
                                ${feedbackInstance?.content}
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12 col-sm-10 col-md-8">
                    <ul class="list-group">
                        <li class="list-group-item">
                            <input type="hidden" id="hdFeedbackId" value="${feedbackInstance?.id}">
                            <textarea cols="80" rows="3" maxlength="1000" class="form-control" placeholder="请输入回复的内容" id="fbContent"></textarea>
                            <p class="text-right" style="padding-top: 5px;"><button type="button" class="btn btn-primary btn-sm" data-loading-text="处理中..." onclick="fbcomment($(this))">回复</button> </p>
                        </li>
                        <g:each in="${replyList}" var="reply">
                            <li class="list-group-item">
                                <h4>
                                    <div class="list-group-item-text"> ${reply?.content}</div>
                                    <div class="text-right">
                                        <small class="text-primary pull-right">${reply?.user}发布于<g:formatDate date="${reply?.dateCreated}" format="yyyy-MM-dd HH:mm" /></small>
                                    </div>
                                </h4>
                            </li>
                        </g:each>
                    </ul>
                </div>
            </div>
		</div>
    <script type="application/javascript">
        $(function(){
            $("#fdbcontent").html(HTMLDecode($("#fdbcontent").html()));
        });
    </script>
	</body>
</html>
