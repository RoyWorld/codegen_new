package com.codegen.jet.core.tool;

public class SystemHelper {
    public static boolean isWindowsOS = System.getProperty("os.name").toLowerCase().indexOf("windows")>= 0;
}
