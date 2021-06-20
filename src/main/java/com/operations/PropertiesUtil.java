package com.operations;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertiesUtil {

    static Properties prop;

    public static String getProperties(String key) {
       String value = "";
        if (prop == null || prop.size() == 0) {
            initializeConfigs();
        }
        if(prop!=null && prop.size()>0){
            value=prop.getProperty(key);
        }
        return value;
    }

    private static void initializeConfigs() {
        if (prop == null || prop.size() == 0) {
            try (InputStream input = new FileInputStream("resources/config.properties")) {
                prop = new Properties();
                prop.load(input);
                System.out.println("############### loading Configs Started ###############\n");
                prop.forEach((key, value) ->
                        System.out.println(key + ":" + value)
                );
                System.out.println("\n############### loading Configs Ended ###############");

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
