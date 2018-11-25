package me.immathan.stockersample

import android.app.Application
import com.codemonkeylabs.fpslibrary.TinyDancer
import me.immathan.stockersample.di.*

/**
 * @author Mathan on 24/11/18
 */
class StockerApp : Application() {

    /**
     * Simple Implementation to achieve Dependency injection without using a dedicated library like
     * [Dagger].
     */
    val appComponent: AppComponent by lazy {
        object : AppComponent,
                AppModule by AppModuleImpl(this),
                PinsListActivityModule by PinsListActivityModuleImpl() {}
    }

    override fun onCreate() {
        super.onCreate()

        //alternatively
        TinyDancer.create()
                .redFlagPercentage(.1f) // set red indicator for 10%....different from default
                .startingXPosition(600)
                .startingYPosition(600)
                //.show(applicationContext)
    }

}