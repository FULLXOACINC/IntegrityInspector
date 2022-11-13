package org.plagiarism.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
public class TTT {
    private String checkedCodeFile;
    private String baselineCodeFile;
    private int checkedCodeFileLineCount;
    private List<Boolean> fullness;

    public TTT(String checkedCodeFile, String baselineCodeFile, int checkedCodeFileLineCount) {
        this.checkedCodeFile = checkedCodeFile;
        this.baselineCodeFile = baselineCodeFile;
        this.checkedCodeFileLineCount = checkedCodeFileLineCount;
        List<Boolean> list = new ArrayList<>(Arrays.asList(new Boolean[checkedCodeFileLineCount]));
        Collections.fill(list, Boolean.FALSE);
        this.fullness = list;
    }
}