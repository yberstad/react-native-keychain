package com.oblador.keychain

import android.os.SystemClock
import android.util.Log
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.WritableMap

/**
 * Wrap any Promise so that every resolve/reject logs elapsed ms under the given tag.
 *
 * @param methodName A short name for the JS-exposed method you’re timing.
 * @param tag        Android log tag (defaults to "PromiseLogger").
 */
fun Promise.timed(
    methodName: String,
    tag: String = "PromiseLogger"
): Promise {
    val startMs = SystemClock.elapsedRealtime()
    Log.d(tag, "$methodName ▶ start")

    return object : Promise {
        override fun resolve(value: Any?) {
            val elapsed = SystemClock.elapsedRealtime() - startMs
            Log.d(tag, "$methodName ✔ resolved in ${elapsed}ms")
            this@timed.resolve(value)
        }

        override fun reject(code: String, message: String?) {
            val elapsed = SystemClock.elapsedRealtime() - startMs
            Log.d(tag, "$methodName ✗ reject($code) in ${elapsed}ms: $message")
            this@timed.reject(code, message)
        }

        override fun reject(code: String, throwable: Throwable?) {
            val elapsed = SystemClock.elapsedRealtime() - startMs
            Log.d(tag, "$methodName ✗ reject($code) in ${elapsed}ms", throwable)
            this@timed.reject(code, throwable)
        }

        override fun reject(code: String, message: String?, throwable: Throwable?) {
            val elapsed = SystemClock.elapsedRealtime() - startMs
            Log.d(tag, "$methodName ✗ reject($code) in ${elapsed}ms: $message", throwable)
            this@timed.reject(code, message, throwable)
        }

        override fun reject(throwable: Throwable) {
            val elapsed = SystemClock.elapsedRealtime() - startMs
            Log.d(tag, "$methodName ✗ reject(throwable) in ${elapsed}ms", throwable)
            this@timed.reject(throwable)
        }

        override fun reject(throwable: Throwable, userInfo: WritableMap) {
            val elapsed = SystemClock.elapsedRealtime() - startMs
            Log.d(tag, "$methodName ✗ reject(throwable, userInfo) in ${elapsed}ms", throwable)
            this@timed.reject(throwable, userInfo)
        }

        override fun reject(code: String, userInfo: WritableMap) {
            val elapsed = SystemClock.elapsedRealtime() - startMs
            Log.d(tag, "$methodName ✗ reject($code, userInfo) in ${elapsed}ms")
            this@timed.reject(code, userInfo)
        }

        override fun reject(code: String, throwable: Throwable?, userInfo: WritableMap) {
            val elapsed = SystemClock.elapsedRealtime() - startMs
            Log.d(tag, "$methodName ✗ reject($code, throwable, userInfo) in ${elapsed}ms", throwable)
            this@timed.reject(code, throwable, userInfo)
        }

        override fun reject(code: String, message: String?, userInfo: WritableMap) {
            val elapsed = SystemClock.elapsedRealtime() - startMs
            Log.d(tag, "$methodName ✗ reject($code, message, userInfo) in ${elapsed}ms: $message")
            this@timed.reject(code, message, userInfo)
        }

        override fun reject(
            code: String?,
            message: String?,
            throwable: Throwable?,
            userInfo: WritableMap?
        ) {
            val elapsed = SystemClock.elapsedRealtime() - startMs
            Log.d(tag, "$methodName ✗ reject(code=$code, message=$message, userInfo, throwable) in ${elapsed}ms", throwable)
            this@timed.reject(code, message, throwable, userInfo)
        }

        override fun reject(message: String) {
            val elapsed = SystemClock.elapsedRealtime() - startMs
            Log.d(tag, "$methodName ✗ reject(message) in ${elapsed}ms: $message")
            this@timed.reject(message)
        }
    }
}




/**
 * Measure how long [block] takes to run, and log the elapsed ms.
 *
 * @param tag Android log tag.
 * @param name A short name for the operation.
 * @param block The suspendable work to time.
 */
suspend fun <T> timeIt(
    tag: String = "CoroutineLogger",
    name: String,
    block: suspend () -> T
): T {
    val start = SystemClock.elapsedRealtime()
    try {
        return block()
    } finally {
        val elapsed = SystemClock.elapsedRealtime() - start
        Log.d(tag, "$name ▶ completed in ${elapsed}ms")
    }
}

fun <T> timeItNoSuspend(
    tag: String = "CoroutineLogger",
    name: String,
    block: () -> T
): T {
    val start = SystemClock.elapsedRealtime()
    try {
        return block()
    } finally {
        val elapsed = SystemClock.elapsedRealtime() - start
        Log.d(tag, "$name ▶ completed in ${elapsed}ms")
    }
}
