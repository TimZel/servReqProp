package org.example;

import java.io.FileWriter;
import java.io.IOException;

public class bodyWriter {
    public static void main(String[] args) {
        try(FileWriter writer = new FileWriter("/home/zelyanin/IdeaProjects/servReq2.0/src/main/java/bodyWriterReader", false))
        {
            // запись всей строки
            String text = "hui";
            writer.write(text);
            // запись по символам
            writer.append('\n');
            writer.append('E');

            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }
}
