package io.github.henriquemcc.dduc.util

import java.security.MessageDigest

fun String.toSha224(): String {
    // 1. Create a instance of MessageDigest with the algorithm SHA-224
    val messageDigest = MessageDigest.getInstance("SHA-224")

    // 2. Converts the String to bytes and generates the hash
    val digest = messageDigest.digest(this.toByteArray())

    // 3. Converts the array from bytes to a Hexadecimal String
    return digest.joinToString("") { "%02x".format(it) }
}