package com.example.bursdagapp385499.repositories

import com.example.bursdagapp385499.data.Person
import com.example.bursdagapp385499.data.PersonDao
import kotlinx.coroutines.flow.Flow

class PersonRepository(private val dao: PersonDao) {
    //tar inn 1 parameter - et dataaccessobjekt av typen PersonDao
    val allPersons: Flow<List<Person>> = dao.getAllPersons()
    //vi lager en liste som er dao sin getAllPersons
    suspend fun insert(person: Person) {
        dao.insertPerson(person)
    }
    suspend fun delete(person: Person) {
        dao.deletePerson(person)

    }
    suspend fun update(person: Person) {
        dao.updatePerson(person)
    }

}