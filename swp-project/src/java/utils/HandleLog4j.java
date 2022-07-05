package utils;

import java.io.File;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class HandleLog4j {

    static Logger logger = Logger.getLogger(HandleLog4j.class);

    public static void main(String[] args) {
        //String log4jConfigFile =env.USER_DIR_WIN+"\\swp-project\\web\\WEB-INF\\log4j.properties";
        String log4jConfigFile =env.USER_DIR_MAC+"/swp-project/web/WEB-INF/log4j.properties";
        PropertyConfigurator.configure(log4jConfigFile);        
        logger.error("this is a information log message");   
    }

}
