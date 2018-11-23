package me.immathan.stocker.internal

/**
 * @author Mathan on 23/11/18
 */
class Result(private val response: Response) {

    operator fun component1(): ByteArray? {
        return response.responseBody
    }

    operator fun component2() : Boolean {
        return response.status
    }

    operator fun component3(): StockerError? {
        return response.error
    }

}