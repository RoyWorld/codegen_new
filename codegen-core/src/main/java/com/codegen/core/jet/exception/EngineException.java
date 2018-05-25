package com.codegen.core.jet.exception;

/**
 * Created by RoyChan on 2017/12/11.
 */
public class EngineException extends Exception {
    private static final String TAG = "[EngineException]";

    public EngineException() {
        super();
    }

    public EngineException(String message) {
        super("[EngineException] ERROR:" + message);
    }
}
