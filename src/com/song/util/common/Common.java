package com.song.util.common;

import java.net.URL;

public class Common {

    /**
     * The config file is located in the root directory of classpath
     * 
     * @param filename
     * @return return null if the file not exists
     */
    public static java.util.Properties loadConfigFile(String filename) {
        URL resource = DbHelper.class.getResource("/" + filename);
        if (resource == null) {
            return null;
        }
        java.util.Properties properties = new java.util.Properties();
        try {
            properties.load(new java.io.FileReader(resource.getPath()));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
