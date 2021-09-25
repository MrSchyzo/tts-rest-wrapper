package it.mrschyzo.domain.identity

interface KeyGenerator<in IN, OUT> where OUT : Comparable<OUT> {
    fun generateFrom(input: IN): OUT
}