fun main() {
    /**
     * List
     */
    // immutable list
    val currencyImmutableList = listOf("원", "달러", "유로")
    println("# currencyImmutableList: $currencyImmutableList")
    // currencyList.add() // immutable collection 에 add 는 불가능

    // mutable list
    val currencyMutableList = mutableListOf<String>().apply {
        add("원")
        add("달러")
        add("유로")
        add("크크")
    }
    println("# currencyMutableList: $currencyMutableList")


    /**
     * Set
     */
    // immutable set
    val mutableSet = setOf(1, 1, 2, 3)
    println("# mutableSet: $mutableSet")

    // mutable set
    val immutableSet = mutableSetOf<Int>().apply {
        add(1)
        add(1)
        add(2)
        add(3)
        add(4)
        add(5)
    }
    println("# immutableSet: $immutableSet")


    /**
     * Map
     */
    // immutable map
    val immutableMapOf = mapOf("key1" to "value1", "key2" to "value2")
    println("# immutableMapOf: $immutableMapOf")

    // mutable map
    val mutableMap = mutableMapOf<String, String>().apply {
        put("key1", "value1")
        put("key2", "value2")
    }
    mutableMap["key3"] = "value3"
    mutableMap["key4"] = "value4"
    println("# mutableMap: $mutableMap")


    /**
     * collection builder
     */
    val buildList: List<Int> = buildList {
        add(1)
        add(2)
    }
    println("# buildList: $buildList")
}