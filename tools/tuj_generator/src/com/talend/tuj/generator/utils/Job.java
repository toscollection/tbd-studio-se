package com.talend.tuj.generator.utils;

import com.talend.tuj.generator.elements.JobElement;
import com.talend.tuj.generator.utils.jobdatafinder.*;
import org.w3c.dom.*;

import java.nio.file.Path;
import java.util.*;

public class Job extends TalendDocument {
    private List<Job> childJobs;
    private JobType jobType = JobType.NONE;
    private JobFramework framework = JobFramework.NONE;
    private Document screenshot;
    private Path originalPath;
    private Optional<String> localContextID = Optional.empty();


    private Optional<TUJ> tuj = Optional.empty();

    public Job(Document properties, Document item, Document screenshot, Optional<Path> jobPath, Path originalAbsPath) {
        super(properties, item, jobPath);
        this.screenshot = screenshot;
        this.childJobs = new ArrayList<>();
        this.originalPath = originalAbsPath;
        writeJobTypeAndFramework();
    }

    public Optional<TUJ> getTuj() {
        return tuj;
    }

    public void setTuj(TUJ tuj) {
        this.tuj = Optional.of(tuj);
    }

    public void addChildJob(Job job) {
        this.childJobs.add(job);
    }

    public Document getScreenshot() {
        return screenshot;
    }

    public List<Job> getChildJobs() {
        List<Job> jobs = new ArrayList<>();
        if(childJobs.size() > 0) {
            jobs.addAll(childJobs);
            childJobs.forEach(
                    child -> jobs.addAll(child.getChildJobs())
            );
        }
        return jobs;
    }

    public Path getOriginalPath() {
        return originalPath;
    }

    public JobType getType() {
        return jobType;
    }

    public JobFramework getFramework() {
        return framework;
    }

    public List<String> findChildjobs() {
        List<String> childJobs = new ArrayList<>();

        NodeList components = this.item.getElementsByTagName("node");
        for (int nodeIndex = 0; nodeIndex < components.getLength(); nodeIndex++) {
            Node component = components.item(nodeIndex);
            try {
                if (component.getAttributes().getNamedItem("componentName").getNodeValue().equals("tRunJob")) {
                    NodeList componentParameters = component.getChildNodes();
                    for (int parameterIndex = 0; parameterIndex < componentParameters.getLength(); parameterIndex++) {
                        Node componentParameter = componentParameters.item(parameterIndex);
                        try {
                            if (componentParameter.getAttributes().getNamedItem("name").getNodeValue().equals("PROCESS:PROCESS_TYPE_PROCESS")) {
                                childJobs.add(componentParameter.getAttributes().getNamedItem("value").getNodeValue());
                            }
                        } catch (NullPointerException ignored) {
                        }
                    }
                }
            } catch (NullPointerException ignored) {
            }
        }
        return childJobs;
    }

    private void writeJobTypeAndFramework() {
        List<JobDataFinderStrategy> strategies = new ArrayList<>();
        strategies.add(new JobDataFinder70Strategy());
        strategies.add(new JobDataFinderOldStrategy());
        strategies.add(new JobDataFinderPropertyStrategy());
        strategies.add(new JobDataFinderPathStrategy());
        strategies.add(new JobDataFinderImplicitStrategy());

        for(JobDataFinderStrategy strategy : strategies){
            if(jobType.equals(JobType.NONE)) jobType = strategy.findJobType(this);
            if(framework.equals(JobFramework.NONE)) framework = strategy.findJobFramework(this);
            if(!(jobType.equals(JobType.NONE) || framework.equals(JobFramework.NONE))) break;
        }
    }

    public void addContextParameters(Map<String, String> parameters){
        NodeList contexts = this.item.getElementsByTagName("context");
        if(!this.localContextID.isPresent()) this.localContextID = Optional.of(JobID.generateJobID());

        for(int i=0 ; i<contexts.getLength() ; i++){
            Node context = contexts.item(i);
            NodeList contextParameters = context.getChildNodes();

            Set<String> keysToModify = parameters.keySet();

            for(int c=0 ; c<contextParameters.getLength() ; c++){
                Node raw_parameter = contextParameters.item(c);
                if(raw_parameter.getNodeName().equals("contextParameter")){
                    Element parameter = (Element) raw_parameter;

                    if(parameter.hasAttribute("name") && keysToModify.remove(parameter.getAttribute("name"))){
                        /*
                        String key = parameter.getAttribute("name");

                        parameter.setAttribute("value", parameters.get(key));
                        if(parameter.hasAttribute("repositoryContextId")){
                            parameter.removeAttribute("repositoryContextId");
                        }
                        */
                    }
                }
            }

            for (String key : keysToModify){
                Document root = context.getOwnerDocument();
                Element newParameter = root.createElement("contextParameter");
                newParameter.setAttribute("comment", "");
                newParameter.setAttribute("name", key);
                newParameter.setAttribute("prompt", key+"?");
                newParameter.setAttribute("promptNeeded", "false");
                newParameter.setAttribute("type", "id_String");
                newParameter.setAttribute("value", parameters.get(key));
                //newParameter.setAttribute("repositoryContextId", localContextID.get());
                newParameter.setAttribute("xmi:id", JobID.generateJobID());

                context.appendChild(newParameter);
            }
        }
    }

    public List<JobElement> getComponents(){
        List<JobElement> components = new ArrayList<>();

        NodeList nodes = this.item.getElementsByTagName("node");
        for (int n = 0 ; n < nodes.getLength() ; n++){
            components.add(new JobElement(nodes.item(n), this));
        }

        return components;
    }

    public List<JobElement> getSubJobs(){
        List<JobElement> components = new ArrayList<>();

        NodeList nodes = this.item.getElementsByTagName("subjob");
        for (int n = 0 ; n < nodes.getLength() ; n++){
            components.add(new JobElement(nodes.item(n), this));
        }

        return components;
    }

    public JobElement getJobConfiguration(){
        NodeList nodes = this.item.getElementsByTagName("parameters");
        return new JobElement(nodes.item(0), this);
    }
}
