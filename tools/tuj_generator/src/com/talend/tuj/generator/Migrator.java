package com.talend.tuj.generator;

import com.talend.tuj.generator.conf.TUJGeneratorConfiguration;
import com.talend.tuj.generator.elements.ElementFactory;
import com.talend.tuj.generator.elements.IElement;
import com.talend.tuj.generator.exception.UnknownDistributionException;
import com.talend.tuj.generator.processors.*;
import com.talend.tuj.generator.utils.Context;
import com.talend.tuj.generator.utils.Job;
import com.talend.tuj.generator.utils.SubstitutionCmdHandler;
import com.talend.tuj.generator.utils.TUJ;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Migrator {
    private List<IProcessor> processors = new ArrayList<>();
    private static ElementFactory elmtFactory = ElementFactory.getInstance();
    private TUJGeneratorConfiguration conf;

    public Migrator(TUJGeneratorConfiguration conf) {
        this.conf = conf;

        processors.add(new ContextIDProcessor());

        if (conf.containsKey("contextSubstitution")) {
            processors.add(new ContextValueSubstitutionProcessor(SubstitutionCmdHandler.processArgument(conf.get("contextSubstitution"))));
        }

        processors.add(new JobIDProcessor());
        if (conf.containsKey("fileSubstitution")) {
            processors.add(new FileNameSubstitutionProcessor(SubstitutionCmdHandler.processArgument(conf.get("fileSubstitution"))));
        }

        try {
            switch (conf.getDistributionName()) {
                case SPARK_LOCAL:
                    processors.add(new SparkConfigurationLocalSparkProcessor(conf.get("distributionVersion")));
                    break;
                case CDH:
                case MAPR:
                case HDP:
                case HDI:
                case DATAPROC:
                    processors.add(new GenericDistributionComponentConfigurationProcessor(conf.getDistributionName().getXmlDistributionName(), conf.get("distributionVersion")));
                    processors.add(new GenericDistributionConfigurationProcessor(conf.getDistributionName().getXmlDistributionName(), conf.get("distributionVersion")));
                    if(Boolean.parseBoolean(conf.get("sparkStandaloneMigration"))) processors.add(new SparkStandaloneToYARNProcessor());
                    //processors.add(new CheckContextProcessor());
                    break;
            }
        } catch (UnknownDistributionException e) {
            e.printStackTrace();
        }
    }

    public TUJ migrate(TUJ tuj) {
        //System.out.println("Processing TUJ : " + tuj.getName());
        navigateJob(tuj.getStarterJob());
        tuj.getContexts().forEach(this::navigateContext);
        return tuj;
    }

    public List<TUJ> migrate(List<TUJ> tujs) {
        return tujs.stream().map(this::migrate).collect(Collectors.toList());
    }

    private void navigateJob(Job job) {
        iterateJobNodes(job.getProperties().getChildNodes(), job);
        iterateJobNodes(job.getItem().getChildNodes(), job);

        job.getChildJobs().forEach(this::navigateJob);
    }

    private void navigateContext(Context context) {
        iterateContextNodes(context.getProperties().getChildNodes(), context);
        iterateContextNodes(context.getItem().getChildNodes(), context);
    }

    private void iterateJobNodes(NodeList nodes, Job job) {
        for (int nodeIndex = 0; nodeIndex < nodes.getLength(); nodeIndex++) {
            Node node = nodes.item(nodeIndex);

            IElement component = elmtFactory.createJobElement(node, job);

            processors.forEach(
                    processor -> {
                        if (processor.shouldBeProcessed(component)) processor.process(component);
                    }
            );

            if (node.hasChildNodes()) iterateJobNodes(node.getChildNodes(), job);
        }
    }

    private void iterateContextNodes(NodeList nodes, Context context) {
        for (int nodeIndex = 0; nodeIndex < nodes.getLength(); nodeIndex++) {
            Node node = nodes.item(nodeIndex);

            IElement component = elmtFactory.createContextElement(node, context);

            processors.forEach(
                    processor -> {
                        if (processor.shouldBeProcessed(component)) processor.process(component);
                    }
            );

            if (node.hasChildNodes()) iterateContextNodes(node.getChildNodes(), context);
        }
    }

}
