package com.runner.utils;

import java.io.File;
import java.io.IOException;

public class RunHubAndNodes {

    private static String operatingSystem = System.getProperty("os.name").toLowerCase();

    private static String getAbsolutePath(String filePath) {
        File file = new File(filePath);
        return file.getAbsolutePath();
    }

    private static boolean isWindows() {
        return operatingSystem.contains("win");
    }

    private static boolean isUnix() {
        return operatingSystem.contains("nix") || operatingSystem.contains("nux") || operatingSystem.contains("aix");

    }

    private static void runHubAndNodes() throws IOException {
        if (isWindows()) {
            Runtime.getRuntime().exec("cmd /c start " + getAbsolutePath("./web-selenium-server/runHub.bat"));
            Runtime.getRuntime().exec("cmd /c start " + getAbsolutePath("./web-selenium-server/runNode1.bat"));
            Runtime.getRuntime().exec("cmd /c start " + getAbsolutePath("./web-selenium-server/runNode2.bat"));
        } else if (isUnix()) {
            Runtime.getRuntime().exec(getAbsolutePath("./web-selenium-server/runHub.sh"));
            Runtime.getRuntime().exec(getAbsolutePath("./web-selenium-server/runNode1.sh"));
            Runtime.getRuntime().exec(getAbsolutePath("./web-selenium-server/runNode2.sh"));
        }
    }

    private static void stopHubAndNodes() {
        if (isWindows()) {
            try {
                Runtime.getRuntime().exec("cmd /c start " + getAbsolutePath("./web-selenium-server/stopAllServersAndNodes.bat"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            runHubAndNodes();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        stopHubAndNodes();

    }
}
