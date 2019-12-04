package application.model

import java.math.BigInteger

class DSA(
        private val P: BigInteger,
        private val Q: BigInteger,
        M: List<Byte>
) {

    lateinit var G: BigInteger
    lateinit var Y: BigInteger
    lateinit var R: BigInteger
    lateinit var S: BigInteger
    var hash: BigInteger

    lateinit var X: BigInteger
    lateinit var K: BigInteger
    lateinit var h: BigInteger

    lateinit var W: BigInteger
    lateinit var U1: BigInteger
    lateinit var U2: BigInteger
    lateinit var V: BigInteger


    init{
        checkQ()
        checkP()
        hash = calculateHash(M, 100.toBigInteger(), Q)
    }

    fun sign(X: BigInteger, K: BigInteger, h: BigInteger){
        this.h = h
        this.X = X
        this.K = K
        checkX()
        checkK()
        calculateG()
        calculateY()
        calculateR()
        calculateS()
        checkRS()
    }

    fun checkSign(R: BigInteger, S: BigInteger, G: BigInteger, Y: BigInteger): Boolean {
        W = S.modPow(Q - TWO, Q)
        U1 = (W * hash).mod(Q)
        U2 = (R * W).mod(Q)
        V = (G.modPow(U1, P) * Y.modPow(U2, P)).mod(P).mod(Q)
        return V == R
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