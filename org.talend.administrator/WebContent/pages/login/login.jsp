<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<html:form action="/login/actionLogin.do">
	<html:hidden property="method" value="submit"/>
	<html:xhtml />
	<div align="center">
	<table>
		<tr>
			<td colspan="3"><html:errors property="login.error.authentification" /></td>
		</tr>
		<tr>
			<td><bean:message key="login.user" />:</td>
			<td><html:text property="login" /></td>
			<td><html:errors property="login" /></td>
		</tr>
		<tr>
			<td><bean:message key="login.password" />:</td>
			<td><html:password property="password" /></td>
			<td><html:errors property="password" /></td>
		</tr>
		<tr>
			<td colspan="3">&nbsp;</td>
		</tr>
		<tr>
			<td align="center" colspan="3">
				<input type="hidden" name="method" value="execute" /> 
				<html:submit alt="common.button.validate.desc">
					<bean:message key="common.button.validate" />
				</html:submit>
			</td>
		</tr>
	</table>
	</div>
</html:form>
