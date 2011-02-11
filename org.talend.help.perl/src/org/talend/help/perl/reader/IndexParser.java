// ============================================================================
//
// Copyright (C) 2006-2011 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.help.perl.reader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.talend.help.perl.Activator;
import org.talend.help.perl.model.EProperty;
import org.talend.help.perl.model.EType;
import org.talend.help.perl.model.Node;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * IndexParser.java.
 * 
 */
public class IndexParser extends DefaultHandler {

    private static final String PATH = "guide/perl/perl_func_list.xml"; //$NON-NLS-1$

    public static final String FILE = "/home/wiu/work/talend/workspace-talend-svn/org.talend.help/" + PATH; //$NON-NLS-1$

    private Node root;

    private Stack<Node> current = new Stack<Node>();

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        // TODO Auto-generated method stub
        super.characters(ch, start, length);
        String value = new String(ch, start, length).trim();
        if (value.length() != 0) {
            // System.out.println(current.peek()+"\t"+value);
            current.peek().getProperties().put(EProperty.VALUE, value);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        // TODO Auto-generated method stub
        super.endDocument();
        current = null;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        // TODO Auto-generated method stub
        super.endElement(uri, localName, qName);
        current.pop();
    }

    @Override
    public void startDocument() throws SAXException {
        // TODO Auto-generated method stub
        super.startDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // TODO Auto-generated method stub
        super.startElement(uri, localName, qName, attributes);
        EType type = EType.find(qName);
        if (type != null) {
            if (root == null) {
                root = new Node(type, null);
                current.push(root);
            } else {
                Node node = new Node(type, current.peek());
                current.push(node);
                String value = attributes.getValue("name"); //$NON-NLS-1$
                if (value != null) {
                    node.getProperties().put(EProperty.LABEL, value);
                }
            }
        }
    }

    public Node getRoot() {
        return root;
    }

    public static Node parse() throws IOException, ParserConfigurationException, SAXException {
        InputStream stream = FileLocator.openStream(Activator.getDefault().getBundle(), new Path(PATH), false);
        try {
            return parse(stream);
        } finally {
            stream.close();
        }
    }

    public static Node parse(InputStream in) throws ParserConfigurationException, SAXException, IOException {
        IndexParser saxHandler = new IndexParser();

        SAXParserFactory newInstance = SAXParserFactory.newInstance();
        SAXParser parser = newInstance.newSAXParser();
        parser.parse(in, saxHandler);
        return saxHandler.getRoot();

    }

    public static void main(String[] args) {
        try {
            IndexParser saxHandler = new IndexParser();

            SAXParserFactory newInstance = SAXParserFactory.newInstance();
            SAXParser parser = newInstance.newSAXParser();
            File file = new File(FILE);
            parser.parse(file, saxHandler);
            System.out.println();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
