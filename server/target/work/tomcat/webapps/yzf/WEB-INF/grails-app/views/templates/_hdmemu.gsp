<div class="navbar navbar-inverse navbar-fixed-top bs-docs-nav" role="banner">
    <div class="container">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
    </div>
    <div class="collapse navbar-collapse" role="navigation" style="height: 1px;">
    <ul id="admin_menu" class="nav navbar-nav">
        <li><a href="<%=request.getContextPath()%>/" class="dropdown-toggle">首页</a></li>
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">订单管理<b class="caret"></b></a>
            <ul class="dropdown-menu">
                <li id="companyTab"><a href="<%=request.getContextPath()%>/project/index">订单列表</a></li>
                <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_SUPER">
                    <li id="certifiedTab"><a href="<%=request.getContextPath()%>/project/create">签订订单</a></li>
                </sec:ifAnyGranted>
            </ul>
        </li>
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="javascript:;">客服专栏<b class="caret"></b></a>
            <ul class="dropdown-menu">
                <li><a href="<%=request.getContextPath()%>/feedback/create">反馈问题</a></li>
                <li><a href="<%=request.getContextPath()%>/feedback">反馈列表</a></li>
            </ul>
        </li>
        <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_SUPER">
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">用户 <b class="caret"></b></a>
            <ul class="dropdown-menu">
                <li id="addUser"><a href="<%=request.getContextPath()%>/user/addUser">添加用户</a></li>
                <li id="listUser"><a href="<%=request.getContextPath()%>/user/listAll">用户列表</a></li>
            </ul>
        </li>
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">系统设定<b class="caret"></b></a>
            <ul class="dropdown-menu">
                <li id="materialTab"><a href="<%=request.getContextPath()%>/material/index">材料管理</a></li>
                <li id="materialCategoryTab"><a href="<%=request.getContextPath()%>/material/create">追加材料</a></li>
                <li role="presentation" class="divider"></li>
                <li id="periodTab"><a href="<%=request.getContextPath()%>/period/index">工程阶段管理</a></li>
                <li id="periodCategoryTab"><a href="<%=request.getContextPath()%>/period/create">追加工程阶段</a></li>
            </ul>
        </li>
        </sec:ifAnyGranted>
        <li><a href="<%=request.getContextPath()%>/down/yzfapp.apk" class="dropdown-toggle">App下载</a></li>
    </ul>
    <!--
    <form class="navbar-form navbar-left hidden-xs" role="search">
        <div class="form-group" style="max-width: 280px;">
            <div class="input-group">
                <input type="text" class="form-control" placeholder="输入检索内容">
                <span class="input-group-btn">
                    <button class="btn btn-default" type="button">检索</button>
                </span>
            </div>
        </div>
    </form>
    -->
    <ul class="nav navbar-nav navbar-right">
        <li><a href="javascript:;">你好，<sec:loggedInUserInfo field="username"/></a></li>
        <li id="logout"><a href="<%=request.getContextPath()%>/j_spring_security_logout">退出</a></li>
    </ul>
    </div>
    </div>
</div>