<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="number" value="0"/>
<html>
<head>
	<title>邮件地址管理</title>
	<script>
	$(document).ready(function() {
		var sheetId = $("#sheetId").val();
		//alert("sheetId:"+sheetId);
		$("#addr_delete").click(function(){
			var ids="";
			$("input[name='recaddrId']:checked").each(function(){
				//ids=ids+"#"+this.value;
				ids = ids+":"+this.value;
			});
			var tmpPath = "${ctx}/recaddr/delete/"+ids+"?sheetId="+sheetId;
			$("#addr_delete").attr("href",tmpPath);
		});
	});
	</script>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<form id="inputForm" action="${ctx}/recaddr/uploadFile" method="post" class="form-horizontal" enctype="multipart/form-data" >
		<div>
			文件名称：<input type="file" name="addrFile" value="选择" />
				   <input type="hidden" name="sheetId" id="sheetId" value="${sheetId}">	
			<input type="submit" name="btnUpload" value="导入"/>
		</div>
	</form>
		<div class="row">
			<div  class="span4 offset7">
				<form class="form-search" action="${ctx}/recaddr/">
				 	<label>姓名：</label> <input  type="text" name="search_LIKE_name" class="input-medium" value="${param.search_LIKE_name}"> 
				 					    <input type="hidden" name="sheetId" value="${sheetId}">	
				    <button type="submit" class="btn">Search</button>
			    </form>
		    </div>
		    <tags:sort/>
		</div>
		<br/>
		<br/>
		<div>
			<table  border="1" width="100%" >
				<tr>
					<td align="center"></td>
					<td align="center">序号</td>
					<td align="center">邮件地址</td>
					<td align="center">公司</td>
					<td align="center">姓名</td>
				</tr>
				<c:forEach items="${recaddrs.content}"  var="recaddr">
					<c:set var="number" value="${number+1}"/>
					<tr>
						<td align="center"><input type="checkbox" name="recaddrId"  value="${recaddr.id}" /></td>
					    <td align="center">${number}</td>
						<td align="center">${recaddr.addr}</td>
						<td align="center">${recaddr.company}</td>
						<td align="center">${recaddr.name}</td>
						
					</tr>
				</c:forEach>
			</table>
		</div>
		<tags:pagination page="${recaddrs}" paginationSize="5"/>
	    <div><a class="btn" id="addr_delete" >删除</a>&nbsp;&nbsp;&nbsp; <a href="${ctx}/addrsheet" class="btn">返回</a>
	    </div>
</body>
</html>
