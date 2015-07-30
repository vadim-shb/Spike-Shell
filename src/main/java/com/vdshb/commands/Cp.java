package com.vdshb.commands;

import com.vdshb.CurrentPath;
import com.vdshb.exceptions.PathNotFound;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import static java.lang.System.out;
import static java.nio.file.FileVisitResult.CONTINUE;

public class Cp {
    public static void cp(String[] args) {
        if (args.length < 2) {
            out.println("Incorrect command parameters.");
            return;
        }

        Path from;
        try {
            from = CurrentPath.getExistRelatedPath(args[0]);
        } catch (PathNotFound pathNotFound) {
            out.format("Destination %s not exist%n", args[0]);
            return;
        }
        Path to = CurrentPath.getRelatedPath(args[1]);

        if (Files.isDirectory(from))
            recursivelyCopyDirectory(from, to);
        else {
            try {
                Files.copy(from, to);
            } catch (IOException e) {
                out.println("Can not copy file");
            }
        }
    }

    private static void recursivelyCopyDirectory(Path from, Path to) {
        try {
            Files.walkFileTree(from, new CopyFileVisitor(from, to));
        } catch (IOException e) {
            out.println("Can not copy directory");
        }
    }

    private static class CopyFileVisitor implements FileVisitor<Path> {

        private Path copySourceRoot;
        private Path copyDestinationRoot;

        public CopyFileVisitor(Path copySourceRoot, Path copyDestinationRoot) {
            this.copySourceRoot = copySourceRoot;
            this.copyDestinationRoot = copyDestinationRoot;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            Files.createDirectory(copyDestinationRoot.resolve(copySourceRoot.relativize(dir)));
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            try {
                Path destination = copyDestinationRoot.resolve(copySourceRoot.relativize(file));
                Files.copy(file, destination);
            } catch (IOException e) {
                out.println("Can not copy file");
            }
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            Files.delete(copyDestinationRoot); //todo: recursively
            throw new IOException();
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            //ignore
            return CONTINUE;
        }
    }
}
