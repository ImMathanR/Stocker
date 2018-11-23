package me.immathan.stocker.utils

import android.app.ActivityManager
import android.content.Context

/**
 * @author Mathan on 23/11/18
 */
class StockerUtil {

    companion object {

        @JvmStatic
        fun getOptimalSize(context: Context): Int {
            val am = context.getSystemService(
                    Context.ACTIVITY_SERVICE) as ActivityManager
            val maxKb = am.memoryClass * 1024
            return maxKb / 8 // 1/8th of total ram
        }

    }

}