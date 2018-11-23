package me.immathan.stockersample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import me.immathan.stocker.Stocker
import me.immathan.stocker.cache.CacheSettings
import me.immathan.stocker.cache.CacheStrategy
import me.immathan.stocker.utils.Logger
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            val stocker = Stocker.Builder(this)
                    .cacheSettings(object: CacheSettings {
                        override fun getCacheStrategy(): CacheStrategy {
                            Logger.d(TAG, "Cache strategy: ${Thread.currentThread().name}")
                            return CacheStrategy.MEM_CACHE
                        }
                    })
                    .build()
            thread {
                stocker.fetch("http://lab.greedygame.com/mathan-dev/skype.png") {
                    Logger.d(TAG, "Fetch callback: ${Thread.currentThread().name}")
                    val (body, status, error) = it
                    Log.d(TAG, "Fetched 1 $status")
                }
            }
            thread {
                stocker.fetch("http://lab.greedygame.com/mathan-dev/skype.png") {
                    val (body, status, error) = it
                    Log.d(TAG, "Fetched 2 $status")
                }
            }
            thread {
                stocker.fetch("http://lab.greedygame.com/mathan-dev/workspace/temp/sdk_init.html") {
                    val (body, status, error) = it
                    Log.d(TAG, "Fetched 3 $status")
                }
            }
            thread {
                stocker.fetch("http://lab.greedygame.com/mathan-dev/workspace/mediation/templates/stroke.json") {
                    val (body, status, error) = it
                    Log.d(TAG, "Fetched 4 $status")
                }
            }
        }
    }
}
