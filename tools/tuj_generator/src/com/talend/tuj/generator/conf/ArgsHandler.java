package com.talend.tuj.generator.conf;

import org.apache.commons.cli.*;

public class ArgsHandler {
    public static TUJGeneratorConfiguration handle(String[] args) {

        Options options = new Options();

        Option input = new Option("i", "input", true, "input TUJ folder");
        input.setRequired(true);
        options.addOption(input);

        Option output = new Option("o", "output", true, "output folder for generated TUJ");
        output.setRequired(true);
        options.addOption(output);

        // TODO Not supported yet
        Option contextSubstitution = new Option("C", "context-substitution", true, "substitutions for context values. For example : aaa=>bbb;ccc=>ddd will replace aaa by bbb and ccc by ddd in contexts");
        contextSubstitution.setRequired(false);
        options.addOption(contextSubstitution);

        Option fileSubstitution = new Option("P", "path-substitution", true, "substitutions for file and folder names. For example : aaa=>bbb;ccc=>ddd will replace aaa by bbb and ccc by ddd in names");
        fileSubstitution.setRequired(false);
        options.addOption(fileSubstitution);

        Option distributionName = new Option("N", "distribution-name", true, "name of the distribution to generate the TUJs. See mapping.md for supported distribution names");
        distributionName.setRequired(true);
        options.addOption(distributionName);

        Option sparkVersion = new Option("S", "spark-version", true, "spark version of the distribution to generate the TUJs. See mapping.md for spark version format");
        sparkVersion.setRequired(false);
        options.addOption(sparkVersion);

        Option distributionVersion = new Option("V", "distribution-version", true, "version of the distribution to generate the TUJs. See mapping.md for supported distribution version format (not checked)");
        distributionVersion.setRequired(true);
        options.addOption(distributionVersion);

        // TODO support that mode
        Option tuj = new Option("t", "tuj-filter", true, "will filter the TUJs by name matching the given string (can be regex)");
        tuj.setRequired(false);
        options.addOption(tuj);

        Option standalone = new Option("s", "spark-yarn", false, "will migrate Spark standalone to YARN Client");
        standalone.setRequired(false);
        options.addOption(standalone);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
            return null;
        }

        TUJGeneratorConfiguration conf = new TUJGeneratorConfiguration();
        conf.put("sparkStandaloneMigration", Boolean.toString(cmd.hasOption('s')));
        conf.put("input", cmd.getOptionValue('i'));
        conf.put("output", cmd.getOptionValue('o'));
        conf.put("distributionName", cmd.getOptionValue('N'));
        conf.put("distributionVersion", cmd.getOptionValue('V'));
        if (cmd.hasOption('t')) conf.put("tuj", cmd.getOptionValue('t'));
        if (cmd.hasOption('P')) conf.put("fileSubstitution", cmd.getOptionValue('P'));
        if (cmd.hasOption('C')) conf.put("contextSubstitution", cmd.getOptionValue('C'));
        return conf;
    }
}
