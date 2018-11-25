# Stocker

![stocker-image](https://images.unsplash.com/photo-1518378782190-56da4f3efde6?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=3977b6e56df57f2f1b4019622c6b9a94&auto=format&fit=crop&w=2689&q=80)

Light weight Downloading library which manages its own Memory cache. 


### Stocker instance
```kotlin
val stocker = Stocker.Builder(applicationContext)
                .build()
```
### Download request
```kotlin
stocker.fetch(ASSET_URL_AS_STRING) {(body, status, error) ->    
  if(status) {
    // Url downloaded
  }
}
```
## To cancel the request
```kotlin
// Stocker fetch will return a Request object. Just pass it onto the Stocker.cancel() method. 
val request = stocker.fetch(ASSET_URL_AS_STRING) {(body, status, error) ->    
  if(status) {
    // Url downloaded
  }
}
stocker.cancel(request) // To cancel the request
```
### Supports Image Binding
```kotlin
Stocker.bind(IMAGE_URL_AS_STRING, imageView)
```
It will download and render the image asset on the ImageView

### Stocker Advantage
Stocker manages Memory caching by default with the best fit Memory size for your device. If you want to change it you can
do it the following way
```kotlin
val stocker = Stocker.Builder(applicationContext)
                .cacheSettings(object: CacheSettings {
                    override fun getCacheStrategy(): CacheStrategy {
                        return CacheStrategy.MEM_CACHE
                    }

                    override fun getMemCacheSize(): Int {
                        return 10 * 1024 // 10MB
                    }
                })
                .build()
```

### No Cache
Stocker can also be configured without any caching
```kotlin
val stocker = Stocker.Builder(applicationContext)
                .cacheSettings(object: CacheSettings {
                    override fun getCacheStrategy(): CacheStrategy {
                        return CacheStrategy.NO_CACHE
                    }
                })
                .build()
```

### TODO
* Unit Testing
* Optimizing Image binding
