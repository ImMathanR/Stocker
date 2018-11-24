package me.immathan.stockersample

import android.app.Application
import com.codemonkeylabs.fpslibrary.TinyDancer

/**
 * @author Mathan on 24/11/18
 */
class StockerApp : Application() {

    override fun onCreate() {
        super.onCreate()

        //alternatively
        TinyDancer.create()
                .redFlagPercentage(.1f) // set red indicator for 10%....different from default
                .startingXPosition(600)
                .startingYPosition(600)
                .show(applicationContext)

    }

}