package com.epam.test;

import io.qameta.allure.Step;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;


public class TestLogger {

    private static TestLogger logger;
    private Logger LOG;

    private TestLogger(String testName, String className) {
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        Configuration config = context.getConfiguration();
        PatternLayout layout = PatternLayout.newBuilder()
                .withConfiguration(config)
                .withPattern("[%-5p] %d{HH:mm:ss} %c: %m%n")
                .build();
        Appender consoleAppender = ConsoleAppender.newBuilder()
                .setConfiguration(config)
                .withLayout(layout)
                .withName("consoleAppender")
                .withIgnoreExceptions(false)
                .build();
        consoleAppender.start();
        config.addAppender(consoleAppender);
        Appender fileAppender = FileAppender.newBuilder()
                .setConfiguration(config)
                .withName("fileAppender")
                .withLayout(layout)
                .withIgnoreExceptions(false)
                .withFileName(String
                        .format("%s/test-output/logs/%s/%s.log", System.getProperty("user.dir"), className, testName))
                .build();
        fileAppender.start();
        config.addAppender(fileAppender);
        AppenderRef consoleAppenderRef = AppenderRef.createAppenderRef("consoleAppender", null, null);
        AppenderRef fileAppenderRef = AppenderRef.createAppenderRef("fileAppender", null, null);
        AppenderRef[] appenderRefs = new AppenderRef[]{consoleAppenderRef, fileAppenderRef};
        LoggerConfig loggerConfig = LoggerConfig
                .createLogger(false, Level.DEBUG, "logger", "true", appenderRefs, null, config, null);
        loggerConfig.addAppender(consoleAppender, Level.INFO, config.getFilter());
        loggerConfig.addAppender(fileAppender, Level.DEBUG, config.getFilter());
        config.addLogger("logger", loggerConfig);
        context.updateLoggers(config);
        LOG = context.getLogger("logger");

    }

    public static TestLogger getLogger(String testName, String className) {
        if (logger == null) {
            logger = new TestLogger(testName, className);
        }
        return logger;
    }

    public static TestLogger getLogger() {
        if (logger == null) {
            System.out.println("getLogger(name) is not executed, test name is not set up");
        }
        return logger;
    }

    @Step("{0}")
    public void info(Object message) {
        LOG.info(message);
    }

    public void debug(Object message) {
        LOG.debug(message);
    }

    public void error(String message) {
        LOG.error(message);
    }

    public void drop() {
        if (logger != null)
            logger = null;
    }
}
