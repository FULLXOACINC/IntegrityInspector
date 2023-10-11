package io.integrityinspector.app;

import com.beust.jcommander.Parameter;
import lombok.Data;


@Data
public class AppParameters {

    @Parameter(names = {"-h", "--help"}, description = "Command which will display a description of all available options and description", help = true)
    private boolean help;

    @Parameter(names = {"-cf", "--config"}, description = "The command provides the option to override the configuration file in JSON format")
    private String configFile;

    @Parameter(names = {"-b", "--baseline-projects"}, description = "Parameter that specifies the path to the folder with the baseline projects with which the project to be checked for plagiarism will be compared")
    private String baseLineProjectDir;

    @Parameter(names = {"-ch-prj", "--checking-project"}, description = "Parameter that specifies the path to the folder with the project that will be compared for plagiarism with other baseline projects")
    private String checkingProject;

    @Parameter(names = {"-ch-dir", "--checking-directory"}, description = "Parameter that specifies the path to the folder with the projects that will be compared for plagiarism between themselves")
    private String checkingDirectory;

}
