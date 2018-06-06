package com.codegen.jet.core.exception;

/**
 * Created by RoyChan on 2017/12/11.
 */
public class FactoryException extends Exception {
    private static final String TAG = "[FactoryException]";

    public FactoryException() {
        super();
    }

    public FactoryException(String message) {
        super("[FactoryException] ERROR:" + message);
    }
}
