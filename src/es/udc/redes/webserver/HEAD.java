package es.udc.redes.webserver;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class HEAD extends Method{
    public HEAD(Path path, BufferedOutputStream outStream) {
        super(path, outStream);
    }


    public void method() throws IOException {

        if (Files.exists(path)){
            AuxFunctions.head(path,outStream, "200 OK");
        } else{
            AuxFunctions.head(AuxFunctions.getpath("error404.html"),outStream, "404 Not Found");
        }
    }
}
