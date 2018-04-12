package com.talend.tuj.generator.io;

import com.talend.tuj.generator.conf.TUJGeneratorConfiguration;
import com.talend.tuj.generator.exception.NotWellMadeTUJException;
import com.talend.tuj.generator.utils.Context;
import com.talend.tuj.generator.utils.Job;
import com.talend.tuj.generator.utils.TUJ;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Pattern;

public class TUJImporter {
    private static final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    private Optional<Pattern> pattern = Optional.empty();


    public List<TUJ> importTUJ(TUJGeneratorConfiguration conf) {
        Path root = FileSystems.getDefault().getPath(conf.get("input"));

        if (conf.containsKey("tuj")) {
            pattern = Optional.of(Pattern.compile(conf.get("tuj")));
        }
        return findTUJs(root);
    }

    private Job generateJobHierarchy(List<Job> jobList) throws NotWellMadeTUJException {
        Map<String, Job> jobMap = new HashMap<>();

        for (Job job : jobList) {
            jobMap.put(job.getId(), job);
        }

        return generateJobHierarchy(jobMap);
    }

    private Job generateJobHierarchy(Map<String, Job> jobMap) throws NotWellMadeTUJException {
        Map<String, List<String>> jobHierarchy = new HashMap<>();

        for (Job job : jobMap.values()) {
            List<String> childs = job.findChildjobs();
            jobHierarchy.put(job.getId(), childs);
            for (String child : childs) {
                job.addChildJob(jobMap.get(child));
            }
        }

        Set<String> jobIds = jobMap.keySet();
        for (List<String> childs : jobHierarchy.values()) {
            jobIds.removeAll(childs);
        }

        if (jobIds.size() == 1) {
            return jobMap.get(jobIds.iterator().next());
        }

        throw new NotWellMadeTUJException("TUJ contains " + jobIds.size() + " entry points");
    }

