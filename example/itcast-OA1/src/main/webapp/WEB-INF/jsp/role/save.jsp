<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
	<title>岗位设置</title>
   	<%@include file="/WEB-INF/jsp/public/header.jsp" %>
</head>
<body> 

<!-- 标题显示 -->
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 岗位修改
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<!--显示表单内容-->
<div id="MainArea">
	<!-- 注意下面抽取等式可以合并add.jsp和edit.jsp的页面，同时注意等式中的单引号 -->
    <s:form action="role_%{id==null?'add':'edit'}" namespace="/" method="post">
    	<s:hidden name="id"></s:hidden><!-- 隐藏域用于保持住传递过来的id -->
        <div class="ItemBlock_Title1"><!-- 信息说明<DIV CLASS="ItemBlock_Title1">
        	<IMG BORDER="0" WIDTH="4" HEIGHT="7" SRC="${pageContext.request.contextPath}/style/blue/images/item_point.gif" /> 岗位信息 </DIV>  -->
        </div>
        
        <!-- 表单内容显示 -->
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table cellpadding="0" cellspacing="0" class="mainForm">
                    <tr>
                        <td width="100">岗位名称</td>
                        <td>
                        <s:textfield name="name" cssClass="InputStyle"></s:textfield>
                        *
                        </td>
                    </tr>
                    <tr>
                        <td>岗位说明</td>
                        <td>
                        	<s:textarea name="description" cssClass="TextareaStyle"></s:textarea>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        
        <!-- 表单操作 -->
        <div id="InputDetailBar">
            <input type="image" src="${pageContext.request.contextPath}/style/images/save.png"/>
            <a href="javascript:history.go(-1);"><img src="${pageContext.request.contextPath}/style/images/goBack.png"/></a>
        </div>
        <!-- 没有按钮代码？？？？ -->
    </s:form>
</div>

</body>
</html>