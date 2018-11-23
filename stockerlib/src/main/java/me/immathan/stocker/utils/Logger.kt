package me.immathan.stocker.utils

import android.util.Log

/**
 * @author Mathan on 19/05/18
 */
object Logger {

    /**
     * Default log tag which can be used to filter the android logs.
     */
    private const val TAG = "STOCKER"

    fun i(tag: String, msg: String) {
        Log.i(getTag(tag), msg)
    }

    fun d(tag: String, msg: String) {
        Log.v(getTag(tag), msg)
    }

    fun d(tag: String, msg: String, throwable: Throwable) {
        Log.v(getTag(tag), "[ERROR] " + msg + "\nError: " + throwable.localizedMessage)
    }

    fun e(tag: String, msg: String) {
        Log.e(getTag(tag), msg)
    }

    fun e(tag: String, msg: String, e: Exception) {
        Log.e(getTag(tag), msg + "\nException: " + e.message)
        e.printStackTrace()
    }

    private fun getTag(tag: String): String {
        return "$TAG[${tag.substring(0..4)}]"
    }

}