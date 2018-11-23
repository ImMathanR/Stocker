package me.immathan.stocker.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.immathan.stocker.utils.Logger
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * @author Mathan on 23/11/18
 */
class Downloader {

    private val client = OkHttpClient()

    companion object {
        private val TAG = Downloader::class.java.simpleName!!
    }

    suspend fun download(url: String) : ByteArray? = withContext(Dispatchers.IO) {
        Logger.d(TAG, "Downloader: ${Thread.currentThread().name}")
        val request = Request.Builder().url(url)
                .build()
        val response = client.newCall(request).execute()
        if(response.isSuccessful && response.body() != null) {
            val inputStream = response.body()!!.byteStream()
            val reader = BufferedReader(InputStreamReader(inputStream))
            val line = reader.readLine()
            var result = line
            reader.forEachLine {
                result += line
            }
            response.body()!!.close()
            return@withContext result.toByteArray()
        }
        return@withContext null
    }

}