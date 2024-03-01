package net.jkcats.simplemvvm.utils.logger;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

public class Logger {

    private static final String LOGCAT_TAG = "jk";

    private static final String EMPTY_MESSAGE = "empty message.";

    private static final String UNKNOWN_MESSAGE = "unknown message.";

    private static void dispatchContent(int tag, @Nullable Object object) {
        if (object == null) {
            output(tag, EMPTY_MESSAGE);
        } else {
            final String message;

            if (object instanceof Exception) {
                message = ((Exception) object).getMessage();
            } else if (object instanceof String) {
                message = String.valueOf(object);
            } else {
                message = null;
            }

            output(tag, message);
        }
    }

    private static void output(int tag, @Nullable String message) {
        if (TextUtils.isEmpty(message)) {
            Log.e(LOGCAT_TAG, UNKNOWN_MESSAGE);
        } else {
            switch (tag) {
                case Log.ERROR:
                    Log.e(LOGCAT_TAG, message);
                    break;
                case Log.INFO:
                    Log.i(LOGCAT_TAG, message);
                    break;
                case Log.DEBUG:
                    Log.d(LOGCAT_TAG, message);
                    break;
                case Log.WARN:
                    Log.w(LOGCAT_TAG, message);
                    break;
                default:
                    Log.wtf(LOGCAT_TAG, message);
                    break;
            }
        }
    }

    public static void error(@Nullable Object message) {
        dispatchContent(Log.ERROR, message);
    }

    public static void info(@Nullable Object message) {
        dispatchContent(Log.INFO, message);
    }

    public static void debug(@Nullable Object message) {
        dispatchContent(Log.DEBUG, message);
    }

    public static void warn(@Nullable Object message) {
        dispatchContent(Log.WARN, message);
    }

}
