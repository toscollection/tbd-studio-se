// ============================================================================
//
// Copyright (C) 2006-2013 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.hcatalog.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.security.KerberosAuthOutInterceptor;
import org.apache.cxf.transport.http.auth.HttpAuthHeader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.repository.ConnectionStatus;
import org.talend.repository.hcatalog.util.KerberosPolicyConfig;
import org.talend.repository.model.hcatalog.HCatalogConnection;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HCatalogServiceUtil {

    public final static String TEMPLETON_DB_ROOT = "templeton/v1/ddl/database/"; //$NON-NLS-1$

    /**
     * DOC ycbai Comment method "testConnection".
     * 
     * Test whether can connect to HCatalog.
     * 
     * @return
     */
    public static ConnectionStatus testConnection(HCatalogConnection connection) {
        ConnectionStatus connectionStatus = new ConnectionStatus();
        try {
            WebClient client = getHCatalogDBClient(connection);
            getDataFromHCatalog(client);
            connectionStatus.setResult(true);
            connectionStatus.setMessageException("Connection successful");
        } catch (Exception e) {
            ExceptionHandler.process(e);
            connectionStatus.setMessageException("Cannot connect to HCatalog --> " + e.getMessage());
        }

        return connectionStatus;
    }

    /**
     * DOC ycbai Comment method "getHCatalogDBClient".
     * 
     * @param connection
     * @return the HCatalog client of the special database from HCatalogConnection.
     */
    public static WebClient getHCatalogDBClient(HCatalogConnection connection) {
        String database = StringUtils.trimToEmpty(connection.getDatabase());
        WebClient client = getHCatalogClient(connection, database);
        addKerberos2Client(client, connection);
        return client;
    }

    private static void addKerberos2Client(WebClient client, HCatalogConnection connection) {
        if (connection.isEnableKerberos()) {
            KerberosAuthOutInterceptor kbInterceptor = new KerberosAuthOutInterceptor();
            AuthorizationPolicy policy = new AuthorizationPolicy();
            policy.setAuthorizationType(HttpAuthHeader.AUTH_TYPE_NEGOTIATE);
            kbInterceptor.setPolicy(policy);
            java.util.Map<String, String> properties = new HashMap<String, String>();
            kbInterceptor.setServicePrincipalName(StringUtils.trimToEmpty(connection.getKrbPrincipal()));
            kbInterceptor.setRealm(StringUtils.trimToEmpty(connection.getKrbRealm()));
            properties.put("useTicketCache", "true"); //$NON-NLS-1$
            properties.put("refreshKrb5Config", "true"); //$NON-NLS-1$
            properties.put("renewTGT", "true"); //$NON-NLS-1$
            kbInterceptor.setLoginConfig(new KerberosPolicyConfig(properties));
            WebClient.getConfig(client).getOutInterceptors().add(kbInterceptor);
        }
    }

    /**
     * DOC ycbai Comment method "getHCatalogClient".
     * 
     * @param connection
     * @param path the path must start with database name or "templeton/v1/ddl/database/".
     * @return the HCatalog client with the special path.
     */
    public static WebClient getHCatalogClient(HCatalogConnection connection, String path) {
        if (connection == null || path == null) {
            return null;
        }
        WebClient rootClient = getHCatalogRootClient(connection);
        if (!path.startsWith(TEMPLETON_DB_ROOT)) {
            path = TEMPLETON_DB_ROOT + path;
        }
        rootClient.path(path);
        rootClient.accept("application/json"); //$NON-NLS-1$

        return rootClient;
    }

    private static WebClient getHCatalogRootClient(HCatalogConnection connection) {
        String host = StringUtils.trimToEmpty(connection.getHostName());
        String port = StringUtils.trimToEmpty(connection.getPort());
        String userName = StringUtils.trimToEmpty(connection.getUserName());
        String path = "http://" + host + ":" + port + "?user.name=" + userName; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

        return WebClient.create(path);
    }

    /**
     * DOC ycbai Comment method "getHCatalogClient".
     * 
     * @param path
     * @return the HCatalog client with the full path.
     */
    public static WebClient getHCatalogClient(String path) {
        return WebClient.create(path);
    }

    /**
     * DOC ycbai Comment method "getDataFromHCatalog".
     * 
     * Get data from HCatalog client.
     * 
     * @param client
     * @return
     * @throws Exception
     */
    public static JSONObject getDataFromHCatalog(WebClient client) throws Exception {
        Response response = client.get();
        InputStream inputStream = (InputStream) response.getEntity();
        String input = IOUtils.toString(inputStream);
        JSONObject jsonObject = (JSONObject) JSONValue.parse(input);
        String errorMsgDetail = ""; //$NON-NLS-1$
        Object errorObj = jsonObject.get("error"); //$NON-NLS-1$
        if (errorObj != null) {
            errorMsgDetail = errorObj.toString();
        }
        int status = response.getStatus();
        if (status != 200) {
            switch (status) {
            case 400:
                throw new Exception("Error 400 (Bad request): The request was invalid.\n" + errorMsgDetail);
            case 401:
                throw new Exception("Error 401 (Unauthorized): Credentials were missing or incorrect.\n" + errorMsgDetail);
            case 404:
                throw new Exception(
                        "Error 404 (Not found): The URI requested is invalid or the resource requested does not exist.\n"
                                + errorMsgDetail);
            case 500:
                throw new Exception("Error 500 (Internal Server Error): We received an unexpected result.\n" + errorMsgDetail);
            case 503:
                throw new Exception("Error 503 (Busy, please retry): The server is busy.\n" + errorMsgDetail);
            default:
                throw new Exception(jsonObject.get("errorCode") + ":" + jsonObject.get("error") + jsonObject.get("errorDetail")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
            }
        }

        return jsonObject;
    }

    public static void main(String[] args) {
        // WebClient client =
        // WebClient.create("http://talend-hdp-all:50111/templeton/v1/ddl/database/talend?user.name=ycbai");
        WebClient client = WebClient.create("http://talend-hdp-all:50111?user.name=ycbai");
        String path = "templeton/v1/ddl/database/" + "talend" + "/table/tablename";
        client.path(path);
        client.accept("application/json");
        Response response = client.get();
        InputStream inputStream = (InputStream) response.getEntity();
        try {
            String input = IOUtils.toString(inputStream);
            JSONObject jsonObject = (JSONObject) JSONValue.parse(input);
            // JSONArray tables = (JSONArray) jsonObject.get("tables");
            JSONArray columns = (JSONArray) jsonObject.get("columns");
            Iterator iterator = columns.iterator();
            while (iterator.hasNext()) {
                Object next = iterator.next();
                System.out.println(next);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
