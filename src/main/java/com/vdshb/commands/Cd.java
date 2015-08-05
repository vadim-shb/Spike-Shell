package com.vdshb.commands;

import com.vdshb.CurrentPath;
import com.vdshb.exceptions.PathNotFound;

import java.nio.file.Files;
import java.nio.file.Path;

import static java.lang.System.out;

public class Cd {
    public static void cd(String[] args) {
        String where = args[args.length - 1];
        try {
            Path destination = CurrentPath.getExistRelatedPath(where);
            if (!Files.isDirectory(destination)){
                out.println("Path is not a directory");
                return;
            }
            CurrentPath.setCurrentPath(destination.normalize());
        } catch (PathNotFound pathNotFound) {
            out.println("Path not exists");
        }
    }
}
