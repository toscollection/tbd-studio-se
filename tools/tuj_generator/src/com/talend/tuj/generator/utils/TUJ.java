package com.talend.tuj.generator.utils;

import org.w3c.dom.Document;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class TUJ {
    private Document project;
    private Job starter;
    private String name;
    private Optional<String> projectName;
    private List<Path> resources;
    private List<Context> contexts;

    public TUJ(Job job, Document project, List<Path> resources, List<Context> contexts, String name, Optional<String> projectName) {
        this.starter = job;
        this.project = project;
        this.resources = resources;
        this.contexts = contexts;
        this.name = name;
        this.projectName = projectName;
        propagateTUJSetting();
    }

    private void propagateTUJSetting(){
        starter.setTuj(this);
        propagateTUJSettingToList(starter.getChildJobs());
    }

    private void propagateTUJSettingToList(List<Job> jobs){
        if(jobs.size() > 0){
            jobs.forEach(
                    job -> {
                        job.setTuj(this);
                        propagateTUJSettingToList(job.getChildJobs());
                    }
            );
        }
    }

    public Job getStarterJob() {
        return starter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<String> getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = Optional.of(projectName);
    }

    public List<Path> getResources() {
        return resources;
    }

    public Document getProject() {
        return project;
    }

    public List<Context> getContexts() {
        return contexts;
    }
}
