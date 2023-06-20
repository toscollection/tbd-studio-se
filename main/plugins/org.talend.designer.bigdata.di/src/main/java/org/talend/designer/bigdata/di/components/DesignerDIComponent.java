package org.talend.designer.bigdata.di.components;

import org.jetbrains.annotations.NotNull;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.core.model.process.INode;
import org.talend.designer.codegen.config.CodeGeneratorArgument;

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

        static boolean getBooleanParameter(final INode node, final String string){
            return Optional.ofNullable(ElementParameterParser.getBooleanValue(node, string))
                    .orElse(false);
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

        default boolean getIsLog4jEnabled(){
            return ("true").equals(ElementParameterParser.getValue(((INode)codeGeneratorArgument().getArgument()).getProcess(), "__LOG4J_ACTIVATE__"));
        }
    }

    interface WithDieOnErrorOption extends BigDataDIComponent {
        default boolean getDieOnError() {
            return this.getParameter("__DIE_ON_ERROR__", false);
        }
    }
}
