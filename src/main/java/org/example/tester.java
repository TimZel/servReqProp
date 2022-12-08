package org.example;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class tester {
    public static void main(String[] args) throws IOException {
        //Pattern patternExeContentType = Pattern.compile("(\\.[^\\s]*)(\\s.*\\s)");
        String EXAMPLE = ".txt";
        Pattern patternExeContentType = Pattern.compile(EXAMPLE + "(.+)");
        String fileName = "/home/zelyanin/IdeaProjects/servReq2.0/src/main/java/exes/testerFiletable";
        FileInputStream input = new FileInputStream(fileName);
        InputStreamReader reader = new InputStreamReader(input);
        BufferedReader buff = new BufferedReader(reader);
        String exeParse = null;
        while (buff.ready()) {
            String str = buff.readLine();
            Matcher matcherExeContentType = patternExeContentType.matcher(str);
            if(matcherExeContentType.find()) {
                exeParse = matcherExeContentType.group(1);
            }
        }
        System.out.println(exeParse);

    }
}
