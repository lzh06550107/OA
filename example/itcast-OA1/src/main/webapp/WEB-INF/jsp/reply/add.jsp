<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
	<title>帖子回复</title>
    <%@include file="/WEB-INF/jsp/public/header.jsp" %>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style/blue/forum.css" />
	<script src="${pageContext.request.contextPath}/ckeditor/ckeditor.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/ckeditor/samples/sample.css">
</head>
<body>

<!-- 标题显示 -->
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 帖子回复
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<!--显示表单内容-->
<div id="MainArea">
<s:form action="reply_add" namespace="/" method="post" cssStyle="margin: 0; padding: 0;">
	<!-- 不可以使用<s:hidden/>因为获取值和赋给值的名称不同-->
	<input type="hidden" name="topicId" value="${id }"/>
	<div id="PageHead"></div>
	<center>
		<div class="ItemBlock_Title1">
			<div width=85% style="float:left">
				<font class="MenuPoint"> &gt; </font>
					<s:a action="forum_list" namespace="/">论坛</s:a>
				<font class="MenuPoint"> &gt; </font>
					<s:a action="forum_show?id=%{forum.id}" namespace="/">${forum.name}</s:a>
				<font class="MenuPoint"> &gt;&gt; </font>
				帖子回复
			</div>
		</div>
		<div class="ItemBlockBorder">
			<table border="0" cellspacing="1" cellpadding="1" width="100%" id="InputArea">
				<tr>
					<td class="InputAreaBg" height="30"><div class="InputTitle">帖子主题</div></td>
					<td class="InputAreaBg"><div class="InputContent">${title }</div></td>
				</tr>
				<tr height="240">
					<td class="InputAreaBg"><div class="InputTitle">内容</div></td>
					<td class="InputAreaBg"><div class="InputContent"><textarea id="content" name="content"></textarea></div></td>
				</tr>
				<tr height="30">
					<td class="InputAreaBg" colspan="2" align="center">
						<input type="image" src="${pageContext.request.contextPath}/style/blue/images/button/submit.PNG" style="margin-right:15px;"/>
						<a href="javascript:history.go(-1);"><img src="${pageContext.request.contextPath}/style/blue/images/button/goBack.png"/></a>
					</td>
				</tr>
			</table>
		</div>
	</center>
</s:form>
</div>
</body>
</html>
<script>
		CKEDITOR.replace( 'content', {
			uiColor: '#14B8C4'
		});
</script>
