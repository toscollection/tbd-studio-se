package com.talend.tuj.generator;

import com.talend.tuj.generator.conf.ArgsHandler;
import com.talend.tuj.generator.conf.TUJGeneratorConfiguration;
import com.talend.tuj.generator.io.TUJExporter;
import com.talend.tuj.generator.io.TUJImporter;
import com.talend.tuj.generator.utils.Context;
import com.talend.tuj.generator.utils.TUJ;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        TUJGeneratorConfiguration conf = ArgsHandler.handle(args);

        TUJImporter importer = new TUJImporter();
        List<TUJ> tujs = importer.importTUJ(conf);


        Migrator migrator = new Migrator(conf);
        List<TUJ> migratedTUJs = migrator.migrate(tujs);

        TUJExporter.exportTUJs(conf, migratedTUJs);

    }
}
