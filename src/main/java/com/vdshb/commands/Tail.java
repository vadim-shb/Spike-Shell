package com.vdshb.commands;

import com.vdshb.CurrentPath;
import com.vdshb.exceptions.WrongParamsException;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

import static java.lang.System.out;

public class Tail {

    private static int tailLinesNumber;
    private static boolean followFlag;
    private static Path filePath;
    private static boolean watchFlag;

    public static void tail(String[] args) {
        try {
            setParams(args);
        } catch (WrongParamsException e) {
            return;
        }
        tail();
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


    private static void tailWatch() {
        try {
            watchFlag = true;
            new Thread(() -> {
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
                            out.println("================================================ file modified ================================================");
                            tail();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void tail() {
        int READ_BLOCK_SIZE = 100; // can not be less than max UTF-8 char (7 bytes)


        ByteBuffer buffer = ByteBuffer.allocate(READ_BLOCK_SIZE);
        CharBuffer charBuffer;
        Charset charSet = StandardCharsets.UTF_8;
        try (FileChannel fc = FileChannel.open(filePath, StandardOpenOption.READ)) {
            long fileSize = Files.size(filePath);
            long position = findTailBeginPosition();
            while (position <= fileSize) {

                fc.position(position);
                buffer.clear();
                fc.read(buffer);

                int foreignBytes = getForeignUtf8Bytes(buffer.array());

                buffer.flip();
                buffer.limit(buffer.limit() - foreignBytes);
                charBuffer = charSet.decode(buffer);
                out.print(charBuffer.toString());

                position = position + READ_BLOCK_SIZE - foreignBytes;
            }
            out.println();

        } catch (IOException e) {
            out.println("Can not read file");
        }

    }

    private static int getForeignUtf8Bytes(byte[] buffer) {
        int additionalBytes = 0;
        for (int i = buffer.length - 1; i >= 0; i--) {
            if ((buffer[i] & 0x80) == 0)
                return 0;
            if ((buffer[i] & 0xC0) >>> 6 == 2) {
                additionalBytes++;
                continue;
            }
            if ((buffer[i] & 0xC0) >>> 6 == 3 && additionalBytesNumber(buffer[i]) > additionalBytes) {
                return ++additionalBytes;
            }
            return 0;
        }
        return buffer.length;
    }

    private static int additionalBytesNumber(byte firstByteOfUtf8Char) { //does not check if it's not first utf-8 byte of char
        if ((firstByteOfUtf8Char & 0x80) == 0) return 0;
        if ((firstByteOfUtf8Char & 0x20) == 0) return 1;
        if ((firstByteOfUtf8Char & 0x10) == 0) return 2;
        if ((firstByteOfUtf8Char & 0x08) == 0) return 3;
        if ((firstByteOfUtf8Char & 0x04) == 0) return 4;
        if ((firstByteOfUtf8Char & 0x02) == 0) return 5;
        return 6;
    }


    private static long findTailBeginPosition() throws IOException {
        int READ_BLOCK_SIZE = 100;

        ByteBuffer buffer = ByteBuffer.allocate(READ_BLOCK_SIZE);
        try (FileChannel fc = FileChannel.open(filePath, StandardOpenOption.READ)) {
            int enterCounter = 0;
            int blockCounter = 0;
            int positionOverflow = 0;
            long fileSize = Files.size(filePath);
            long position = fileSize;
            while (position > 0) {
                blockCounter++;
                position = position - READ_BLOCK_SIZE;
                if (position < 0) {
                    positionOverflow = (int) -position;
                    position = 0;
                }
                fc.position(position);
                buffer.clear();
                fc.read(buffer);
                buffer.limit(buffer.limit()-positionOverflow);
                for (int i = buffer.limit() - 1; i >= 0; i--) {
                    if (buffer.get(i) == 10) {
                        enterCounter++;
                        if (enterCounter == tailLinesNumber) {
                            return fileSize - (blockCounter * READ_BLOCK_SIZE) + i + positionOverflow + 1;
                        }
                    }
                }
            }
        }

        return 0;
    }
}
