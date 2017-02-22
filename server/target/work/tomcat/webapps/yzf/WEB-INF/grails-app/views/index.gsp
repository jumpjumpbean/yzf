<!DOCTYPE html>
<html>
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="content-language" content="zh-CN"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
        <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
        <link rel="stylesheet" href="<%=request.contextPath%>/css/bootstrap.css" type="text/css">
        <script type="application/javascript">
           // window.location.href="<%=request.contextPath%>/project/index"
        </script>
        <style type="text/css">
            a{
                display: inline-block;
                float: left;
                width: 90px;
                height:60px;
                border: 1px #66afe9 solid;
                color: #000;
                background-color: rgba(102,176,217,0.5);
                margin-right: 10px;
                text-decoration: none;
                text-align: center;
                padding-top: 15px;
                font-size: 16px;
                vertical-align: middle;
            }
            a:hover{
                text-decoration: none;
                border: 1px #66afe9 solid;
                color: #fff;
                background-color: rgba(0,0,0,0.9);
                transition: background-color .25s ease-in-out, color .25s ease-in-out;
            }
            p{
                display: block;
                width: 200px;
                font-size: 20px;
                color: #ffffff;
                background:-webkit-gradient(linear, 0 0, 100% 0, from(#000), to(rgba(0, 0, 0, 0)));
            }
        </style>
	</head>
	<body style="background: #eee9ed url('./img/index_bg.jpg') center top;padding-top: 70px;">
        <div class="container">
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-12">
                    <p>订单管理</p>
                    <a href="<%=request.contextPath%>/project/index">我的订单</a>
                    <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_SUPER">
                    <a href="<%=request.contextPath%>/project/create">创建订单</a>
                    </sec:ifAnyGranted>
                </div>
            </div>
            <div class="row" style="margin-top: 20px;">
                <div class="col-xs-12 col-sm-12 col-md-12">
                    <p>客服专栏</p>
                    <a href="<%=request.contextPath%>/feedback/create">问题反馈</a>
                    <a href="<%=request.contextPath%>/feedback">反馈列表</a>
                </div>
            </div>
            <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_SUPER">
            <div class="row"  style="margin-top: 20px;">
                <div class="col-xs-12 col-sm-12 col-md-12">
                    <p>系统设定</p>
                    <a href="<%=request.contextPath%>/user/ListAll">所有用户</a>
                    <a href="<%=request.contextPath%>/user/addUser">添加用户</a>
                </div>
            </div>
            <div class="row"  style="margin-top: 10px;">
                <div class="col-xs-12 col-sm-12 col-md-12">
                    <a href="<%=request.contextPath%>/material/index">材料管理</a>
                    <a href="<%=request.contextPath%>/material/create">添加材料</a>
                </div>
            </div>
            <div class="row"  style="margin-top: 10px;">
                <div class="col-xs-12 col-sm-12 col-md-12">
                    <a href="<%=request.contextPath%>/period/create" style="padding-top: 5px;">添加<br/>工程阶段</a>
                    <a href="<%=request.contextPath%>/period/index" style="padding-top: 5px;">工程阶段<br/>管理</a>
                </div>
            </div>
            </sec:ifAnyGranted>
        </div>
	</body>
</html>
