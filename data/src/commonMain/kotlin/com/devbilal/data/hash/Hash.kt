package com.devbilal.data.hash

interface Hasher {
    fun hash(plain: String): String
    fun verify(plain: String, hashed: String): Boolean
}

expect class HasherImpl() : Hasher {
    /**
     * Take a plain password and return a hash
     * Ready for storage
     */
    override fun hash(plain: String): String

    /**
     * Verify if a plain matches a hashed
     */
    override fun verify(plain: String, hashed: String): Boolean
}

expect fun getHasherInstance(): HasherImpl