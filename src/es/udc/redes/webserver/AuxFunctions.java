package es.udc.redes.webserver;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class AuxFunctions {

    public static Path getpath(String p){
        String aux;
        String currentDir = System.getProperty("user.dir");

        if(p.equals("/")){
            aux = "/index.html";
        } else aux=p;

        return Path.of(currentDir, "/p1-files", aux);
    }

    public static Date get_ifmodsince(String[] message) throws ParseException {

        Date date=null;
        int i = message.length;
        String[] aux = message[i-1].split(": ");


        if (aux.length==2 && aux[0].equals("If-Modified-Since")){
            date = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH).parse(aux[1]);
        }

        return date;
    }

    public static void head(Path path, BufferedOutputStream outStream, String status) throws IOException {
        byte [] filecont = Files.readAllBytes(path);

        outStream.write(("HTTP/1.0 " + status + "\r\n").getBytes());
        outStream.write(("Date: " + new Date(System.currentTimeMillis()) + "\r\n").getBytes());
        outStream.write(("Server: WebServer_158 \r\n").getBytes());
        outStream.write(("Content-Length: " + filecont.length + "\r\n").getBytes());
        outStream.write(("Content-Type: " + Files.probeContentType(path) + "\r\n").getBytes());
        outStream.write(("Last-Modified: " + new Date(Files.getLastModifiedTime(path).toMillis()) + "\r\n\r\n").getBytes());
        outStream.flush();
    }

    public static void get(Path path, BufferedOutputStream outStream){

        try{
            byte [] filecont = Files.readAllBytes(path);
            outStream.write(filecont);
            outStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
