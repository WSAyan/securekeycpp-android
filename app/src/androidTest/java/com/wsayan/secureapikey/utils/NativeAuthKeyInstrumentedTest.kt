package com.wsayan.secureapikey.utils

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class NativeAuthKeyInstrumentedTest {
    @Test
    fun nativeAuthKeyShouldReturnNonEmptyString() {
        val key = NativeAuthKey.generateAuthKey()
        assertNotNull("Native auth key should not be null", key)
        assertTrue("Native auth key should not be empty", key.isNotEmpty())
        println("Generated native auth key: $key")
    }

    @Test
    fun nativeAuthKeyPerformanceUnderLoad() {
        val numberOfCalls = 1000
        val executionTimes = mutableListOf<Long>()

        for (i in 0 until numberOfCalls) {
            val startTime = System.nanoTime()
            NativeAuthKey.generateAuthKey()
            val endTime = System.nanoTime()
            executionTimes.add(endTime - startTime)
        }

        assertEquals("All generateAuthKey calls should complete", numberOfCalls, executionTimes.size)

        val averageTime = executionTimes.average()
        println("Average execution time per call (ns): $averageTime")

        assertTrue(
            "Average execution time should be reasonable",
            averageTime < TimeUnit.MILLISECONDS.toNanos(10)
        )
    }
}