<%@page import="java.util.List"%>
<%@page import="org.talend.administrator.common.constants.Constants"%>
<%@ taglib uri="/WEB-INF/displaytag-el-12-10.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/talend.tld" prefix="talend" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@page import="org.talend.administrator.persistence.proxy.UserProxy"%>

<talend:messages errors="true" />
<talend:messages />

<%
	List<UserProxy> users = (List<UserProxy>) request.getSession().getAttribute(Constants.USER_LIST);
	request.setAttribute("listeUsers", users);
    
	if(users != null) {
%>
<bean:define id="listeUsers" name="listeUsers" scope="request" />
<display:table 
	name="listeUsers" 
	formName="userForm"
	formMethod="post"
	formAction="actionUser.do"
	requestURI="" 
	width="100%" 
	sort="list" 
	defaultsort="2" 
	defaultorder="ascending" 
	editablePagesize="true"
	pagesizeInSession="true"
	pagesize="10"
>
	<display:column property="inputCheckboxField" class="cbox" headerClass="nopr" title="" decorator="org.displaytag.decorator.wrapper.InputCheckboxDecorator" />

	<display:column property="displayLink" titleKey="user.login" sortable="true" filterType="text" decorator="org.talend.administrator.common.view.displaytag.decorator.LinkDecorator" />
	<display:column property="lastName" filterType="text" titleKey="user.lastname" sortable="true" headerClass="sortable" width="75" style="text-align:center" />
	<display:column property="firstName" filterType="text" titleKey="user.firstname" sortable="true" headerClass="sortable" width="75" style="text-align:center" />
	<display:column property="dateCreation" filterType="list_filtered" titleKey="user.creationdate" sortable="true" headerClass="sortable" width="75" style="text-align:center" decorator="org.talend.administrator.common.view.displaytag.decorator.DateDecorator"/>
	<display:column property="active" filterType="list" titleKey="user.active" sortable="true" headerClass="sortable" width="75" style="text-align:center" />
	<display:column property="role" filterType="list" titleKey="user.role" sortable="true" headerClass="sortable" width="75" style="text-align:center" decorator="org.talend.administrator.common.view.displaytag.decorator.I18nDecorator"/>
	<display:column class="lastcell" filterCellClass="lastcell" headerClass="lastcell">&nbsp;</display:column>
</display:table>
<%
	}
%>

<%
String urlDelete="javascript:submitForm('userForm','?method=" + Constants.Action.CONFIRMDELETE + "');"; 
String urlEdit="javascript:submitForm('userForm','?method=" + Constants.Action.UPDATE + "');"; 
String urlAdd= "javascript:submitForm('userForm','?method=" + Constants.Action.ADD + "');";
%>

<talend:actionsBar styleClass="fixed">
	<talend:link titleKey="common.action.add" path="<%=urlAdd%>" titleStyling="true"/>
	<talend:link titleKey="common.action.edit" path="<%=urlEdit%>" titleStyling="true"/>
	<talend:link titleKey="common.action.delete" path="<%=urlDelete%>" titleStyling="true" titleDispatching="true" />
	<talend:link titleKey="common.action.print" javascript="printPage();" titleStyling="true" />
</talend:actionsBar>
