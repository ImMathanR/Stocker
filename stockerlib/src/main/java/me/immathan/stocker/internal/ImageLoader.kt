package me.immathan.stocker.internal

import android.graphics.BitmapFactory
import android.widget.ImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.immathan.stocker.AssetManager
import me.immathan.stocker.CacheListener
import me.immathan.stocker.utils.Logger
import java.util.concurrent.ConcurrentHashMap

/**
 * @author Mathan on 25/11/18
 */
class ImageLoader(private val assetManager: AssetManager) {

    private val imageLoadingMap = ConcurrentHashMap<ImageView, String>()

    private val urlCacheListenerMap = ConcurrentHashMap<String, Request>()

    companion object {
        private val TAG = ImageLoader::class.java.simpleName!!
    }

    fun bind(url : String, imageView: ImageView) {
        val urlOnTheMap = imageLoadingMap[imageView]
        if(urlOnTheMap != null && url.isNotEmpty()) { // Checking whether there is an image binded
            // request on this ImageView
            val urlRequest = urlCacheListenerMap[url]
            if(urlRequest != null) { // Cancelling the request to free up and bind the new request
                assetManager.cancel(urlRequest)
            }
            imageLoadingMap.remove(imageView)
            urlCacheListenerMap.remove(urlOnTheMap)
        }

        // Adding the new request into the memory
        val cacheListener = getCacheListener(url, imageView)
        imageLoadingMap[imageView] = url
        urlCacheListenerMap[url] = Request(url, cacheListener)
        assetManager.fetch(url, cacheListener)
    }

    private fun getCacheListener(url: String, imageView: ImageView): CacheListener {
        return { _, status, response ->
            val responseObject : Response
            responseObject = if(response != null) {
                Response(status, response as ByteArray?)
            } else {
                val error = StockerError("Download failed")
                Response(false, error = error)
            }

            val responseArray = responseObject.responseBody
            if(responseArray != null) {
                val bitmap = BitmapFactory.decodeByteArray(responseArray, 0, responseArray.size)
                GlobalScope.launch(Dispatchers.Main) {
                    imageView.setImageBitmap(bitmap)
                }
            } else {
                Logger.d(TAG, "Response body null")
            }
            imageLoadingMap.remove(imageView)
            urlCacheListenerMap.remove(url)
        }
    }

}