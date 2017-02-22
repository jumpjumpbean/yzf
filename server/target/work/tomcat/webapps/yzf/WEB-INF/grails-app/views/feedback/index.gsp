<%@ page import="co.yzf.Feedback" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>反馈列表</title>
	</head>
	<body>
		<div id="list-feedback" class="content container" role="main">
			<g:if test="${flash.message}">
				<div class="message" class="alert alert-warning" role="status">${flash.message}</div>
			</g:if>
            <div class="row">
                <div class="col-xs-12 col-sm-10 col-md-8">
                    <ul class="media-list">
                    <g:if test="${feedbackInstanceList != null && feedbackInstanceList.size() > 0}">
                    <g:each in="${feedbackInstanceList}" status="i" var="feedbackInstance">
                        <li class="list-group-item feedbackb">
                            <div class="list-group-item-heading"><h4><g:link action="show" class="btn-link" resource="${feedbackInstance}">${feedbackInstance?.title}</g:link><small class="text-primary pull-right">${feedbackInstance?.user}发布于<g:formatDate date="${feedbackInstance?.dateCreated}" format="yyyy-MM-dd" /></small></h4></div>
                            <hr/>
                            <div class="list-group-item-text">
                                <h5>${feedbackInstance?.summary}</h5>
                            </div>
                            <div class="text-right btn-group">
                                <g:link action="show" class="btn btn-default btn-sm" resource="${feedbackInstance}">查看详细</g:link>
                                <g:if test="${curUser?.id == feedbackInstance.user.id}">
                                    <g:link action="edit" class="btn btn-default btn-sm" resource="${feedbackInstance}">编辑</g:link>
                                    <g:link action="delete" class="btn btn-warning btn-sm" resource="${feedbackInstance}" onclick="return confirm('确定要删除？');">删除</g:link>
                                </g:if>
                                <a class="btn btn-info btn-sm" onclick="$('#hdFeedbackId').val('${feedbackInstance?.id}');$('#feedbackDlg').modal('show');">快速回复</a>
                            </div>
                        </li>
                    </g:each>
                    </g:if>
                    <g:else>
                        <li class="list-group-item feedbackb"><p>暂时没有问题反馈</p></li>
                    </g:else>
                    </ul>
                </div>
            </div>
			<div class="pagination">
				<g:paginate total="${feedbackInstanceCount ?: 0}" />
			</div>
		</div>
        <div class="modal fade" id="feedbackDlg">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">快速回复</h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <input type="hidden" id="hdFeedbackId">
                            <textarea cols="80" rows="3" maxlength="1000" class="form-control" placeholder="请输入回复的内容" id="fbContent"></textarea>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" data-loading-text="处理中..." class="btn btn-primary" onclick="fbcomment($(this),true)">确定</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div>
	</body>
</html>
