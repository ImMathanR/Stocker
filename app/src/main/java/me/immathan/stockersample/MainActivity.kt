package me.immathan.stockersample

import android.animation.Animator
import android.arch.lifecycle.Observer
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.view.ViewAnimationUtils
import kotlinx.android.synthetic.main.activity_main.*
import me.immathan.stocker.Stocker
import me.immathan.stocker.cache.CacheSettings
import me.immathan.stocker.cache.CacheStrategy
import me.immathan.stockersample.adapter.PinsListAdapter
import me.immathan.stockersample.utils.ItemOffsetDecoration


class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName!!
    }

    private val appComponent by lazy {
        (application as StockerApp).appComponent
    }

    private val pinsViewModel by lazy {
        appComponent.getPinsViewModel(this, appComponent.getFetchPinsUseCas(appComponent.apiInterface))
    }

    private var pinsAdapter : PinsListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val stocker = Stocker.Builder(applicationContext)
                .cacheSettings(object: CacheSettings {
                    override fun getCacheStrategy(): CacheStrategy {
                        return CacheStrategy.MEM_CACHE
                    }
                })
                .build()

        pinsAdapter = PinsListAdapter(stocker)

        pinsRV.setHasFixedSize(true)
        pinsRV.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        val itemDecoration = ItemOffsetDecoration(applicationContext, R.dimen.rv_offset)
        pinsRV.addItemDecoration(itemDecoration)

        pinsRV.adapter = pinsAdapter

        registerViewModel()
        pinsViewModel.getPins()
    }

    private fun registerViewModel() {
        pinsViewModel.pinsLoadLiveData.observe(this, Observer {
            hideLoader {
                pinsAdapter!!.pinsList = it
                pinsAdapter!!.notifyDataSetChanged()
            }
        })
        pinsViewModel.pinsErrorLiveData.observe(this, Observer {

        })
        pinsViewModel.loadingLiveData.observe(this, Observer {

        })
    }

    private fun hideLoader(callback: Callback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val startRadius = 0
            val endRadius = Math.hypot(parentView.width.toDouble(), parentView.height.toDouble()).toInt()
            val anim = ViewAnimationUtils.createCircularReveal(loader, ((loader.x + loader.width / 2).toInt()), ((loader.y + loader.height / 2).toInt()), startRadius.toFloat(), endRadius.toFloat())
            anim.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(p0: Animator?) {

                }

                override fun onAnimationEnd(p0: Animator?) {
                    loader.visibility = View.GONE
                    pinsRV.visibility = View.VISIBLE
                    callback()
                }

                override fun onAnimationStart(p0: Animator?) {

                }

                override fun onAnimationCancel(p0: Animator?) {

                }
            })
            anim.start()
        } else {
            loader.visibility = View.GONE
            pinsRV.visibility = View.VISIBLE
        }

    }
}

typealias Callback = () -> Unit
