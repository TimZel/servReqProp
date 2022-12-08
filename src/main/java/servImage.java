
import jdk.jfr.ContentType;

import java.io.*;
import java.net.ServerSocket;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class servImage {
//    static String getContentType(String exe) throws FileNotFoundException {
//        Pattern patternExeContentType = Pattern.compile(exe + "(.+)");
//        String fileName = "/home/zelyanin/IdeaProjects/servReq2.0/src/main/java/testerFiletable";
//        String exeParse = null;
//        try (var buff = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))) {
//            while (buff.ready()) {
//                String str = buff.readLine();
//                Matcher matcherExeContentType = patternExeContentType.matcher(str);
//                if (matcherExeContentType.find()) {
//                    exeParse = matcherExeContentType.group(1);
//                }
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return exeParse;
//    }

    public static final  String notFound = "HTTP/1.0 404 Not Found\r\nContent-Type: text/html\r\n\r\n";
    public static final String badRequest = "HTTP/1.0 400 Bad Request\r\nContent-Type: text/html\r\n\r\n";
    public static String pathToProperties = "/home/zelyanin/IdeaProjects/servReqProperties/src/main/java/testerFiletable";

    public static void main(String[] args) throws IOException {
        Properties prop = new Properties();
        try {
            FileInputStream fisProperties = new FileInputStream(pathToProperties);
            prop.load(fisProperties);
            if ( !(prop == null) ) {
                int visit = 0;//счетчик посетителей
                ServerSocket servsock = new ServerSocket(12000);//серверсокет с указанным портом
                do {
                    try ( var socket = servsock.accept();// сокет соединения с клиентом
                          var isr = socket.getInputStream();//принимаю поток
                          var out = new DataOutputStream(socket.getOutputStream()) ) //вывожу поток
                    {
                        System.out.println("Client " + (++visit) + " accepted.");//информирую об обращении клиента
                        Request request = Request.parse(isr);//создаю побъект типа Реквест, вызываю метод парсинга и передаю туда входящий поток
                        //возвращаю обработанные данные
                        File file = new File(request.path);
                        if(request == null) {
                            continue;
                        }
                        if (file.isDirectory()) { //если запроше не файл
                            out.write(badRequest.getBytes());
                            System.out.println("Warning! The user requested not a file!");
                            continue;
                        }

                        try (var in = new FileInputStream(file)) { //открываю поток для чтения файла
                            String contentType = prop.getProperty(request.exe);
                            String httpResponse = "HTTP/1.0 200 OK\r\n" + "Content-Type:" + contentType +  "\r\n" + "\r\n";
                            byte[] bytes = new byte[5*1024];//создаю байт-массив для хранения информации из файла
                            out.write(httpResponse.getBytes());//отправляю хттп-ответ
                            int count;
                            while ((count = in.read(bytes)) > -1) {
                                out.write(bytes, 0, count);//отправляю содержимое файла
                            }
                            System.out.println("The request from user was completed succesfully!");
                            //checking the parsed values:
                            System.out.println("\nrequest information: "+ "\n" + "method: " + request.method + "\n"+ "path: " + request.path+ "\n" + "http: "+
                                    request.http  +"\n"+ "contentType:" + contentType);
                            System.out.println("body: ");
                            for (int i = 0; i < request.bodyList.size(); i++) {
                                System.out.println(request.bodyList.get(i));
                            }
                            System.out.println("\n\n");
                            System.out.println("headers and values: ");
                            for(var pair: request.mapHeadersValues.entrySet())
                            {
                                System.out.println(pair.getKey() + " --> " + pair.getValue());
                            }
                            System.out.println("\n\n");
                        } catch (FileNotFoundException ex) {
                            System.out.println("Внимание! Розыск: " + ex);//шуткую, если файла нет
                            out.write(notFound.getBytes());
                        }
                    }
                } while (visit < 5);
                servsock.close();//закрываю сервер
                System.out.println("Server closed due to settings.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
