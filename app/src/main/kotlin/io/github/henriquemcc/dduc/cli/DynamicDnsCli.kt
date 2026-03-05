package io.github.henriquemcc.dduc.cli

interface DynamicDnsCli {
    fun add(args: Array<String>)
    fun alter(args: Array<String>)
    fun delete(args: Array<String>)
    fun help(args: Array<String>)
    fun testAuth(args: Array<String>)
    fun forceClean(args: Array<String>)
    fun forceUpdate(args: Array<String>)
    fun getType(): String
}