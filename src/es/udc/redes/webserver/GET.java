package es.udc.redes.webserver;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

public class GET extends Method{

    Date if_mod_since;

    public GET(Path path, BufferedOutputStream outStream, Date if_mod_since) {
        super(path, outStream);
        this.if_mod_since = if_mod_since;
    }

    public void method() throws IOException {
        if (Files.exists(path)){
            Date last_mod_date = new Date(Files.getLastModifiedTime(path).toMillis());


            if(if_mod_since==null){
                AuxFunctions.head(path,outStream, "200 OK");
                AuxFunctions.get(path,outStream);
                return;
            }
            Long last_mod = last_mod_date.getTime()/1000;
            Long if_mod = if_mod_since.getTime()/1000;

            if (last_mod.compareTo(if_mod)>0){
                AuxFunctions.head(path,outStream, "200 OK");
                AuxFunctions.get(path,outStream);

            } else{
                AuxFunctions.head(path,outStream, "304 Not Modified");
            }

        } else{
            Path error = AuxFunctions.getpath("error404.html");
            AuxFunctions.head(error,outStream, "404 Not Found");
            AuxFunctions.get(error,outStream);
        }

    }
}
