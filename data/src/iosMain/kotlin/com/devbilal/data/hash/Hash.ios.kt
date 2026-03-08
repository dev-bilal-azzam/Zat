@file:OptIn(ExperimentalForeignApi::class)

package com.devbilal.data.hash

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.convert
import kotlinx.cinterop.refTo
import platform.CoreCrypto.CC_SHA256

actual class HasherImpl actual constructor() : Hasher {
    private val salt = "Zat_SECRET_SALT"


    actual override fun hash(plain: String): String {
        return sha256(salt + plain)
    }

    actual override fun verify(plain: String, hashed: String): Boolean {
        return sha256(salt + plain) == hashed
    }


    private fun sha256(input: String): String {
        val data = input.encodeToByteArray()
        val hash = UByteArray(32) // SHA-256
        CC_SHA256(data.refTo(0), data.size.convert(), hash.refTo(0))

        return hash.joinToString("") { it.toByte().toHex() }
    }

    private fun Byte.toHex(): String {
        val hexChars = "0123456789abcdef"
        val v = this.toInt() and 0xFF
        return "${hexChars[v.ushr(4)]}${hexChars[v and 0x0F]}"
    }

}

actual fun getHasherInstance(): HasherImpl = HasherImpl()