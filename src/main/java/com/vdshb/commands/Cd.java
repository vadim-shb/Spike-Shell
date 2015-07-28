package com.vdshb.commands;

import com.vdshb.CurrentPath;
import com.vdshb.exceptions.PathNotFound;

import java.nio.file.Path;

import static java.lang.System.out;

public class Cd {
    public static void cd(String[] args) {
        String where = args[args.length - 1];
        try {
            Path destination = CurrentPath.getRelatedPath(where);
            CurrentPath.setCurrentPath(destination);
        } catch (PathNotFound pathNotFound) {
            out.println("Path not exists");
        }
    }
}
