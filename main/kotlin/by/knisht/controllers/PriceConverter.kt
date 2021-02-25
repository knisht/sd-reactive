package by.knisht.controllers

import by.knisht.database.User.Companion.Currency

class PriceConverter {

    companion object {
        private val multipliers : Map<Currency, Double> = mapOf(
            Currency.DOLLAR to 1.0,
            Currency.EURO to 0.82,
            Currency.ROUBLE to 74.07
        )


        fun convertPrices(from : Currency, to : Currency, value : Double) : Double =
            value / multipliers[from]!! * multipliers[to]!!
    }
}