package org.talend.designer.bigdata.di.components;

import org.immutables.value.Value;
import org.jetbrains.annotations.NotNull;
import org.talend.core.model.process.EConnectionType;
import org.talend.core.model.process.ElementParameterParser;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.INode;
import org.talend.core.model.process.node.IExternalMapTable;
import org.talend.designer.codegen.config.CodeGeneratorArgument;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public interface DesignerDIComponent {

    BigDataDIComponent.JavaVariable UNDEFINED_JAVA_VARIABLE = ImmutableDesignerDIComponent.JavaVariable.of("Void", "unknownVariable", "unknownVariable");

    String name();
    @Value.Immutable
    interface JavaVariable {
        @Value.Parameter
        String type();

        @Value.Parameter
        String name();

        @Value.Parameter
        String alias();
    }

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
        static boolean getBooleanParameter(final INode node, final String string) throws ClassCastException{
            return Optional.ofNullable(ElementParameterParser.getBooleanValue(node, string))
                    .orElse(false);
        }
        @NotNull
        static List<Map<String, String>> tableParameter(final INode node, final String code, @NotNull
        List<Map<String, String>> defaultValue) throws ClassCastException {
            return Optional.ofNullable(ElementParameterParser.getTableValue(node, code)).orElse(defaultValue);
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
            return ("true").equals(ElementParameterParser.getValue(((INode)codeGeneratorArgument().getArgument()).getProcess(), "__LOG4J_ACTIVATE__"));
        }
    }

    interface WithDieOnErrorOption extends BigDataDIComponent {
        default boolean dieOnError() {
            return this.getParameter("__DIE_ON_ERROR__", false);
        }
    }

    interface WithOneInput extends BigDataDIComponent {
     //   default JavaVariable inputVariable() {
//            INode node = (INode) codeGeneratorArgument().getArgument();
//            List<String> inputEntries = Optional.ofNullable(node.getExternalData())
//                    .map(externalData ->
//                            externalData.getInputTables().stream()
//                                    .map(IExternalMapTable::getName)
//                                    .collect(Collectors.toList()))
//                    .orElse(new ArrayList<>());
//            return node.getIncomingConnections(EConnectionType.FLOW_MAIN).stream()
//                    .findFirst()
//                    .map(connection -> {
//                        INode source = connection.getSource();
//                        String alias = inputEntries.stream().
//                                filter(input -> input.equals(connection.getName()))
//                                .findFirst()
//                                .orElse(Optional.ofNullable(source).map(INode::getIncomingConnections)
//                                        .orElse(new ArrayList<>())
//                                        .stream()
//                                        .map(IConnection::getName)
//                                        .findFirst()
//                                        .orElse(connection.getName()));
//                        return ImmutableDesignerDIComponent.JavaVariable.of(
//                                //codeGeneratorArgument().getRecordStructName(connection, nodeUseDataset(source)),
//                                //TODO: change this null
//                                null,
//                                connection.getName(),
//                                alias
//                        );
//                    })
//                    .orElse(ImmutableDesignerDIComponent.JavaVariable.of("Object", "unknown", "unknown"));
//        }

//        default List<IMetadataColumn> getListOutputVariable(){
////            INode node = (INode) codeGeneratorArgument().getArgument();
////            return node.getMetadataList().stream()
////                    .findFirst()
////                    .orElseThrow(UnsupportedOperationException::new)
////                    .getListColumns();
    //    }

    }
}
