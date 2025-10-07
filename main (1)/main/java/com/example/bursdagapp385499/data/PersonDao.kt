package com.example.bursdagapp385499.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(person: Person)

    @Query("SELECT * FROM person_table ORDER BY birthday DESC")
    fun getAllPersons(): Flow<List<Person>>

    @Delete
    suspend fun deletePerson(person: Person)

    @Update
    suspend fun updatePerson(person: Person)




}