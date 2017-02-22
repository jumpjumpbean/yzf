<div class="panel panel-default">
<g:if test="${title != null}">
    <div class="panel-heading">
        ${title}
    </div>
</g:if>
<div class="panel-body" id="_picbox">
<g:if test="${picList == null || picList.size() == 0}">
    <p>暂时还未上传图片</p>
</g:if>
<g:else>
    <g:each in="${picList}" var="pic">
        <g:if test="${pic != null}">
            <g:if test="${editflag == 1}">
                <div class="upld" onclick="$(this).toggleClass('selected')" data-pid="${pic.id}">
                    <img src="<%=request.contextPath%>/upld/${pic.picPath}/${pic.picName}">
                    <span class="ico glyphicon glyphicon-trash"></span>
                </div>
            </g:if>
            <g:else>
                <div class="upld">
                    <img src="<%=request.contextPath%>/upld/${pic.picPath}/${pic.picName}">
                </div>
            </g:else>
        </g:if>
    </g:each>
</g:else>
</div>
</div>