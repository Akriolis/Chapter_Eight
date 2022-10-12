import java.io.BufferedReader
import java.io.FileReader
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

fun List <SiteVisit>.averageDuration(os: OS) =
    filter { it.os == os}.map (SiteVisit::duration).average()

fun List<SiteVisit>.averageDurationFor(predicate: (SiteVisit) -> Boolean) =
    filter(predicate).map(SiteVisit::duration).average()

/**
 * Inline functions
 */

/**
 * Using the use function
 */

fun readFirstLineFromFile(path: String): String{
    BufferedReader(FileReader(path)).use { br -> return br.readLine() }
}

/**
 * Control flow in higher-order functions
 */

fun lookForAlice(people: List<Person2>){
    for (person in people){
        if (person.name == "Alice"){
            println("Found!")
            return
        }
    }
    println("Alice is not found")
}

fun lookForAlice2(people: List<Person2>){
    people.forEach {
        if (it.name == "Alice"){
            println("Found!")
            return
        }
    }
    println("Not found!")
}

/**
 * if you use the return keyword in a lambda, it returns from the function
 * in which you called the lambda, not just from the lambda itself
 * it is called a non-local return
 *
 * returning from lambdas (return with a label)
 *
 */

fun lookForAlice3 (people: List<Person2>){
    people.forEach label@{
        if (it.name == "Alice") return@label
    }
    println("Alice might be somewhere")
}

fun lookForAlice4 (people: List<Person2>){
    people.forEach {
        if (it.name == "Alice") return@forEach
    }
    println("Alice might be somewhere")
}

/**
 * Anonymous functions
 */

fun lookForAlice5 (people: List<Person2>){
    people.forEach (fun (person){
        if (person.name == "Alice") return
        println("${person.name} is not Alice")
    })
}

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

    val calculator =
        getShippingCostCalculator(Delivery.EXPEDITED)
    println("Shipping costs ${calculator(Order(3))}")


    val contacts = listOf(Person("Dmitry", "Jemerov", "123-4567"),
                                    Person("Svetlana", "Isakova", null))

    val contactListFilters = ContactListFilters()
    with (contactListFilters){
        prefix = "Dm"
        onlyWithPhoneNumber = true
    }

    println(contacts.filter(contactListFilters.getPredicate()))

    val averageWindowsDuratin = log
        .filter{ it.os == OS.WINDOWS}
        .map (SiteVisit::duration)
        .average()

    println(averageWindowsDuratin)

    println(log.averageDuration(OS.WINDOWS))
    println(log.averageDuration(OS.MAC))

    val averageMobileDuration = log
        .filter { it.os in setOf(OS.IOS, OS.ANDROID)}
        .map(SiteVisit::duration)
        .average()

    println(averageMobileDuration)

    println(log.averageDurationFor { it.os in setOf(OS.ANDROID, OS.IOS) })

    println(log.averageDurationFor { it.os == OS.IOS && it.path == "/signup" })

    val people2 = listOf<Person2>(Person2("Alice", 29), Person2("Bob", 31))

    lookForAlice(people2)
    lookForAlice2(people2)
    lookForAlice3(people2)
    lookForAlice4(people2)
    lookForAlice5(people2)

    val testAnonymous = people2.filter (fun(person): Boolean{
        return person.age < 30
    })
    println(testAnonymous)

}
