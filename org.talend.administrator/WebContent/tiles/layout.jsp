<%@ page language="java"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%
	String ctxPath = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<title><tiles:getAsString name="title" /></title>
	<script src="<%=ctxPath%>/js/common.js" type="text/javascript"></script>
	<link href="<%=ctxPath %>/css/display.css" rel="stylesheet" type="text/css" />
</head>
<body>
<table>
	<tbody>
		<tr>
			<td colspan="2" height="50"><tiles:insert attribute="header" /></td>
		</tr>
		<tr>
			<td width="150"><tiles:insert attribute="menu" /></td>
			<td width="100%" height="100%" valign="top">
			<table border="0" width="100%" cellpadding="0" cellspacing="0" valign="top">
			<tbody>
				<tr>
					<td height="30"><tiles:insert attribute="navigation" /></td>
				</tr>
				<tr>
					<td height="370" valign="top" align="center"><tiles:insert attribute="body" /></td>
				</tr>
			</tbody>
			</table>
			</td>
		</tr>
		<tr>
			<td colspan="2" height="50"><tiles:insert attribute="footer" /></td>
		</tr>
	</tbody>
</table>
</body>
</html:html>
