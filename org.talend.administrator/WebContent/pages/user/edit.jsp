<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/talend.tld" prefix="talend" %>
<%@page import="org.talend.administrator.persistence.proxy.UserProxy"%>
<%@page import="org.talend.administrator.common.constants.Constants"%>
<%@page import="org.talend.administrator.common.constants.Constants.ReadMode" %>
<%@page import="org.talend.administrator.common.session.SessionManager"%>
<%@page import="org.talend.core.model.properties.UserRole"%>
<%@page import="java.util.List" %>

<%
	ReadMode readMode = (ReadMode)request.getAttribute(Constants.READ_MODE);
	boolean readOnly = (readMode == ReadMode.READ_ONLY) ? true : false;
	String styleClass = (readMode == ReadMode.READ_ONLY) ? "readOnly" : "";
	
	String thisAction = "/user/actionUser.do";
%>

<bean:define id="user" name="<%=Constants.USER.toString() %>" type="UserProxy" scope="session"/>

<bean:define id="listUserRole" name="<%=Constants.USER_ROLE.toString() %>" type="List" scope="session"/>

<html:form action="<%=thisAction %>" readonly="<%=readOnly%>" styleClass="<%=styleClass%>">
<html:xhtml/>
<html:hidden property="method" value="submit"/>

<html:hidden property="id" />
<html:hidden property="suppressionDateString" />
<html:hidden property="creationDateString" />

<div class="blocContenu">
<table cellspacing="0" class="basicForm">
	<tr><td><html:errors property="businessError"/></td></tr>
	<tr>
		<td class="lib"><bean:message key="user.login" /></td>
		<td width="300">
	<%if(readMode!=ReadMode.READ_ONLY){ %>
		<html:text property="login" styleClass="text" />
	<%} else {%>
		<bean:write name="user" property="login" />
	<%}%>
		</td>
		<td class="lib"><html:errors property="login" /></td>
	</tr>
	<tr>
		<td class="lib"><bean:message key="user.firstname" /></td>
		<td>
	<%if(readMode!=ReadMode.READ_ONLY){ %>
		<html:text property="firstName" styleClass="text" />
	<%} else {%>
		<bean:write name="user" property="firstName" />
	<%}%>
		</td>
		<td><html:errors property="firstName" /></td>
	</tr>
	<tr>
		<td class="lib"><bean:message key="user.lastname" /></td>
		<td>
	<%if(readMode!=ReadMode.READ_ONLY){ %>
		<html:text property="lastName" styleClass="text" />
	<%} else {%>
		<bean:write name="user" property="lastName" />
	<%}%>
		</td>
		<td><html:errors property="lastName" /></td>
	</tr>
	<tr>
		<td class="lib"><bean:message key="user.role" /></td>
		<td>
	<%if(readMode!=ReadMode.READ_ONLY){ %>
			<html:select property="roleId" styleClass="formSelect" >
      			<html:options collection="listUserRole" property="id" labelProperty="localizedLabel"  />
			</html:select>
	<%} else {%>
		<bean:message name="user" key="<%=user.getUserRole().getLocalizedLabel() %>" />
	<%}%>
		</td>
	</tr>
	<tr>
		<td class="lib"><bean:message key="user.password" /></td>
		<td>
	<%if(readMode!=ReadMode.READ_ONLY){ %>
		<html:password property="password" styleClass="text" />
	<%} else {%>
		<bean:write name="user" property="password" />
	<%}%>
		</td>
		<td><html:errors property="password" /></td>
	</tr>
	<% if (user.getId()!=0) { %>
	<tr>
		<td class="lib"><bean:message key="user.creationdate" /></td>
		<td>
			<bean:write name="user" property="dateCreationString"/>
		</td>
	</tr>
	<% if (!user.getActive()) { %>
	<tr>
		<td class="lib"><bean:message key="user.active" /></td>
		<td>
	<%
		if (readMode!=ReadMode.READ_ONLY) {  	
	%>	
		<html:checkbox property="active" styleClass="text" />
	<%
	   	} else {
	%>
		<bean:write name="user" property="active" />
	<%
	   	}
	%>
		</td>
	</tr>
	<% } else { %>
	<html:hidden property="active"/>
	<% } %>
	<% if (!user.getActive()) { %>
	<tr>
		<td class="lib"><bean:message key="user.suppressiondate" /></td>
		<td>
			<bean:write name="user" property="dateDeleteString" />
		</td>
	</tr>
	<% } %>
	<% } %>
	<tr>
		<td class="lib"><bean:message key="user.componentmodifier" /></td>
		<td>
	<%if(readMode!=ReadMode.READ_ONLY){ %>
		<html:checkbox property="componentModifier" styleClass="text" />
	<%} else {%>
		<bean:write name="user" property="componentModifier" />
	<%}%>
		</td>
	</tr>
	<tr>
		<td class="lib"><bean:message key="user.comment" /></td>
		<td>
	<% if (readMode!=ReadMode.READ_ONLY) { %>
			<html:textarea property="comment" styleClass="text" cols="100" rows="3"/>
	<% } else { %>
			<bean:write name="user" property="comment" />
	<% } %>
		</td>
	</tr>
</table>
</html:form>

<% 
UserRole role = SessionManager.getUserSession(request).getUserRole();
String thisUser = "?id=" + user.getId();
%>
<talend:actionsBar>
<%if(readMode==ReadMode.READ_ONLY){ %>
	<talend:link titleKey="common.action.backToList" path="<%=thisAction%>" dispatchParam="method" dispatchValue="<%=Constants.Action.LIST%>" titleStyling="true" />
	<talend:link titleKey="common.action.edit" role="<%=role%>" path="<%=thisAction+thisUser%>" dispatchParam="method" dispatchValue="<%=Constants.Action.UPDATE%>" titleStyling="true" />
	<% if (user.getActive()) { %><talend:link titleKey="common.action.delete" role="<%=role%>" path="<%=thisAction+thisUser%>" dispatchParam="method" dispatchValue="<%=Constants.Action.CONFIRMDELETE%>" titleStyling="true" /><% } %>
	<talend:link titleKey="common.action.print" javascript="printPage();" titleStyling="true" />
<%} else { %>
	<talend:link titleKey="common.action.cancelConfirm" path="<%=thisAction%>" dispatchParam="method" dispatchValue="<%=Constants.Action.LIST%>" titleStyling="true" />
	<talend:link titleKey="common.action.validate" role="<%=role%>" form="UserForm" titleStyling="true" />
<%}%>
</talend:actionsBar>
