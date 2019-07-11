package sample.LoggingManager;


import sample.GUI.AlertWindow;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

public class Logger {

    static java.util.logging.Logger logger = java.util.logging.Logger.getLogger("MyLog");
    static FileHandler fh;
    static boolean ready = false;

    public static void getLogger() {
        try {
            File parent = new java.io.File(System.getProperty("user.home"), "Moaqeet User Files");
            File child = new File(parent.getAbsolutePath() , "Log");
            boolean success = true;

            if (!parent.exists()){
                parent.mkdirs();
                success = child.mkdirs();
            } else if(!child.exists()){
                success = child.mkdirs();

            }

            if (success){
                String path = child.getAbsolutePath();
                fh = new FileHandler(path + "/LogFile.log", true);
                logger.addHandler(fh);
                SimpleFormatter formatter = new SimpleFormatter();
                fh.setFormatter(formatter);
                ready = true;
            }
            else {
                throw new IOException("couldn't create the directory for the logger file");
            }


        } catch (SecurityException e) {
            AlertWindow.display("Error", e.getMessage());
        } catch (IOException e) {
            AlertWindow.display("Error", e.getMessage());
        }
    }

    public static void close(boolean properly) {

        if (ready) {
            if (properly) {
                logger.info("__________________________________ End of session! __________________________________");
            } else {
                logger.info("____________________________ Unexpected end of session! _____________________________");
            }

            fh.close();
        }
    }

    public static void info(String sourceClass, String sourceMethod, String msg) {
        if (ready) {
            logger.logp(Level.INFO, sourceClass, sourceMethod, msg);
        }
    }

    public static void Error(String sourceClass, String sourceMethod, String msg, Throwable throwable) {
        if (ready) {
            logger.logp(Level.SEVERE, sourceClass, sourceMethod, msg, throwable);
        }
    }
}
