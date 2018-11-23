package me.immathan.stocker.cache

import kotlinx.coroutines.Deferred

/**
 * @author Mathan on 22/11/18
 */
interface Cache<Key: Any, Value : Any> {

    fun get(key: Key) : Deferred<Value?>

    fun set(key: Key, value: Value) : Deferred<Unit>

    fun evict(key: Key) : Deferred<Unit>

    fun evictAll() : Deferred<Unit>

    fun sizeOf(): Int

    fun remainingSize() : Int

    fun name() : String

}