    private List<TUJ> findTUJs(Path root) {
        List<TUJ> tujs = new ArrayList<>();

        try (DirectoryStream<Path> rootFolder = Files.newDirectoryStream(root)) {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            for (Path tujRoot : rootFolder) {
                if (Files.isDirectory(tujRoot) &&
                        (!pattern.isPresent() ||
                                (pattern.isPresent() && pattern.get().matcher(tujRoot.getFileName().toString()).find())
                        )) {
                    Optional<String> projectName = findProjectName(tujRoot);
                    Path projectRoot = tujRoot;

                    List<Job> jobs = new ArrayList<>();
                    List<Context> contexts = new ArrayList<>();

                    if(projectName.isPresent()){
                        projectRoot = tujRoot.resolve(projectName.get());
                    }

                    try{
                    if (Files.isDirectory(projectRoot.resolve("process"))) {
                        jobs.addAll(findJobsInFolder(projectRoot.resolve("process")));
                    } else {
                        //System.err.println("No DI folder");
                    }

                    if (Files.isDirectory(projectRoot.resolve("process_mr"))) {
                        jobs.addAll(findJobsInFolder(projectRoot.resolve("process_mr")));
                    } else {
                        //System.err.println("No Batch folder");
                    }

                    if (Files.isDirectory(projectRoot.resolve("process_storm"))) {
                        jobs.addAll(findJobsInFolder(projectRoot.resolve("process_storm")));
                    } else {
                        //System.err.println("No Streaming folder");
                    }
                    } catch (NotWellMadeTUJException e){
                        System.err.println(tujRoot.getFileName().toString() + " has issue : " + e.getMessage());
                        continue;
                    }


                    if (Files.isDirectory(projectRoot.resolve("context"))) {
                        contexts.addAll(findContextsInFolder(projectRoot.resolve("context")));
                    } else {
                        //System.err.println("No Streaming folder");
                    }

                    try {
                        tujs.add(new TUJ(
                                generateJobHierarchy(jobs),
                                dBuilder.parse(projectRoot.resolve("talend.project").toString()),
                                findResourcesInFolder(tujRoot),
                                contexts,
                                tujRoot.getFileName().toString(),
                                projectName
                        ));
                    } catch (NotWellMadeTUJException e) {
                        System.err.println("Ignoring TUJ : " + tujRoot.getFileName().toString() + " because : " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        System.out.println("Imported " + tujs.size() + " TUJs");

        return tujs;
    }

    private List<Context> findContextsInFolder(Path root) {
        return findContextsInFolder(root, Optional.empty());
    }

    private List<Context> findContextsInFolder(Path root, Optional<Path> folderStructure) {
        ArrayList<Context> contexts = new ArrayList<>();
        Set<String> contextNames = new HashSet<>();

        try (DirectoryStream<Path> rootFolder = Files.newDirectoryStream(root)) {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            for (Path folder : rootFolder) {
                if (Files.isDirectory(folder)) {
                    if (folderStructure.isPresent())
                        contexts.addAll(findContextsInFolder(folder, Optional.of(folderStructure.get().resolve(folder.getFileName().toString()))));
                    else contexts.addAll(findContextsInFolder(folder, Optional.of(folder.getFileName())));
                } else {
                    String jobName = folder.getFileName().toString();
                    contextNames.add(jobName.substring(0, jobName.lastIndexOf('.')));
                }
            }

            for (String jobName : contextNames) {
                contexts.add(new Context(
                        dBuilder.parse(root.resolve(jobName + ".properties").toString()),
                        dBuilder.parse(root.resolve(jobName + ".item").toString()),
                        folderStructure
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return contexts;
    }

    private List<Job> findJobsInFolder(Path root) throws NotWellMadeTUJException {
        return findJobsInFolder(root, Optional.empty());
    }

    private List<Job> findJobsInFolder(Path root, Optional<Path> folderStructure) throws NotWellMadeTUJException {
        ArrayList<Job> jobs = new ArrayList<>();
        Set<String> jobNames = new HashSet<>();

        try (DirectoryStream<Path> rootFolder = Files.newDirectoryStream(root)) {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            for (Path folder : rootFolder) {
                if (Files.isDirectory(folder)) {
                    if (folderStructure.isPresent())
                        jobs.addAll(findJobsInFolder(folder, Optional.of(folderStructure.get().resolve(folder.getFileName().toString()))));
                    else jobs.addAll(findJobsInFolder(folder, Optional.of(folder.getFileName())));
                } else {
                    String jobName = folder.getFileName().toString();
                    jobNames.add(jobName.substring(0, jobName.lastIndexOf('.')));
                }
            }

            for (String jobName : jobNames) {
                jobs.add(new Job(
                        dBuilder.parse(root.resolve(jobName + ".properties").toString()),
                        dBuilder.parse(root.resolve(jobName + ".item").toString()),
                        dBuilder.parse(root.resolve(jobName + ".screenshot").toString()),
                        folderStructure,
                        root.toAbsolutePath()
                ));
            }
        } catch (Exception e){
            throw new NotWellMadeTUJException("Malformed TUJ because of : " + e.getMessage());
        }

        return jobs;
    }

    private List<Path> findResourcesInFolder(Path root) {
        List<Path> resources = new ArrayList<>();

        try (DirectoryStream<Path> rootFolder = Files.newDirectoryStream(root)) {
            for (Path resourceFile : rootFolder) {
                if (!Files.isDirectory(resourceFile)) resources.add(resourceFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resources;
    }

    private Optional<String> findProjectName(Path root) {
        return findProjectName(root, root);
    }

    private Optional<String> findProjectName(Path root, Path processRoot){
        try(DirectoryStream<Path> candidateProjectFolder = Files.newDirectoryStream(processRoot)){
            for (Path file : candidateProjectFolder) {
                if(file.getFileName().toString().equals("talend.project")){
                    if(processRoot.getFileName().equals(root.getFileName())) return Optional.empty();
                    return Optional.of(processRoot.getFileName().toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try(DirectoryStream<Path> candidateProjectFolder = Files.newDirectoryStream(processRoot)){
            for (Path file : candidateProjectFolder) {
                if(Files.isDirectory(file)){
                    Optional<String> result = findProjectName(root, file);
                    if(result.isPresent()){
                        return result;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
