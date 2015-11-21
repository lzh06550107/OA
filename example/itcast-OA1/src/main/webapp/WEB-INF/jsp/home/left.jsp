<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>导航菜单</title>
<%@include file="/WEB-INF/jsp/public/header.jsp"%>
<script language="JavaScript" src="script/menu.js"></script>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath }/style/blue/menu.css" />
</head>
<body style="margin: 0">
	<div id="Menu">
		<ul id="MenuUl">
			<s:iterator value="#application.privilegeTopList">
				<!-- 是否有权限来显示该菜单 -->
				<s:if test="#session.user.hasPrivilegeByName(name)">
				<li class="level1">
					<div onClick="menuClick(this);" class="level1Style">
						<img
							src="${pageContext.request.contextPath }/style/images/MenuIcon/${id }.gif"
							class="Icon" /> ${name }
					</div>
					<ul style="display: none;" class="MenuLevel2">
						<!-- 为什么可以这样写children,是因为父迭代器已经把privilegeTopList对象放在值栈顶 -->
						<s:iterator value="children">
						<s:if test="#session.user.hasPrivilegeByName(name)">
							<li class="level2">
								<div class="level2Style">
									<img
										src="${pageContext.request.contextPath }/style/images/MenuIcon/menu_arrow_single.gif" />
									<!--<s:a target="right" action="%{url}" namespace="/"> ${name }</s:a>-->
									<a target="right" href="${pageContext.request.contextPath }${url}.action">${name }</a>
								</div>
							</li>
							</s:if>
						</s:iterator>
					</ul>
				</li>
				</s:if>
			</s:iterator>
		</ul>
	</div>
</body>
</html>
