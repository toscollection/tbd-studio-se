// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006 Talend - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================
package org.talend.administrator;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.MessageResourcesConfig;
import org.apache.struts.config.ModuleConfig;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.talend.administrator.common.util.messages.TalendMessageResources;
import org.talend.administrator.common.util.messages.TalendMessageResourcesFactory;

/**
 * DOC mhirt class global comment. Detailled comment <br/> $Id$
 */
public class TalendActionServlet extends ActionServlet {

    private static final long serialVersionUID = 4157304330528804660L;
    private static final String SERVLET_ERROR_ACTION = "servlet-error-action";
    private static final String SERVLET_ERROR_PAGE = "servlet-error-page";
    private static final String MESSAGE_RESOURCE_FACTORY = "message-resource-factory";

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.struts.action.ActionServlet#initModuleMessageResources(org.apache.struts.config.ModuleConfig)
     */
    protected void initModuleMessageResources(ModuleConfig config) throws ServletException {
        MessageResourcesConfig[] mrcs = config.findMessageResourcesConfigs();
        for (int i = 0; i < mrcs.length; i++) {
            if ((mrcs[i].getFactory() == null) || (mrcs[i].getParameter() == null)) {
                continue;
            }
            String messageRessource = this.getInitParameter(MESSAGE_RESOURCE_FACTORY);
            if (messageRessource == null) {
                messageRessource = "org.talend.administrator.common.util.messages.TalendMessageResourcesFactory";
            }

            TalendMessageResourcesFactory.setFactoryClass(messageRessource);
            TalendMessageResourcesFactory factoryObject = (TalendMessageResourcesFactory) TalendMessageResourcesFactory
                    .createFactory();

            TalendMessageResources resources = factoryObject.createResources(mrcs[i].getParameter());

            resources.setReturnNull(mrcs[i].getNull());
            getServletContext().setAttribute(mrcs[i].getKey() + config.getPrefix(), resources);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void init() throws ServletException {
        super.init();
        // used for init purpose only

        // Create a resource set.
        ResourceSet resourceSet = new ResourceSetImpl();

        // Register the default resource factory -- only needed for stand-alone!
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
                .put("ecore", new EcoreResourceFactoryImpl());

        // Register the package -- only needed for stand-alone!
        EcorePackage eclass = EcorePackage.eINSTANCE;
        eclass.getClass();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.struts.action.ActionServlet#process(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void process(HttpServletRequest request, HttpServletResponse response) throws IOException,
            ServletException {
        try {
            super.process(request, response);
        } catch (ServletException exc) {
            request.setAttribute(Globals.EXCEPTION_KEY, exc);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(
                    this.getInitParameter(SERVLET_ERROR_ACTION));
            try {
                dispatcher.forward(request, response);
            } catch (Exception exc2) {
                try {
                    dispatcher = getServletContext().getRequestDispatcher(this.getInitParameter(SERVLET_ERROR_PAGE));
                    dispatcher.include(request, response);
                } catch (Exception exc3) {
                    // Do nothing
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
