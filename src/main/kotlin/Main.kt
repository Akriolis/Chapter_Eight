import java.util.function.BiPredicate

/**
 * Higher-order functions:
 * lambdas as parameters and return values
 */



fun testingLambda (x: Int, y: Int, myLambda: (Int, Int) -> Int): Int{
    return myLambda (x, y)
}

fun performRequest(
    url: String,
    callback: (code: Int, content: String) -> Unit
){}

/**
 * Calling functions passed as arguments
 */

fun twoAndThree (operation: (Int, Int) -> Int){
    val result = operation (2,3)
    println("The result is $result")
}

fun String.filter(predicate: (Char) -> Boolean): String{
    val sb = StringBuilder()
    for (index in 0 until length){
        val element = get(index)
        if (predicate(element)) sb.append(element)
    }
    return sb.toString()
}

fun foo(callback: (() -> Unit)?){
    if (callback != null){
        callback()
    }
}

fun <T> Collection<T>.joinToString(
    separator: String = ", ",
    prefix: String = "",
    postfix: String = "",
    transform: ((T) -> String)? = null
): String{
    val result = StringBuilder(prefix)
    for((index, element) in this.withIndex()){
        if (index > 0) result.append(separator)
        val str = transform?.invoke(element)
            ?: element.toString()
        result.append(str)
    }
    result.append(postfix)
    return result.toString()

}

/**
 * Returning functions from functions
 */

fun main() {
    val sum = { x: Int, y: Int -> x + y }
    val sum2: (Int, Int) -> Int = { x, y -> x + y }
    val action = { println("Hello world!") }
    val action2: () -> Unit = { println(42) }
    println(testingLambda(10, 15) { x, y -> x * y })
    println(sum(2, 5))
    action.invoke()

    var canReturnNull: (Int) -> Int? = { null }

    var funOrNull: ((Int, Int) -> Int)? = null

    var url = "http://kotl.in"
    performRequest(url) { code, content -> }
    performRequest(url, { code, page -> })

    twoAndThree(sum)
    twoAndThree { a, b -> a + b }
    twoAndThree { biba, boba -> biba * boba }

    println("ab1c".filter { it in 'a'..'z' })



}
