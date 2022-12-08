package org.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Pattern patternExe = Pattern.compile("(\\.[^\s]*)");
        String g = "/home/zelyanin/Desktop/testFolder/test2.txt HTTP/1.0\"";

        Matcher matcherExe = patternExe.matcher(g);
        String exeParse = null;
        if(matcherExe.find()) {
            exeParse = matcherExe.group();
        }
       System.out.println(exeParse);
    }
}