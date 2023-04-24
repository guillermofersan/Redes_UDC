package es.udc.redes.tutorial.copy;

import java.io.*;

public class Copy {

    public static void main(String[] args) throws IOException {

        FileInputStream inStream = null;
        FileOutputStream outStream = null;

        try{
            int c;

            inStream = new FileInputStream(args[0]);
            outStream = new FileOutputStream(args[1]);

            while ((c = inStream.read()) != -1){
                outStream.write(c);
            }
        } finally {
            if (inStream!=null){
                inStream.close();
            }
            if (outStream!=null){
                outStream.close();
            }
        }
    }
}