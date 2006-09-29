<%@ page language="java"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ page import="org.talend.administrator.common.session.SessionManager"%>
<%@ page import="org.talend.administrator.persistence.proxy.UserProxy"%>
<%
	String ctxP = request.getContextPath();
	UserProxy user = (UserProxy) SessionManager.getUserSession(request);
	if (user==null) {
%>
<logic:redirect href="login/actionLogin.do?method=load"/>
<%
	} else {
%>
<table width="100%">
	<tr>
		<%
			if (user.isAdmin()) {
		%>
		<td nowrap="nowrap"><a href="<%=ctxP%>/user/actionUser.do?method=list">Menu
		User</a></td>
		<td nowrap="nowrap"><a href="<%=ctxP%>/project/actionProject.do">Menu
		Projets</a></td>
		<td nowrap="nowrap"><a
			href="<%=ctxP%>/component/actionComponent.do">Menu Composants</a></td>
		<%
			}
		%>

		<td nowrap="nowrap"><a
			href="<%=ctxP%>/login/actionLogin.do?method=delete">Quitter</a></td>
		<td width="100%"></td>
	</tr>
</table>
<%
	}
%>
