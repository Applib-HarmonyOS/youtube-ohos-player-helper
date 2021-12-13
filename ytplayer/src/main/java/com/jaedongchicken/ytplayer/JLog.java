package com.jaedongchicken.ytplayer;

import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class JLog {
    private static final String TAG_LOG = "JLOG";

    private static final int DOMAIN_ID = 0xD000F00;

    private static final HiLogLabel LABEL_LOG = new HiLogLabel(3, DOMAIN_ID, JLog.TAG_LOG);

    private static final String LOG_FORMAT = "%{public}s: %{public}s";

    private static boolean DEBUG = false;

    public static void setDEBUG(boolean DEBUG) {
        JLog.DEBUG = DEBUG;
    }

    private JLog() {
        /* Do nothing */
    }

    /**
     * Print debug log
     *
     * @param tag log tag
     * @param msg log message
     */
    public static void d(String tag, String msg) {
        if (DEBUG) {
            HiLog.debug(LABEL_LOG, LOG_FORMAT, tag, msg);
        }
    }

    /**
     * Print error log
     *
     * @param tag log tag
     * @param msg log message
     */
    public static void e(String tag, String msg) {
        if (DEBUG) {
            HiLog.error(LABEL_LOG, LOG_FORMAT, tag, msg);
        }
    }

    /**
     * Print info log
     *
     * @param tag log tag
     * @param msg log message
     */
    public static void i(String tag, String msg) {
        if (DEBUG) {
            HiLog.info(LABEL_LOG, LOG_FORMAT, tag, msg);
        }
    }

    /**
     * Print warn log
     *
     * @param tag log tag
     * @param msg log message
     */
    public static void w(String tag, String msg) {
        if (DEBUG) {
            HiLog.warn(LABEL_LOG, LOG_FORMAT, tag, msg);
        }
    }

    /**
     * Print fatal log
     *
     * @param tag log tag
     * @param msg log message
     */
    public static void f(String tag, String msg) {
        if (DEBUG) {
            HiLog.fatal(LABEL_LOG, LOG_FORMAT, tag, msg);
        }
    }


    private static final String TAG = "JD";


    /**
     * Print debug log
     *
     * @param msg log message
     */
    public static void d(String msg) {
        if (DEBUG) {
            HiLog.debug(LABEL_LOG, LOG_FORMAT, TAG, msg);
        }
    }

    /**
     * Print error log
     *
     * @param msg log message
     */
    public static void e(String msg) {
        if (DEBUG) {
            HiLog.error(LABEL_LOG, LOG_FORMAT, TAG, msg);
        }
    }

    /**
     * Print info log
     *
     * @param msg log message
     */
    public static void i(String msg) {
        if (DEBUG) {
            HiLog.info(LABEL_LOG, LOG_FORMAT, TAG, msg);
        }
    }

    /**
     * Print warn log
     *
     * @param msg log message
     */
    public static void w(String msg) {
        if (DEBUG) {
            HiLog.warn(LABEL_LOG, LOG_FORMAT, TAG, msg);
        }
    }

    /**
     * Print fatal log
     *
     * @param msg log message
     */
    public static void f(String msg) {
        if (DEBUG) {
            HiLog.fatal(LABEL_LOG, LOG_FORMAT, TAG, msg);
        }
    }

}
