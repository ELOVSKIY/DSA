package application

import application.model.DSA
import application.model.calculateHash

val P = 643.toBigInteger()
val Q = 107.toBigInteger()

fun main(){
    val value = mutableListOf<Byte>(2, 4, 21,10, 18)
    val dsa = DSA(P, Q, value)
    dsa.sign(45.toBigInteger(), 31.toBigInteger(), 2.toBigInteger())
    println(dsa.checkSign(47.toBigInteger(), 97.toBigInteger(), 357.toBigInteger(), 60.toBigInteger()))
}