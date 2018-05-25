package com.codegen.jet.dolphins.tools;

import java.io.PrintStream;

public class GLogger {
	public static final int TRACE = 60;
	public static final int DEBUG = 70;
	public static final int INFO = 80;
	public static final int WARN = 90;
	public static final int ERROR = 100;

	public static int logLevel = INFO;
	public static PrintStream out = System.out;
	public static PrintStream err = System.err;

	public static boolean perf = false;

	private static final String TRACE_MSG = "[Generator TRACE] ";
	private static final String DEBUG_MSG = "[Generator DEBUG] ";
	private static final String INFO_MSG = "[Generator INFO] ";
	private static final String WARN_MSG = "[Generator WARN] ";
	private static final String ERROR_MSG = "[Generator ERROR] ";

	private static final String PERFORMANCE_MSG = "[Generator Performance] ";

	public static void trace(String s) {
		if (logLevel <= TRACE)
			out.println(TRACE_MSG + s);
	}
	
	public static void debug(String s) {
		if (logLevel <= DEBUG)
			out.println(DEBUG_MSG + s);
	}

	public static void info(String s) {
		if (logLevel <= INFO)
			out.println(INFO_MSG + s);
	}

	public static void warn(String s) {
		if (logLevel <= WARN)
			err.println(WARN_MSG + s);
	}

	public static void error(String s) {
		if (logLevel <= ERROR)
			err.println(ERROR_MSG + s);
	}

	public static void warn(String s, Throwable e) {
		if (logLevel <= WARN) {
			err.println(WARN_MSG + s + " cause:"+e);
			e.printStackTrace(err);
		}
	}

	public static void error(String s, Throwable e) {
		if (logLevel <= ERROR) {
			err.println(ERROR_MSG + s + " cause:"+e);
			e.printStackTrace(err);
		}
	}
	

    public static void perf(String s) {
        if(perf)
            out.println(PERFORMANCE_MSG + s);
    }
	
	public static void println(String s) {
		if (logLevel <= INFO) {
			out.println(s);
		}
	}
	
}
