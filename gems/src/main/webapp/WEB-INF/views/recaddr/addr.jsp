<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>邮件地址管理</title>
	<script>
		  
	</script>
</head>

<body>
	<form id="inputForm" action="${ctx}/recaddr/uploadFile" method="post" class="form-horizontal" enctype="multipart/form-data" >
		<div>
			文件名称：<input type="file" name="addrFile" value="选择" /><input type="button" name="btnUpload" value="导入"/>
			<input type="submit" name="save" value="保存"/>
		</div>
		<br/>
		<br/>
		
		<div>
			<table  border="1" width="100%" >
				<tr>
					<td align="center">序号</td>
					<td align="center">邮件地址</td>
					<td align="center">公司（姓名）</td>
				</tr>
				<c:forEach items="${recaddrs.content}"  var="recaddr">
					<tr>
						<td>${recaddr.addr}</td>
						<td>${recaddr.company}</td>
					</tr>
				</c:forEach>
			</table>		
		</div>
		
	</form>
</body>
</html>
