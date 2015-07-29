package com.vdshb;

import com.vdshb.exceptions.PathNotFound;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CurrentPath {
    private static Path currentPath = Paths.get("/");

    public static String pwd() {
        return currentPath.toString();
    }

    public static Path getCurrentPath() {
        return currentPath;
    }

    public static void setCurrentPath(Path path) {
        CurrentPath.currentPath = path;
    }

    public static Path getExistRelatedPath(String path) throws PathNotFound {
        Path destination = getRelatedPath(path);

        if (Files.exists(destination)) {
            return destination;
        } else {
            throw new PathNotFound();
        }
    }

    public static Path getRelatedPath(String path) {

        if (path.startsWith("/")) {
            return Paths.get(path);
        } else {
            return currentPath.resolve(path);
        }

    }
}
