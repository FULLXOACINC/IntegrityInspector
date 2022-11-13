package org.plagiarism.app;

import com.google.gson.Gson;
import org.plagiarism.model.Project;
import org.plagiarism.parser.ProjectParser;
import org.plagiarism.report.ReportCreator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class App {

    public static void main(String[] args) throws IOException {

        File folder = new File("./codebase");
        File checkFolder = new File("./checked");
        ProjectParser parser = new ProjectParser();
        List<Project> baselineProjects = parser.parseProjectListFromRootDir(folder);
        Project checkProject = parser.parseProject(checkFolder);
        ReportCreator reportCreator = new ReportCreator();

        BufferedWriter writer = new BufferedWriter(new FileWriter("test.json"));
        Gson gson = new Gson();
        writer.write(gson.toJson(reportCreator.create(checkProject, baselineProjects)));
        writer.close();
    }


}
