package me.immathan.stocker.cache

import android.content.Context
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

/**
 * Just a mock Disk cache class that can be implemented if needed
 * @author Mathan on 23/11/18
 */
class StockerDiskCache<Key: Any, Value: Any> private constructor
/**
 * Size in Mega Bytes
 */(val context: Context, var size: Int = 0) : Cache<Key, Value> {

    override fun get(key: Key): Deferred<Value?> {
        return GlobalScope.async {
            return@async null
        }
    }

    override fun set(key: Key, value: Value): Deferred<Unit> {
        return GlobalScope.async {

        }
    }

    override fun evict(key: Key): Deferred<Unit> {
        return GlobalScope.async<Unit> {  }
    }

    override fun evictAll(): Deferred<Unit> {
        return GlobalScope.async<Unit> {  }
    }

    override fun sizeOf(): Int {
        return 0
    }

    override fun remainingSize(): Int {
        return 0
    }

    override fun name(): String {
        return DISK_CACHE
    }

    companion object {
        /**
         * Returns [StockerDiskCache] instance with the size passed
         */
        fun getInstance(context: Context, size: Int) = StockerDiskCache<Any, Any>(context, size)

        /**
         * Returns [StockerDiskCache] instance with the best size calculated
         */
        fun getInstance(context: Context) = StockerDiskCache<String, Any>(context)

        private const val DISK_CACHE = "Disk cache"

    }

}