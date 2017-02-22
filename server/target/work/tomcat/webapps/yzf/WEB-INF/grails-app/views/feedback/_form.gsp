<%@ page import="co.yzf.Feedback" %>
<div class="panel-body">
<div class="form-group">
	<label for="title" class="col-sm-2 control-label">
		反馈主题
	</label>
    <div class="col-sm-10"><g:textField name="title" class="form-control" maxlength="100" value="${feedbackInstance?.title}"/></div>
</div>

<div class="form-group">
	<label for="content" class="col-sm-2 control-label">
		反馈内容
	</label>
    <div class="col-sm-10"><g:textArea id="fcontent" name="content" cols="40" rows="5" class="form-control" maxlength="1023" value="${feedbackInstance?.content}"/>
    <input type="hidden" id="summary" name="summary" value="${feedbackInstance?.summary}"/></div>
</div>
</div>
<script src="<%=request.getContextPath()%>/ck/ckeditor.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
<script type="application/javascript">
    var editor;
    $(function(){
        var spacing_H = 400;//
        var max_H = $(window).height()-spacing_H > 0?$(window).height()-spacing_H:300;
        CKEDITOR.replace( 'fcontent' ,{
            height:max_H,
            autoGrow_minHeight:max_H,
            autoGrow_maxHeight: max_H,
            magicline_color: '#498af4'
        });
        editor = CKEDITOR.instances.fcontent;
    });
</script>
