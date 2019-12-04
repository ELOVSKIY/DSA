package application.model

import java.math.BigInteger

val ONE = 1.toBigInteger()
val ZERO = 0.toBigInteger()
val TWO = 2.toBigInteger()

fun Byte.toBigInteger(): BigInteger{
    return this.toInt().toBigInteger()
}

fun BigInteger.byteCount(): Int{
    return this.toByteArray().size
}


fun checkPrimary(X: BigInteger): Boolean{
    return X.isProbablePrime(16)
}
