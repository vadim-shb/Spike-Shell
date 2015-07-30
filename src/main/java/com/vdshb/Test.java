package com.vdshb;

import com.vdshb.commands.Tail;

public class Test {
    public static void main(String[] args) {
        String[] tailTestArgs =  {"/home/vshb/temp/neo.txt", "0"};
        Tail.tail(tailTestArgs);
    }
}
