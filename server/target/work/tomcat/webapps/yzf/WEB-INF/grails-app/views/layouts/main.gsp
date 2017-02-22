<%
    String contextPath = request.contextPath;
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="content-language" content="zh-CN"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
        <!--[if lt IE 9]>
            <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.min.js"></script>
            <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
		<title><g:layoutTitle default="业之峰"/></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
        <link rel="stylesheet" href="${contextPath}/css/bootstrap.css" type="text/css">
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'yzf.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css">
        <script src="${contextPath}/js/jquery-1.11.js" type="text/javascript"></script>
		<g:layoutHead/>
		<r:layoutResources />
	</head>
	<body>
        <g:render template="/templates/hdmemu"/>
        <r:layoutResources />
		<g:layoutBody/>
		<div class="footer" role="contentinfo"></div>
		<div id="spinner" class="spinner" style="display:none;"></div>

        <script src="${contextPath}/js/bootstrap.js" type="text/javascript"></script>
        <script src="${contextPath}/js/jquery-ui-1.10.4.min.js" type="text/javascript"></script>
        <script src="${contextPath}/js/yzf.js" type="text/javascript"></script>
        <script type="application/javascript">
            var contextPath = "${contextPath}/";
        </script>
	</body>
</html>
