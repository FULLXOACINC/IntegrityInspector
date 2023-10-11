package io.integrityinspector.app;

import com.beust.jcommander.JCommander;
import io.integrityinspector.Inspector.IntegrityInspector;
import io.integrityinspector.Inspector.MultipleProjectIntegrityInspector;
import io.integrityinspector.Inspector.SingleProjectIntegrityInspector;
import io.integrityinspector.config.AppConfigReader;

import java.io.IOException;

public class App {
    public static final String PLAGIARISM_CHECKER = "IntegrityInspector";

    public static void main(String[] args) throws IOException {

        AppParameters parameters = new AppParameters();
        JCommander jCommander = initAppParameters(parameters);
        jCommander.parse(args);
        if (parameters.isHelp()) {
            jCommander.usage();
            return;
        }

        AppCoreComponentsFactory appCoreComponentsFactory = new AppCoreComponentsFactory();
        AppConfigReader configReader = new AppConfigReader();
        AppCoreComponents appCoreComponents = appCoreComponentsFactory.createAppCoreComponents(configReader.readBasedOnParameters(parameters));
        IntegrityInspector integrityInspector = (parameters.getCheckingDirectory() != null) ?
                new MultipleProjectIntegrityInspector(appCoreComponents) :
                new SingleProjectIntegrityInspector(appCoreComponents);
        integrityInspector.process(parameters);

    }

    private static JCommander initAppParameters(AppParameters parameters) {
        return JCommander.newBuilder()
                .programName(PLAGIARISM_CHECKER)
                .addObject(parameters)
                .build();
    }


}
