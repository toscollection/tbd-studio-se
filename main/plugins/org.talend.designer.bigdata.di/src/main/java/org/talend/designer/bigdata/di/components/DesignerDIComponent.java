package org.talend.designer.bigdata.di.components;

import org.jetbrains.annotations.NotNull;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.core.model.process.INode;
import org.talend.core.model.process.IProcess;
import org.talend.designer.codegen.config.CodeGeneratorArgument;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DesignerDIComponent {

    String name();

    interface BigDataDIComponent extends DesignerDIComponent {
        CodeGeneratorArgument codeGeneratorArgument();
        @NotNull
        static <T> T getParameter(final INode node, final String code, @NotNull T defaultValue) throws ClassCastException {
            return Optional.ofNullable(ElementParameterParser.getObjectValue(node, code))
                    .map(parameter -> (T) parameter)
                    .orElse(defaultValue);
        }
        @NotNull
        default <T> T getParameter(final String code, @NotNull T defaultValue) throws ClassCastException {
            return getParameter(((INode) codeGeneratorArgument().getArgument()), code, defaultValue);
        }
        @NotNull
        static List<Map<String, String>> tableParameter(final INode node, final String code, @NotNull
        List<Map<String, String>> defaultValue) throws ClassCastException {
            return Optional.ofNullable(ElementParameterParser.getTableValue(node, code)).orElse(defaultValue);
        }
        static String getParameter(final IProcess process, final String uniqueName, final String code) throws ClassCastException {
            return Optional.ofNullable(ElementParameterParser.getValue(process, uniqueName, code))
                    .orElse("");
        }
        static INode getLinkedParameter(final INode node, final String code, final INode defaultValue){
            return Optional.ofNullable(ElementParameterParser.getLinkedNodeValue(node, code)).orElse(defaultValue);
        }
        @Override
        default String name() {
            return ((INode) codeGeneratorArgument().getArgument()).getUniqueName() + "Component";
        }
        default String getCid() {
            return ((INode) codeGeneratorArgument().getArgument()).getUniqueName();
        }
        default String getComponentVariable() {
            return ((INode) codeGeneratorArgument().getArgument()).getComponent().getName();
        }
        default boolean isLog4jEnabled(){
            INode iNode = ((INode) codeGeneratorArgument().getArgument());
            return getParameter(iNode.getProcess(), iNode.getUniqueName(), "__LOG4J_ACTIVATE__").equals("true");
        }
    }

    interface WithDieOnErrorOption extends BigDataDIComponent {
        default boolean dieOnError() {
            return this.getParameter("__DIE_ON_ERROR__", false);
        }
    }
}
