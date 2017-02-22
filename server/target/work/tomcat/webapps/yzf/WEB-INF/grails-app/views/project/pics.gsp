<%@ page import="co.yzf.Project" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
        <title>查看图片</title>
	</head>
	<body>
		<div class="content container" role="main">
			<g:if test="${flash.message}">
			<div class="alert alert-warning" role="status">${flash.message}</div>
			</g:if>
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-12">
                    <g:render template="/templates/procinfo" model="['process':process,'material':material]" />
                    <g:if test="${mPicList != null}">
                        <g:render template="/templates/piclist" model="['picList':mPicList,'editflag':0,title:'材料图库（验收结果：'+mAtti+')']" />
                    </g:if>
                    <g:render template="/templates/piclist" model="['picList':pPicList,'editflag':0,title:'施工过程图库（验收结果：'+pAtti+')']" />
                </div>
            </div>
		</div>
	</body>
</html>
