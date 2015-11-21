<!-- 使用超链接 ，自己实现，但不具有通用性 -->
<div id=PageSelectorBar>
	<div id=PageSelectorMemo>页次：${currentPage }/${pageCount }页 &nbsp;
		每页显示：${pageSize }条 &nbsp; 总记录数：${recordCount }条</div>
	<div id=PageSelectorSelectorArea>

		<s:a action="topic_show?currentPage=1&id=%{id}" label="首页" cssStyle="cursor: hand;" namespace="/">
			<img src="${pageContext.request.contextPath}/style/blue/images/pageSelector/firstPage.png" />
		</s:a>

		<s:iterator begin="beginPageIndex" end="endPageIndex" var="s">
			<s:if test="currentPage==%{s}">
				<span class="PageSelectorNum PageSelectorSelected">${s }</span>
			</s:if>
			<s:else>
				<s:url action="topic_show" namespace="/" var="showPage">
					<s:param name="id">${id }</s:param>
					<s:param name="currentPage">${s }</s:param>
				</s:url>
				<!-- 注意不要使用action属性，否则会重复应用程序名字 -->
				<s:a href="%{showPage}" cssClass="PageSelectorNum" cssStyle="cursor: hand;" namespace="/">${s }</s:a>
			</s:else>
		</s:iterator>

		<s:a action="topic_show?currentPage=%{s}&id=%{id }" label="尾页" cssStyle="cursor: hand;" namespace="/">
			<img src="${pageContext.request.contextPath}/style/blue/images/pageSelector/lastPage.png" />
		</s:a>

		转到： <select id="pages" onchange="gotoPageNum(this.value)">
			<s:iterator begin="1" end="pageCount" var="s">
				<option value="${s}">${s }</option>
			</s:iterator>
		</select>
	</div>
</div>
<script type="text/javascript">
	function gotoPageNum(pageNum){
		window.location.href="${pageContext.request.contextPath}/topic_show.action?currentPage="+pageNum+"&id=${id}";
	}
	$("#pages").val(${currentPage});
</script>