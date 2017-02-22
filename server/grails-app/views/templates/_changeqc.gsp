<div class="modal fade" id="changeQc">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">更换监理</h4>
                <input type="hidden" id="qcProcessId" value="${processId}">
            </div>
            <div class="panel-body">
            <ul class="list-unstyled">
                <li>
                    <div class="col-sm-3">现在监理</div>
                    <div class="col-sm-9"><g:if test="${curQc != null}">${curQc.uname}(电话：${curQc.telephone})</g:if><g:else>还未指定</g:else></div>
                </li>
                <li style="padding-top: 30px;">
                    <div class="col-sm-3">新监理</div>
                    <div class="col-sm-9"><g:select id="newqc" name="user.id" from="${co.yzf.UserRole.executeQuery("select user from UserRole ur where ur.role.authority='ROLE_SUPERVISOR'",[])}" optionKey="id" value="${curQc?.id}" class="form-control"/></div>
                </li>
            </ul>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" data-loading-text="处理中..." class="btn btn-primary" onclick="updateQc($(this))">更新监理</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>