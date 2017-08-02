package sboishtyan.spek_vs_jukito.counter

inline fun assert(vararg params: Pair<String, Any>, unit: () -> Unit) {
    try {
        unit.invoke()
    } catch (e: AssertionError) {
        throw AssertionError("params info: ${params.joinToString { "${it.first} = ${it.second}" }}", e)
    }
}

