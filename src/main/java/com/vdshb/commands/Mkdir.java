package com.vdshb.commands;

import com.vdshb.CurrentPath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.lang.System.out;

public class Mkdir {
    public static void mkdir(String[] args) {
        if (args.length < 1) {
            out.println("Incorrect command parameters.");
            return;
        }
        Path dirToCreate = CurrentPath.getRelatedPath(args[0]);
        try {
            Files.createDirectory(dirToCreate);
        } catch (IOException e) {
            out.println("Can not create directory");
        }
    }
}
