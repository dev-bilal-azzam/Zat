package com.devbilal.data.hash

import org.mindrot.jbcrypt.BCrypt

actual class HasherImpl actual constructor() : Hasher {
    actual override fun hash(plain: String): String {
        return BCrypt.hashpw(plain, BCrypt.gensalt())
    }

    actual override fun verify(plain: String, hashed: String): Boolean {
        return BCrypt.checkpw(plain, hashed)
    }

}

actual fun getHasherInstance(): HasherImpl = HasherImpl()