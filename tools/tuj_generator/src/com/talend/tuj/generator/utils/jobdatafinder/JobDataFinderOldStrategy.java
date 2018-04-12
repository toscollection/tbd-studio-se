package com.talend.tuj.generator.utils.jobdatafinder;

import com.talend.tuj.generator.utils.Job;
import com.talend.tuj.generator.utils.JobFramework;
import com.talend.tuj.generator.utils.JobType;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class JobDataFinderOldStrategy implements JobDataFinderStrategy {
    @Override
    public JobFramework findJobFramework(Job job) {
        try {
            NodeList childs = job.getProperties().getElementsByTagName("TalendProperties:Property").item(0).getChildNodes();
            for(int i = 0 ; i < childs.getLength() ; i++){
                Node node = childs.item(i);
                if(node.getNodeName().equals("additionalProperties") && node.getAttributes().getNamedItem("key").getNodeValue().equals("FRAMEWORK")){
                    return JobFramework.valueOf(node.getAttributes().getNamedItem("value").getNodeValue().toUpperCase().replaceAll("\\s", "_"));
                }
            }
            return JobFramework.NONE;
        } catch (NullPointerException e1) {
            return JobFramework.NONE;
        }
    }

    @Override
    public JobType findJobType(Job job) {
        return JobType.NONE;
    }
}
