package net.jkcats.simplemvvm.utils;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import net.jkcats.simplemvvm.utils.logger.Logger;

public class LogUtils {

    private static final String TAG = "jk";

    private static final String EMPTY_MESSAGE = "empty message.";

    private static final String UNKNOWN_MESSAGE = "unknown message.";

    private static void dispatchContent(@Logger int logger, @Nullable Object object) {
        if (object == null) {
            output(logger, EMPTY_MESSAGE);
        } else {
            final String message;

            if (object instanceof Exception) {
                message = ((Exception) object).getMessage();
            } else if (object instanceof String) {
                message = String.valueOf(object);
            } else {
                message = null;
            }

            output(logger, message);
        }
    }

    public static void output(@Logger int logger, @Nullable String message) {
        if (TextUtils.isEmpty(message)) {
            Log.e(TAG, UNKNOWN_MESSAGE);
        } else {
            switch (logger) {
                case Logger.ERROR:
                    Log.e(TAG, message);
                    break;
                case Logger.INFO:
                    Log.i(TAG, message);
                    break;
                case Logger.DEBUG:
                    Log.d(TAG, message);
                    break;
                case Logger.WARNING:
                    Log.w(TAG, message);
                    break;
                default:
                    Log.wtf(TAG, message);
                    break;
            }
        }
    }

    public static void error(@Nullable Object message) {
        dispatchContent(Logger.ERROR, message);
    }

    public static void info(@Nullable Object message) {
        dispatchContent(Logger.INFO, message);
    }

    public static void debug(@Nullable Object message) {
        dispatchContent(Logger.DEBUG, message);
    }

    public static void warning(@Nullable Object message) {
        dispatchContent(Logger.WARNING, message);
    }
}
