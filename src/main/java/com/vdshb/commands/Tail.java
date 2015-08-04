package com.vdshb.commands;

import com.vdshb.CurrentPath;
import com.vdshb.exceptions.WrongParamsException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class Tail {

    private static int tailLinesNumber;
    private static boolean followFlag;
    private static Path filePath;

    public static void tail(String[] args) {
        try {
            setParams(args);
        } catch (WrongParamsException e) {
            return;
        }
        tail(filePath, tailLinesNumber);
        if (followFlag) {
            tailWatch();
        }
    }

    private static void setParams(String[] args) throws WrongParamsException {
        if (args.length < 1) {
            out.println("Incorrect command parameters.");
            throw new WrongParamsException();
        }
        setTailLinesNumberParam(args);
        setFollowFlagParam(args);
        setPathParam(args);
    }

    private static void setTailLinesNumberParam(String[] args) throws WrongParamsException {
        tailLinesNumber = 10;
        for (int i = 0; i < args.length - 1; i++) {
            if ("-n".equals(args[i])) {
                try {
                    tailLinesNumber = Integer.valueOf(args[i + 1]);
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    out.println("Wrong number of lines");
                    throw new WrongParamsException();
                }
            }
        }
    }

    private static void setFollowFlagParam(String[] args) throws WrongParamsException {
        followFlag = false;
        for (int i = 0; i < args.length - 1; i++) {
            if ("-f".equals(args[i])) {
                followFlag = true;
            }
        }
    }

    private static void setPathParam(String[] args) throws WrongParamsException {
        filePath = CurrentPath.getRelatedPath(args[args.length - 1]);
        if (!Files.exists(filePath)) {
            out.println("File not exists");
            throw new WrongParamsException();
        }
    }

    private static void tail(Path fileToRead, int tailLinesNumber) {
        StringVault tail = new StringVault(tailLinesNumber);
        try (BufferedReader reader = Files.newBufferedReader(fileToRead, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null)
                tail.addString(line);
            for (String tailLine : tail.getStrings())
                out.println(tailLine);
        } catch (IOException e) {
            out.println("Can not read file");
        } catch (IllegalArgumentException ex) {
            out.println("Illegal lines number");
        }
    }

    private static class StringVault {
        private final String[] strings;
        private int pointer = -1;
        private int capacity = 0;
        private boolean round = false;

        public StringVault(int vaultCapacity) {
            if (vaultCapacity < 1) throw new IllegalArgumentException();
            this.strings = new String[vaultCapacity];
            this.capacity = vaultCapacity;
        }

        public void addString(String str) {
            incrementPointer();
            strings[pointer] = str;
        }

        public List<String> getStrings() {
            List<String> retval = new ArrayList<>(capacity);
            if (round) {
                for (int i = 0; i < capacity; i++) {
                    incrementPointer();
                    retval.add(strings[pointer]);
                }

            } else {
                for (int i = 0; i <= pointer; i++) {
                    retval.add(strings[i]);
                }
            }
            return retval;
        }

        private void incrementPointer() {
            pointer++;
            if (pointer == capacity) {
                pointer = 0;
                round = true;
            }
        }


    }

    private static boolean watchFlag;
    private static void tailWatch() {
        try {
            watchFlag = true;
            new Thread(()->{
                int inputChar = 0;
                try {
                    inputChar = System.in.read();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
                if (inputChar == 'q')
                    watchFlag = false;

            }).start();
            WatchService watchService = FileSystems.getDefault().newWatchService();
            WatchKey watchKey = filePath.getParent().register(watchService, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.OVERFLOW, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_CREATE);
            while (watchFlag) {
                for (WatchEvent<?> event : watchKey.pollEvents()) {

                    WatchEvent.Kind<?> kind = event.kind();
                    if (kind == StandardWatchEventKinds.ENTRY_MODIFY || kind == StandardWatchEventKinds.OVERFLOW || kind == StandardWatchEventKinds.ENTRY_DELETE || kind == StandardWatchEventKinds.ENTRY_CREATE) {
                        WatchEvent<Path> ev = (WatchEvent<Path>) event;
                        Path changedFile = ev.context();
                        if (filePath.endsWith(changedFile)) {
                            out.println();
                            out.println("================================================ file modified ================================================");
                            tail(filePath, tailLinesNumber);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//
//    private static void tail(Path fileToRead, int tailLinesNumber) {
//        ByteBuffer buffer = ByteBuffer.allocate(200);
//        CharBuffer charBuffer;
//        Charset charSet = StandardCharsets.UTF_8;
//        try(FileChannel fc = FileChannel.open(fileToRead, StandardOpenOption.READ)){
//            long position=0;
//            fc.position(position);
//            fc.read(buffer);
//            buffer.flip();
//            charBuffer = charSet.decode(buffer);
//            out.println(charBuffer.toString());
//
//        } catch (IOException e) {
//            out.println("Can not read file");
//        }
//
//
//
//    }
}
