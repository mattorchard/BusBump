package com.orchard.seg.busbump.parser;

import android.text.TextUtils;

import java.io.IOException;

public class OCTranspoException extends IOException {

    public OCTranspoException(String message) {
        super(OCTranspoErrorCode.getDefaultMessage(message));
    }

    private enum OCTranspoErrorCode {
        API_KEY(1, "Invalid API key"),
        DATA_SOURCE(2, "Unable to query data source"),
        STOP_NO(10, "Invalid route number"),
        ROUTE_NO(11, "Invalid route number"),
        SERVICE_ROUTE(12, "Stop does not service route");
        private static final String unrecognizedErrorCodeMessage
                = "Unknown OCTranspo error code received: ";

        private final int mErrorCode;
        private final String mDefaultMessage;

        OCTranspoErrorCode(int errorCode, String defaultMessage) {
            this.mErrorCode = errorCode;
            this.mDefaultMessage = defaultMessage;
        }

        public static String getDefaultMessage(int errorCode) {
            for (OCTranspoErrorCode possibleCode : OCTranspoErrorCode.values()) {
                if (errorCode == possibleCode.mErrorCode) {
                    return possibleCode.mDefaultMessage;
                }
            }
            return unrecognizedErrorCodeMessage + errorCode;
        }

        public static String getDefaultMessage(String message) {
            return getDefaultMessage(extractErrorCode(message));
        }

        private static int extractErrorCode(String message) {
            return Integer.parseInt(message.replaceAll("[^0-9]", ""));
        }
    }
}
