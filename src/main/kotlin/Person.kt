data class Person (
    val firstName: String,
    val lastName: String,
    val phoneNumber: String?
        )

class ContactListFilters{
    var prefix: String = ""
    var onlyWithPhoneNumber: Boolean = false

    fun getPredicate(): (Person) -> Boolean{
        val startWithPrefix = {p: Person ->
            p.firstName.startsWith(prefix) || p.lastName.startsWith(prefix)
        }
        if (!onlyWithPhoneNumber){
            return startWithPrefix
        }
        return { startWithPrefix (it) && it.phoneNumber != null}
    }


}
