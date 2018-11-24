package me.immathan.stockersample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import me.immathan.stocker.Stocker
import me.immathan.stocker.cache.CacheSettings
import me.immathan.stocker.cache.CacheStrategy
import me.immathan.stocker.utils.Logger

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val stocker = Stocker.Builder(this)
                .cacheSettings(object: CacheSettings {
                    override fun getCacheStrategy(): CacheStrategy {
                        return CacheStrategy.MEM_CACHE
                    }
                })
                .build()

        button.setOnClickListener {
            val request1 = stocker.fetch("http://lab.greedygame.com/mathan-dev/skype.png") {
                val (body, status, error) = it
                Logger.d(TAG, "Fetched 1 $status")
            }
            stocker.cancel(request1)
            Logger.d(TAG, "Request created 1")
            val request2 = stocker.fetch("http://lab.greedygame.com/mathan-dev/skype.png") {
                val (body, status, error) = it
                Logger.d(TAG, "Fetched 2 $status")
            }
            Logger.d(TAG, "Cancel request")
            val request3 = stocker.fetch("http://lab.greedygame.com/mathan-dev/workspace/temp/sdk_init.html") {
                val (body, status, error) = it
                Logger.d(TAG, "Fetched 3 $status")
            }
            val request4 = stocker.fetch("http://lab.greedygame.com/mathan-dev/workspace/mediation/templates/stroke.json") {
                val (body, status, error) = it
                Logger.d(TAG, "Fetched 4 $status")
            }
        }
    }
}
