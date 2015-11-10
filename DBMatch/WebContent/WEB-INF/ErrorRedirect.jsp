<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Redirecting...</title>
		<script type="text/javascript">
			window.onload = function() {
				var button = document.getElementById("button");
				button.click();
			}
		</script>
	</head>
	<body>
		<form method="get" action="${pageContext.request.contextPath}${model.viewLocation}">
			<input type="hidden" name="message" value="${model.model}" />
			<input id="button" type="submit" value="Click to continue to the next page" />
		</form>
	</body>
</html>