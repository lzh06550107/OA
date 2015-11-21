<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!-- 使用表单来传递参数，具有通用性 -->
<div id=PageSelectorBar>
	<div id=PageSelectorMemo>页次：${currentPage }/${pageCount }页 &nbsp;
		每页显示：${pageSize }条 &nbsp; 总记录数：${recordCount }条</div>
	<div id=PageSelectorSelectorArea>

		<a href="#" onclick="gotoPageNum(1);" title="首页" style="cursor: hand;">
			<img src="${pageContext.request.contextPath}/style/blue/images/pageSelector/firstPage.png" />
		</a>

		<s:iterator begin="beginPageIndex" end="endPageIndex" var="s">
			<s:if test="currentPage==%{s}">
				<span class="PageSelectorNum PageSelectorSelected">${s }</span>
			</s:if>
			<s:else>
				<span onclick="gotoPageNum(${s});" class="PageSelectorNum" style="cursor: hand;">${s }</span>
			</s:else>
		</s:iterator>

		<a href="#" onclick="gotoPageNum(${pageCount});" title="尾页" style="cursor: hand;" >
			<img src="${pageContext.request.contextPath}/style/blue/images/pageSelector/lastPage.png" />
		</a>

		转到： <select id="pages" onchange="gotoPageNum(this.value);">
			<s:iterator begin="1" end="pageCount" var="s">
				<option value="${s}">${s }</option>
			</s:iterator>
		</select>
	</div>
</div>
<script type="text/javascript">
	function gotoPageNum(pageNum){
		/*
		如果没有其它过滤条件，用来提交前往的页码的信息currentPage，为了得到该字段信息：
			1)需要在Action中属性驱动int currentPage，并设初始值为1;并利用PageBean和HQLHelper类构造查询语句;
			2)静态引入分页代码：<%-- <%@include file="/WEB-INF/jsp/public/pageView.jsp" %> --%>
			3)在jsp页面中需要增加一个//<s:form id="pageForm" action=""></s:form>;
		如果有其它的过滤条件，前两步一样最后一步的表单中需要包含其它过滤条件的控件
		*/
		$("#pageForm").append('<input type="hidden" name="currentPage" value="'+ pageNum +'"/>');
		$("#pageForm").submit();
	}
	$("#pages").val(${currentPage});
</script>