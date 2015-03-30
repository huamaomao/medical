package com.android.common.util;

/**
 *
 *
 * @author hua
 *
 */
public final class Log {

    /**
     * isPrint: print switch, true will print. false not print
     */
    public static  boolean isPrint    = true;
    private static String  defaultTag = "Android_Log";

    private Log() {}

    public static void setTag(String tag) {
        defaultTag = tag;
    }

    public static int i(Object o) {
        return isPrint && o != null ? android.util.Log.i(defaultTag, o.toString()) : -1;
    }

    public static int i(String m) {
        return isPrint && m != null ? android.util.Log.i(defaultTag, m) : -1;
    }

    /**
     * ******************** Log **************************
     */
    public static int v(String tag, String msg) {
        return isPrint && msg != null ? android.util.Log.v(tag, msg) : -1;
    }

    public static int d(String tag, String msg) {
        return isPrint && msg != null ? android.util.Log.d(tag, msg) : -1;
    }
    public static int d(Object msg) {
        return isPrint && msg != null ? android.util.Log.d(defaultTag, msg.toString()) : -1;
    }

    public static int i(String tag, String msg) {
        return isPrint && msg != null ? android.util.Log.i(tag, msg) : -1;
    }

    public static int w(String tag, String msg) {
        return isPrint && msg != null ? android.util.Log.w(tag, msg) : -1;
    }

    public static int e(String tag, String msg) {
        return isPrint && msg != null ? android.util.Log.e(tag, msg) : -1;
    }

    /**
     * ******************** Log with Throwable **************************
     */
    public static int v(String tag, String msg, Throwable tr) {
        return isPrint && msg != null ? android.util.Log.v(tag, msg, tr) : -1;
    }

    public static int d(String tag, String msg, Throwable tr) {
        return isPrint && msg != null ? android.util.Log.d(tag, msg, tr) : -1;
    }

    public static int i(String tag, String msg, Throwable tr) {
        return isPrint && msg != null ? android.util.Log.i(tag, msg, tr) : -1;
    }

    public static int w(String tag, String msg, Throwable tr) {
        return isPrint && msg != null ? android.util.Log.w(tag, msg, tr) : -1;
    }

    public static int e(String tag, String msg, Throwable tr) {
        return isPrint && msg != null ? android.util.Log.e(tag, msg, tr) : -1;
    }

    /**
     * ******************** TAG use Object Tag **************************
     */
    public static int v(Object tag, String msg) {
        return isPrint ? android.util.Log.v(tag.getClass().getSimpleName(), msg) : -1;
    }

    public static int d(Object tag, String msg) {
        return isPrint ? android.util.Log.d(tag.getClass().getSimpleName(), msg) : -1;
    }

    public static int i(Object tag, String msg) {
        return isPrint ? android.util.Log.i(tag.getClass().getSimpleName(), msg) : -1;
    }

    public static int w(Object tag, String msg) {
        return isPrint ? android.util.Log.w(tag.getClass().getSimpleName(), msg) : -1;
    }

    public static int e(Object tag, String msg) {
        return isPrint ? android.util.Log.e(tag.getClass().getSimpleName(), msg) : -1;
    }
}