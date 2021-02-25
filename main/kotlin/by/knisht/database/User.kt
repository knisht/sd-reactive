package by.knisht.database

import com.mongodb.rx.client.Success
import org.bson.Document
import rx.Observable

data class User(val id: Int, val name: String, val currency: Currency) {

    companion object {
        enum class Currency {
            EURO,
            DOLLAR,
            ROUBLE
        }

        private const val ID = "_id"
        private const val NAME = "name"
        private const val CURRENCY = "currency"

        fun persist(user: User): Observable<Success> {
            val newUser = Document(ID, user.id).append(NAME, user.name).append(CURRENCY, user.currency.toString())
            return database.getCollection("users").insertOne(newUser).apply { subscribe() }
        }

        fun getAll(): Observable<User> {
            return database.getCollection("users").find().toObservable()
                .map { User(it[ID] as Int, it[NAME] as String, Currency.valueOf(it[CURRENCY] as String)) }
        }

        fun findById(id : Int) : Observable<User> {
            return database.getCollection("users").find(Document(ID, id)).toObservable().map(::convertToUser)
        }

        private fun convertToUser(doc : Document) : User = User(doc[ID] as Int, doc[NAME] as String, Currency.valueOf(doc[CURRENCY] as String))
    }
}
