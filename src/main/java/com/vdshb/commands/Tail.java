package com.vdshb.commands;

import com.vdshb.CurrentPath;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class Tail {

    public static void tail(String[] args) {
        if (args.length < 1) {
            out.println("Incorrect command parameters.");
            return;
        }
        int tailLinesNumber;
        if (args.length > 1) {
            tailLinesNumber = Integer.valueOf(args[1]);
        } else {
            tailLinesNumber = 10;
        }
        Path filePath = CurrentPath.getRelatedPath(args[0]);
        tail(filePath, tailLinesNumber);
    }

    private static void tail(Path fileToRead, int tailLinesNumber) {
        StringVault tail = new StringVault(tailLinesNumber);
        try (BufferedReader reader = Files.newBufferedReader(fileToRead, StandardCharsets.UTF_8)) {
            String line = null;
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
