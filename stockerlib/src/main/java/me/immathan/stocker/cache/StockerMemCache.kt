package me.immathan.stocker.cache

import android.content.Context
import android.util.LruCache
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import me.immathan.stocker.utils.StockerUtil


/**
 * @author Mathan on 22/11/18
 */
class StockerMemCache<Key: Any, Value: Any> private constructor
/**
 * Size in Mega Bytes
 */(val context: Context, var size: Int = 0) : Cache<Key, Value> {

    private val lruCache: LruCache<Key, Value>

    init {
        if(size == 0) {
            // Size not configured so calculating the best side based on the memory available
            size = StockerUtil.getOptimalSize(context)
        }
        lruCache = object : LruCache<Key, Value>(size) {
            override fun sizeOf(key: Key, value: Value): Int {
                if(value is ByteArray) {
                    val result = value as ByteArray
                    return result.size
                } else if(value is String) {
                    val result = value as String
                    return result.toByteArray().size
                }
                return 0
            }
        }
    }

    override fun get(key: Key): Deferred<Value?> {
        return GlobalScope.async {
            lruCache[key]
        }
    }

    override fun set(key: Key, value: Value): Deferred<Unit> {
        return GlobalScope.async<Unit> {
            lruCache.put(key, value)
        }
    }

    override fun evict(key: Key): Deferred<Unit> {
        return GlobalScope.async<Unit> {
            lruCache.remove(key)
        }
    }

    override fun evictAll(): Deferred<Unit> {
        return GlobalScope.async {
            lruCache.evictAll()
        }
    }

    override fun sizeOf(): Int {
        return lruCache.maxSize()
    }

    override fun remainingSize(): Int {
        return lruCache.maxSize() - lruCache.size()
    }

    override fun name(): String {
        return MEM_CACHE
    }

    companion object {
        /**
         * Returns StockerMemCache instance with the size passed
         */
        fun getInstance(context: Context, size: Int) = StockerMemCache<Any, Any>(context, size)

        /**
         * Returns StockerMemCache instance with the best size calculated
         */
        fun getInstance(context: Context) = StockerMemCache<String, Any>(context)

        private const val MEM_CACHE = "Memory cache"

    }

}