<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag-el-12-10.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/talend.tld" prefix="talend" %>
<%@ page import="org.talend.administrator.common.session.ConfirmDeletion" %>
<%@page import="org.talend.administrator.common.constants.Constants"%>

<%
  ConfirmDeletion confirmDeletion = (ConfirmDeletion) request.getAttribute(Constants.Action.CONFIRMDELETE.toString());
%>

<bean:define id="confirm" name="<%=Constants.Action.CONFIRMDELETE.toString()%>" type="org.talend.administrator.common.session.ConfirmDeletion" scope="request" />

<div id="titreInfozone"><span><bean:write name="confirm" property="message"/></span></div>

<bean:message key="common.confirmDelete.errors.title"/>
<bean:message key="common.confirmDelete.messages.title"/>

<logic:equal name="confirm" property="deletable" value="false">
	<div id="infozone" class="erreur">
	<span>
		<logic:iterate id="element" name="confirm" property="blockingElements" type="org.talend.administrator.common.session.ConfirmDeletionElement">
		<bean:write name="element" property="elementMessage" />
		<ul>
			<logic:iterate id="item" name="element" property="elementSubList" type="java.lang.String">
    			<li><bean:write name="item" /></li>
			</logic:iterate>
		</ul>
		</logic:iterate>
	</span>
	</div>
</logic:equal>

<logic:greaterThan name="confirm" property="notBlockingElementsSize" value="0" >
	<div id="infozone" class="ok">
	<span>
		<logic:iterate id="element" name="confirm" property="notBlockingElements" type="org.talend.administrator.common.session.ConfirmDeletionElement">
		<bean:write name="element" property="elementMessage" />
		<ul>
			<logic:iterate id="item" name="element" property="elementSubList" type="java.lang.String">
    			<li><bean:write name="item" /></li>
			</logic:iterate>
		</ul>
		</logic:iterate>
	</span>
	</div>
</logic:greaterThan>


<talend:actionsBar>
	<%if ( confirmDeletion.isDeletable()){ %>
	<talend:link titleKey="common.action.confirm" path="<%=confirmDeletion.getUrlConfirm()%>" titleStyling="true"/>
	<talend:link titleKey="common.action.cancel" path="<%=confirmDeletion.getUrlCancel()%>" titleStyling="true"/>
	<%}else{ %>
	<talend:link titleKey="common.action.backToList" path="<%=confirmDeletion.getUrlCancel()%>" titleStyling="true"/>
	<%} %>
</talend:actionsBar>
