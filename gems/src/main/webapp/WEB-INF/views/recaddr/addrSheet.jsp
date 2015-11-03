<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="number" value="0"/>
<html>
<head>
<title>地址簿管理</title>
</head>
<body>
<c:if test="${not empty message}">
	<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
</c:if>
<div class="row">
	<div  class="span4 offset7">
		<form class="form-search" name="addrSheetForm" action="#">
				<label>名称：</label> <input  type="text" name="search_LIKE_name" class="input-medium" value="${param.search_LIKE_name}"> 
				<button type="submit" class="btn">Search</button>
		</form>
	</div>
	<tags:sort/>
</div>
<div>
	<table  border="1" width="100%" >
		<tr>
			<td align="center">序号</td>
			<td align="center">名称</td>
			<td align="center">操作</td>
		</tr>
		<c:forEach items="${addrSheets.content}"  var="addrSheet">
			<c:set var="number" value="${number+1}"/>
			<tr>
			    <td align="center">${number}</td>
				<td align="center"><a href="${ctx}/addrsheet/update/${addrSheet.id}">${addrSheet.name}</a></td>
				<td align="center">
						<a href="${ctx}/addrsheet/delete/${addrSheet.id}">删除</a>&nbsp;&nbsp;
						<a href="${ctx}/recaddr/view/${addrSheet.id}">邮件列表</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</div>
<tags:pagination page="${addrSheets}" paginationSize="5"/>
 <div><a href="${ctx}/addrsheet/create" class="btn" id="addrSheet_create" >增加</a></div>
</body>
</html>