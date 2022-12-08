package org.example;

import java.io.*;
import java.util.Properties;

public class propertiesTest {
    public static void main(String[] args) throws IOException {
        String PATH_TO_PROPERTIES = "/home/zelyanin/IdeaProjects/servReq2.0/src/main/java/testerFiletable";
        FileInputStream fileInputStream;
        OutputStream ops = null;
//инициализируем специальный объект Properties
//типа Hashtable для удобной работы с данными
        Properties prop = new Properties();

        try {
//обращаемся к файлу и получаем данные
            fileInputStream = new FileInputStream(PATH_TO_PROPERTIES);
            prop.load(fileInputStream);
            String site = prop.getProperty(".txt");
            System.out.println("site: " + site);
            prop.store(ops, null);

        } catch (IOException e) {
            System.out.println("Ошибка в программе: файл " + PATH_TO_PROPERTIES + " не обнаружено");
            e.printStackTrace();
        }
        // store the properties list in an output stream


        PipedInputStream pis = new PipedInputStream((PipedOutputStream) ops);
        //PipedOutputStream pout = new PipedOutputStream(pis);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pis));//Обёртка для удобной работы со строками
        //BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(pout));
        ops.write(10);
        String contentType = bufferedReader.readLine();
        System.out.println(contentType);

    }

}
