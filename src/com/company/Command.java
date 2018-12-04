package com.company;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Command {

    static private String USER_DIR = "user.dir";

    // pwd command
    public static void pwd() {
        String path = System.getProperty(USER_DIR);
        System.out.println(path);
    }

    // ls command
    public static void ls(String[] args) {

        int argSize = args.length;
        String options, filePath;

        if (argSize == 0) {
            options = "";
            filePath = "";
        } else if(argSize == 1) {
            options = args[0];
            filePath = "";
        } else {
            options = args[0];
            filePath = args[1];
        }

        filePath = System.getProperty(USER_DIR) + "/" +  filePath;

        File dir = new File(filePath);
        List<File> files = Arrays.asList(dir.listFiles());
        List<String> output;

        // do not options contain "a" ?
        if (!options.contains("a"))
            files = files.stream().filter(file -> !file.getName().startsWith(".")).collect(Collectors.toList());

        // do not options contain "l" ?
        if (options.contains("l")) {
            output = files.stream().map(file -> getDrwx(file) + "   " + file.getName()).collect(Collectors.toList());
        } else {
            output = files.stream().map(file -> file.getName()).collect(Collectors.toList());
        }

        output.stream().forEach(System.out::println);
    }

    // check isDirectory, canread, canwrite, canexecute
    private static String getDrwx(File file) {
        String fileAuth = "";
        String non = "-";

        // check file is directory
        fileAuth = file.isDirectory()? fileAuth + "d" : fileAuth + non;

        // check file can be read
        fileAuth = file.canRead()? fileAuth + "r" : fileAuth + non;

        // check file can be written
        fileAuth = file.canWrite()? fileAuth + "w" : fileAuth + non;

        // check file can be executable
        fileAuth = file.canExecute()? fileAuth + "x" : fileAuth + non;

        return fileAuth;
    }

}
