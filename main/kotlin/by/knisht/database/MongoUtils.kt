package by.knisht.database

import com.mongodb.rx.client.MongoClients

private val mongoClient = MongoClients.create("mongodb://localhost")

internal val database = mongoClient.getDatabase("shop")

