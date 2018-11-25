package me.immathan.stocker

import android.widget.ImageView
import me.immathan.stocker.internal.Request
import me.immathan.stocker.internal.Result

/**
 * Proxy used to hide all the details of the implementation of the [Stocker] using [StockerImpl]
 *
 * @author Mathan on 23/11/18
 */
interface StockerProxy {

    fun fetch(url: String, responseHandler: ResponseHandler): Request

    fun cancel(request: Request)

    fun bind(url: String, imageVIew: ImageView)

}

/**
 * Higher order function to use as a callback for downloading events
 */
typealias ResponseHandler = (Result) -> Unit