<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
    <title>待我审批</title>
     <%@include file="/WEB-INF/jsp/public/header.jsp" %>
</head>
<body> 

<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 待我审批
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<!--
<div id="QueryArea">
	<div style="height: 30px">
		<form action="#">
		<table border=0 cellspacing=3 cellpadding=5>
			<tr>
				<td>按条件查询：</td>
				<td><select name="arg1" class="SelectStyle">
						<option value="">查看全部模板</option>
						<option value="0">领款单</option>
						<option value="10">工作报告</option>
						<option value="13">设备采购单</option>
						<option value="21">员工请假单</option>
						<option value="22">加班申请</option>
					</select>
				</td>
				<td><a href=""><input type="IMAGE" src="${pageContext.request.contextPath}/style/blue/images/button/query.PNG"/></a></td>
			</tr>
		</table>
	
		</form>
	</div>
</div>
-->
<form>
	<input type="hidden" name="pageNum" value="1" />
</form>

<div id="MainArea">
    <table cellspacing="0" cellpadding="0" class="TableStyle">
        <!-- 表头-->
        <thead>
            <tr align="CENTER" valign="MIDDLE" id="TableTitle">
				<td width="250">标题</td>
				<td width="115">申请人</td>
				<td width="115">申请日期</td>
				<td>相关操作</td>
			</tr>
		</thead>		
		<!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" datakey="formList">
        	<s:iterator value="list">
				<tr class="TableDetail1 template">
					<td><a href="approveUI.html">${first.title}</a></td>
					<td>${first.applicant.name}&nbsp;</td>
					<td>
						<s:date name="first.applyTime" format="yyyy-MM-dd"/>
					&nbsp;</td>
					<td><s:a action="flow_approveUI?taskId=%{second.id}&applicationId=%{first.id}">审批处理</s:a>
						<!-- <a href="showForm.html">查看申请信息</a> -->
						<s:a action="flow_historyApprovedList?applicationId=%{first.id}">查看流转记录</s:a>
					</td>
				</tr>
        	</s:iterator>
        </tbody>
    </table>
    
    <!-- 其他功能超链接 -->
    <div id="TableTail"><div id="TableTail_inside"></div></div>
</div>

<!--分页信息-->
<div id=PageSelectorBar>
	<div id=PageSelectorMemo>
		页次：7/13页 &nbsp;
		每页显示：30条 &nbsp;
		总记录数：385条
	</div>
	<div id=PageSelectorSelectorArea>
		<!--
		<IMG SRC="${pageContext.request.contextPath}/style/blue/images/pageSelector/firstPage2.png"/>
		-->
		<a href="javascript:void(0)" title="首页" style="cursor: hand;">
			<img src="${pageContext.request.contextPath}/style/blue/images/pageSelector/firstPage.png"/></a>
		
		<span class="PageSelectorNum" style="cursor: hand;" onClick="gotoPageNum(2);">3</span>
		<span class="PageSelectorNum" style="cursor: hand;" onClick="gotoPageNum(2);">4</span>
		<span class="PageSelectorNum" style="cursor: hand;" onClick="gotoPageNum(2);">5</span>
		<span class="PageSelectorNum" style="cursor: hand;" onClick="gotoPageNum(2);">6</span>
		<span class="PageSelectorNum PageSelectorSelected">7</span>
		<span class="PageSelectorNum" style="cursor: hand;" onClick="gotoPageNum(2);">8</span>
		<span class="PageSelectorNum" style="cursor: hand;" onClick="gotoPageNum(2);">9</span>
		<span class="PageSelectorNum" style="cursor: hand;" onClick="gotoPageNum(2);">10</span>
		<span class="PageSelectorNum" style="cursor: hand;" onClick="gotoPageNum(2);">11</span>
		<span class="PageSelectorNum" style="cursor: hand;" onClick="gotoPageNum(2);">12</span>
		
		<!--
		<IMG SRC="${pageContext.request.contextPath}/style/blue/images/pageSelector/lastPage2.png"/>
		-->
		<a href="#" title="尾页" style="cursor: hand;"><img src="${pageContext.request.contextPath}/style/blue/images/pageSelector/lastPage.png"/></a>
		
		转到：
		<input onFocus="this.select();" maxlength="2" class="inputStyle" type="text" value="1" name="currPage" tabindex="0"/>
		<input type="submit" name="goBtn" value="Go" class="MiddleButtonStyle" />
	</div>
</div>

<div class="Description">
	说明：<br />
	1，这里列出的所有的表单状态都为"审批中"。
</div>

</body>
</html>
