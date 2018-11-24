package me.immathan.stocker.internal

import me.immathan.stocker.CacheListener

/**
 * @author Mathan on 23/11/18
 */
data class Request(val url: String, val cacheListener: CacheListener)