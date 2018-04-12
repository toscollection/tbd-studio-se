package com.talend.tuj.generator.io;

import com.talend.tuj.generator.conf.TUJGeneratorConfiguration;
import com.talend.tuj.generator.utils.*;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;

public class TUJExporter {
    private static TransformerFactory tFactory = TransformerFactory.newInstance();
    private static int failed;
    private static int exported;

    public static void exportTUJs(TUJGeneratorConfiguration conf, List<TUJ> tujs) {
        failed = 0;
        exported = 0;
        for (TUJ tuj : tujs) {
            writeTUJ(tuj, conf);
        }
        System.out.println("Exported successfully " + exported + " TUJs but failed with " + failed + " others");
    }

    private static void writeTUJ(TUJ tuj, TUJGeneratorConfiguration conf) {
        List<Job> jobs = tuj.getStarterJob().getChildJobs();
        jobs.add(tuj.getStarterJob());

        for (Job job : jobs) {
            if(job.getType().equals(JobType.NONE)) {
                //System.err.println("Failed to export TUJ : " + tuj.getName());
                failed++;
                return;
            }
        }

        Path tujRoot = FileSystems.getDefault().getPath(conf.get("output"), tuj.getName());
        tujRoot.toFile().mkdirs();

        // write resources here
        try {
            for (Path resource : tuj.getResources()) {
                Files.copy(resource, tujRoot.resolve(resource.getFileName()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Path projectRoot = tujRoot;

        if(tuj.getProjectName().isPresent()){
            projectRoot = tujRoot.resolve(tuj.getProjectName().get());
            projectRoot.toFile().mkdirs();
        }

        try {
            Transformer transformer = tFactory.newTransformer();

            File projectFile = projectRoot.resolve("talend.project").toFile();
            projectFile.createNewFile();
            transformer.transform(new DOMSource(tuj.getProject()), new StreamResult(new FileWriter(projectFile)));

            for (Job job : jobs) {
                String processFolder = "process";

                switch (job.getType()) {
                    case BIG_DATA_BATCH:
                        processFolder += "_mr";
                        break;
                    case BIG_DATA_STREAMING:
                        processFolder += "_storm";
                        break;
                }

                Path jobFolder = projectRoot.resolve(processFolder).resolve(job.getFsPath().orElse(Paths.get("")));
                jobFolder.toFile().mkdirs();

                File itemFile = jobFolder.resolve(job.getName() + "_" + job.getVersion() + ".item").toFile();
                itemFile.createNewFile();
                transformer.transform(new DOMSource(job.getItem()), new StreamResult(new FileWriter(itemFile)));

                File propertiesFile = jobFolder.resolve(job.getName() + "_" + job.getVersion() + ".properties").toFile();
                propertiesFile.createNewFile();
                transformer.transform(new DOMSource(job.getProperties()), new StreamResult(new FileWriter(propertiesFile)));

                File screenshotFile = jobFolder.resolve(job.getName() + "_" + job.getVersion() + ".screenshot").toFile();
                screenshotFile.createNewFile();
                transformer.transform(new DOMSource(job.getScreenshot()), new StreamResult(new FileWriter(screenshotFile)));
            }

            for (Context context : tuj.getContexts()) {
                String contextFolderName = "context";

                Path contextFolder = projectRoot.resolve(contextFolderName).resolve(context.getFsPath().orElse(Paths.get("")));
                contextFolder.toFile().mkdirs();

                File itemFile = contextFolder.resolve(context.getName() + "_" + context.getVersion() + ".item").toFile();
                itemFile.createNewFile();
                transformer.transform(new DOMSource(context.getItem()), new StreamResult(new FileWriter(itemFile)));

                File propertiesFile = contextFolder.resolve(context.getName() + "_" + context.getVersion() + ".properties").toFile();
                propertiesFile.createNewFile();
                transformer.transform(new DOMSource(context.getProperties()), new StreamResult(new FileWriter(propertiesFile)));
            }
            exported++;
        } catch (IOException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
