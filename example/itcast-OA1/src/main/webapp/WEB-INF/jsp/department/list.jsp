<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>部门列表</title>
<%@include file="/WEB-INF/jsp/public/header.jsp"%>
</head>
<body>
	<div id="Title_bar">
		<div id="Title_bar_Head">
			<div id="Title_Head"></div>
			<div id="Title">
				<!--页面标题-->
				<img border="0" width="13" height="13"
					src="${pageContext.request.contextPath}/style/images/title_arrow.gif" />
				部门管理
			</div>
			<div id="Title_End"></div>
		</div>
	</div>

	<div id="MainArea">
		<table cellspacing="0" cellpadding="0" class="TableStyle">

			<!-- 表头-->
			<thead>
				<tr align="CENTER" valign="MIDDLE" id="TableTitle">
					<td width="150px">部门名称</td>
					<td width="150px">上级部门名称</td>
					<td width="200px">职能说明</td>
					<td>相关操作</td>
				</tr>
			</thead>

			<!--显示数据列表-->
			<tbody id="TableData" class="dataContainer" datakey="departmentList">
				<s:iterator value="list">
					<tr class="TableDetail1 template">
						<td><s:a action="department_list?parentId=%{id}"
								namespace="/">${name}</s:a>&nbsp;</td>
						<td>${parent.name}&nbsp;</td>
						<td>${description}&nbsp;</td>
						<!-- 因为是struts标签，action中地址会自动加上action或者do后缀，如果使用a标签则要加上 -->
						<td><s:a onclick="return window.confirm('确定删除当前记录吗？')"
								action="department_delete?id=%{   id   }" namespace="/">删除</s:a>
							<s:a action="department_editUI?id=%{  id  }" namespace="/">修改</s:a>
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>

		<!-- 其他功能超链接 -->
		<div id="TableTail">
			<div id="TableTail_inside">
				<!-- 也可用s:a标签 -->
				<a
					href="${pageContext.request.contextPath}/department_addUI.action?parentId=${parentId}"><img
					src="${pageContext.request.contextPath}/style/images/createNew.png" />
				</a>
				
				<s:if test="parentId != null">
					<s:a action="department_list?parentId=%{model.parent.id}" namespace="/">
						<img alt="" src="${pageContext.request.contextPath }/style/blue/images/button/ReturnToPrevLevel.png" />
					</s:a>
				</s:if>
			</div>
		</div>
	</div>
</body>
</html>