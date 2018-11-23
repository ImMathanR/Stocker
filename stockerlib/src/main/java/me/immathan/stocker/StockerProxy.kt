package me.immathan.stocker

import me.immathan.stocker.internal.Result

/**
 * Proxy used to hide all the details of the implementation of the [Stocker] using [StockerImpl]
 *
 * @author Mathan on 23/11/18
 */
interface StockerProxy {

    fun fetch(url: String, responseHandler: ResponseHandler)

}

typealias ResponseHandler = (Result) -> Unit