package com.test.build;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.test.exception.BuildException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This is an utils class which will deploy the SCONSTRUCT and VERSION files to the
 * supplied source path as argument
 *
 * The deployed files files will have updated version number picked from the env property BuildNum.
 *
 * This class is used by the updateVersion.sh script to deploy to any env.
 *
 * The resources/build/templates directory have the template files with placeholder for the
 * version values which will be replaced while running the script.
 */
public class VersionUtils {

    public static void main(String[] args) throws IOException, BuildException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(args[0]), "Source file path cannot be empty");
        Preconditions.checkArgument(StringUtils.isNotEmpty(args[1]), "Property to change cannot be empty ");
        updateBuildFiles(args[0]);
    }

    private static void updateBuildFiles(String filePath) throws IOException, BuildException {

        String replaceValue = System.getProperty("BuildNum");
        String replaceTemplateField = "<version>";
        List<String> files = IOUtils.readLines(Thread.currentThread().getClass().getClassLoader().getResourceAsStream("build/templates/"), Charsets.UTF_8);

        try {
            files.forEach(file -> {
                Stream<String> lines = null;
                try {
                    lines = Files.lines(Paths.get(file));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                List<String> updatedLines = lines.map(line -> replacedLine(line, replaceTemplateField, replaceValue)).collect(Collectors.toList());
                Path path = Paths.get(filePath + "/" + file);
                writeLines(updatedLines, path);
            });
        } catch (Exception e) {
            throw new BuildException("[ERROR][BUILD]: Exception in build : " + e.getMessage());
        }
    }

    private static String replacedLine(String line, String propertyName, String value) {
        if (line.contains(propertyName)) {
            return line.replace("<version>", value);
        }
        return line;
    }

    private static void writeLines(List<String> list, Path path) {
        try {
            byte[] bytes = list.stream().collect(Collectors.joining("\n")).getBytes();
            Files.write(path, bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
