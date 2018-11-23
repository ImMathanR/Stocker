package me.immathan.stocker.cache

import me.immathan.stocker.cache.CacheStrategy

/**
 * @author Mathan on 23/11/18
 */
interface CacheSettings {

    fun getCacheStrategy() : CacheStrategy = CacheStrategy.MEM_CACHE

    fun getMemCacheSize() : Int = 0

    fun getDiskCacheSize() : Int = 0

}