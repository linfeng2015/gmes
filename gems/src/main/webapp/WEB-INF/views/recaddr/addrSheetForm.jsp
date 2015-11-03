<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>地址簿管理</title>
	
	<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#addrSheet_name").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>
</head>

<body>
	<form id="inputForm" action="${ctx}/addrsheet/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${addrSheet.id}"/>
		<fieldset>
			<legend><small>管理工作簿</small></legend>
			<div class="control-group">
				<label for="addrSheet_name" class="control-label">名称:</label>
				<div class="controls">
					<input type="text" id="addrSheet_name" name="name"  value="${addrSheet.name}" class="input-large required" minlength="3"/>
				</div>
			</div>	
			
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
			</div>
		</fieldset>
	</form>
</body>
</html>
