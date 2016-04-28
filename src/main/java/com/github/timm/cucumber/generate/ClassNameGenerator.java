package com.github.timm.cucumber.generate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;

import com.google.common.base.CaseFormat;

public class ClassNameGenerator {

    private static final String PARALLEL_TEST_NAME = "Parallel%02dIT.java";
    private final Pattern startsWithDigit = Pattern.compile("^\\d.*");

    public String generateClassNameFromFeatureFileName(String featureFileName, int fileCounter) {

        String fileNameWithNoExtension = FilenameUtils.removeExtension(featureFileName);

        fileNameWithNoExtension = fileNameWithNoExtension.replaceAll("_", "-");
        fileNameWithNoExtension = fileNameWithNoExtension.replaceAll(" ", "");

        String className = CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, fileNameWithNoExtension);

        Matcher startsWithDigitCheck = startsWithDigit.matcher(className);

        if (startsWithDigitCheck.matches()) {
            className = "_" + className;
        }

        return String.format(className + "%02dIT.java", fileCounter);
    }

    public String generateSimpleClassName(int fileCounter) {

        return String.format(PARALLEL_TEST_NAME, fileCounter);

    }

}
