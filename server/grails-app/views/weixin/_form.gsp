<%@ page import="co.yzf.Feedback" %>
<div class="panel-body">
<div class="form-group">
	<label for="title" class="col-sm-2 control-label">
		反馈主题
	</label>
    <div class="col-sm-10"><g:textField id="ftitle" name="title" class="form-control" maxlength="80" value=""/></div>
</div>

<div class="form-group">
	<label for="content" class="col-sm-2 control-label">
		反馈内容
	</label>
    <div class="col-sm-10"><g:textArea id="fcontent" name="content" cols="20" rows="5" class="form-control" maxlength="800" value=""/></div>
</div>
</div>