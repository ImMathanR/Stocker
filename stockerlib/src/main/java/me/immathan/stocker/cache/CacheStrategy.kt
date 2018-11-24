package me.immathan.stocker.cache

/**
 * Caching strategy for the [Stocker]
 *
 * @author Mathan on 23/11/18
 */
enum class CacheStrategy {

    /**
     * No caching strategy will not be applied. It will always get download it from the internet
     */
    NO_CACHE,

    /**
     * Caching the assets in the memory based on the Cache size provided
     */
    MEM_CACHE,

    /**
     * To cache the resources on the disk and it will also cache it on the Memory
     * TODO: Not yet implemented
     */
    DISK_CACHE

}