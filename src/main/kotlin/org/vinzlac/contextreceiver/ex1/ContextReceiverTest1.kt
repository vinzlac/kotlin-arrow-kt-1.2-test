package org.vinzlac.contextreceiver.ex1

interface Logger {
    fun log(
        name: String,
        params: Map<String, String> = emptyMap()
    )
}

class MyLogger: Logger {
    override fun log(name: String, params: Map<String, String>) {
        println("$name with $params")
    }

}
interface ParamProvider {
    val providedParams: Map<String, String>
}

context(ParamProvider)
fun Logger.logWithParams(name: String) {
    log(name = name, params = providedParams)
}

class TestClass (private val logger: Logger) : ParamProvider {
    override val providedParams: Map<String, String> = mapOf("key1" to "value1", "key2" to "value2")

    fun action1() = logger.logWithParams("action1")
}

fun main(){
    val logger = MyLogger()
    val testClass = TestClass(logger)
    testClass.action1()
}