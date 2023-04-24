package es.udc.redes.webserver;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Path;

public class Method {

    Path path;
    BufferedOutputStream outStream;

    public Method(Path path, BufferedOutputStream outStream) {
        this.path = path;
        this.outStream = outStream;
    }

    public void method() throws IOException {

        Path error = AuxFunctions.getpath("error400.html");
        AuxFunctions.head(error,outStream, "400 Bad Request");
        AuxFunctions.get(error,outStream);
    }

}
