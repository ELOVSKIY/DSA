package application.model

import java.math.BigInteger

class DSA(
        private val P: BigInteger,
        private val Q: BigInteger
) {

    private lateinit var G: BigInteger
    private lateinit var Y: BigInteger
    private lateinit var R: BigInteger
    private lateinit var S: BigInteger
    private lateinit var hash: BigInteger

    private lateinit var X: BigInteger
    private lateinit var K: BigInteger
    private lateinit var h: BigInteger

    init{
        checkQ()
        checkP()
    }

    fun sign(M: MutableList<Byte>, X: BigInteger, K: BigInteger, h: BigInteger){
        this.h = h
        this.X = X
        this.K = K
        checkX()
        checkK()
        hash = calculateHash(M, 100.toBigInteger(), Q)
        calculateG()
        calculateY()
        calculateR()
        calculateS()
        checkRS()
    }

    fun checkSign(M: List<Byte>, R: BigInteger, S: BigInteger, G: BigInteger, Y: BigInteger): Boolean {
        val W = S.modPow(Q - TWO, Q)
        hash = calculateHash(M, 100.toBigInteger(), Q)
        val u1 = (W * hash).mod(Q)
        val u2 = (R * W).mod(Q)
        val v = (G.modPow(u1, P) * Y.modPow(u2, P)).mod(P).mod(Q)
        return v == R
    }

    private fun checkX(){
        if ((X <= ONE ) || (X >= Q))
            throw Exception("X is not in the diapason (0, Q)")
    }

    private fun checkK(){
        if ((K <= ONE ) || (K >= Q))
            throw Exception("K is not in the diapason (0, Q)")
    }

    private fun checkQ(){
        if (!checkPrimary(Q))
            throw Exception("Q is not primary.")
    }

    private fun checkP(){
        val  T = P - ONE
        if (!checkPrimary(P))
            throw Exception("P is not primary.")
        if (T % Q != ZERO)
            throw Exception("Q is not divider of (P-1)")
    }

    private fun checkRS(){
        if ((R == ZERO) || (S == ZERO)){
            throw Exception("S and R must be not equals 0, change K and try again.")
        }
    }

    private fun calculateG(){
        val exp = (P - ONE) / Q
        val G =  hash.modPow(exp, P)
        if (G < ONE){
            throw Exception("H value is not correct, G less than 1")
        }
        this.G = G
    }

    private fun calculateY(){
        Y =  G.modPow(X, P)
    }

    private fun calculateR(){
        R =  G.modPow(K, P).mod(Q)
    }

    private fun calculateS() {
        val K1 = K.modPow(Q - TWO, Q)
        val sum = hash + X * R
        S =  (K1 * sum).mod(Q)
    }

}