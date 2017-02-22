<%@ page import="co.yzf.Project" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
        <title>查看图片</title>
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'uploadify.css')}" type="text/css">
	</head>
	<body>
		<div class="content container" role="main">
			<g:if test="${flash.message}">
			<div class="alert alert-warning" role="status">${flash.message}</div>
			</g:if>
            <g:if test="${procstep.status == 2}">
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">上传照片</h3>
                        </div>
                        <div class="panel-body">
                            <button type="button" class="btn btn-danger" data-loading-text="处理中..." onclick="deletePic($(this))"><span class="glyphicon glyphicon-trash text-primary"></span>删除选中的图片</button>
                            <button type="button" class="btn btn-success" data-loading-text="处理中..." onclick="showUpfileDlg('${procstepid}')">上传照片</button>
                            <button type="button" class="btn btn-warning" data-loading-text="申请中..." onclick="applyExamine($(this),'${project.id}')">申请审核</button>
                            <p class="text-danger">小提示：点击图片可以选中，再次点击取消选中</p>
                            <input type="hidden" id="procstepid" value="${procstepid}">
                        </div>
                    </div>
                </div>
            </div>
            </g:if>
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-12">
                     <g:render template="/templates/piclist" model="['picList':picList,'editflag':1,title:'图库']" />
                </div>
            </div>
		</div>
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
                    <button type="button" class="btn btn-danger" data-loading-text="申请中..." onclick="applyExamine($(this),'${project.id}')">申请审核</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div>
    <script type="application/javascript">
        $(function(){
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
        });
        function showUpfileDlg(processId){
            $("#upProcessId").val(processId);
            $("#uploadDlg").modal({show:true,backdrop:'static'});
            $('#uploadDlg').on('hide.bs.modal', function (e) {
                window.location.reload(true);
            })
        }
        function startUpload(btn){
            btn.button('loading');
            $('#upfile').uploadify('settings','uploader',contextPath+'process/upload?procstepid='+$("#upProcessId").val());
            $('#upfile').uploadify('upload','*')
        }
    </script>
    <script src="<%=request.contextPath%>/js/jquery.uploadify.js" type="text/javascript"></script>
	</body>
</html>
