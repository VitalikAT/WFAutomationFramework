package com.runner.test;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
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

public class MyLogHolder {
    private static ThreadLocal<Logger> threadLocal = new ThreadLocal<>();

    public static Logger getLoggerHolder(String testName, String className) {
        if (threadLocal.get() == null) {
            initLogger(testName, className);
        }
        return threadLocal.get();
    }

    private static Logger getLoggerFromPool() {
        return threadLocal.get();
    }

    public static void removeLogger() {
        threadLocal.remove();
    }

    private static void initLogger(String testName, String className) {
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        Configuration config = context.getConfiguration();
        PatternLayout layout = PatternLayout.newBuilder()
                .withPattern("[%-5p] %d{HH:mm:ss} %c: %m%n")
                .build();
        Appender consoleAppender = ConsoleAppender.newBuilder()
                .withLayout(layout)
                .withName("consoleAppender")
                .withIgnoreExceptions(false)
                .build();
        consoleAppender.start();
        config.addAppender(consoleAppender);
        Appender fileAppender = FileAppender.newBuilder()
                .withName("fileAppender")
                .withAppend(false)
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
        String loggerName = "LoggerID: " + RandomStringUtils.randomAlphanumeric(7);
        LoggerConfig loggerConfig = LoggerConfig
                .createLogger(false, Level.DEBUG, loggerName, "true", appenderRefs, null, config, null);
        loggerConfig.addAppender(consoleAppender, Level.INFO, config.getFilter());
        loggerConfig.addAppender(fileAppender, Level.DEBUG, config.getFilter());
        config.addLogger(loggerName, loggerConfig);
        context.updateLoggers(config);
        Logger logger = context.getLogger(loggerName);
        threadLocal.set(logger);
    }

    @Step("{0}")
    public static void info(Object message) {
        getLoggerFromPool().info(message);
    }

    public static void debug(Object message) {
        getLoggerFromPool().debug(message);
    }

    public static void error(String message) {
        getLoggerFromPool().error(message);
    }
}
