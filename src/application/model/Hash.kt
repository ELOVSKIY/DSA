package application.model

import java.math.BigInteger

fun calculateHash(M: List<Byte>, H0: BigInteger, Q: BigInteger): BigInteger{
    var hash = H0
    for (m in M){
        hash = (hash + m.toBigInteger()).modPow(TWO, Q)
    }
    return hash
}