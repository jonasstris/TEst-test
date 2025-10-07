package com.example.bursdagapp385499.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "person_table")
data class Person(
    @PrimaryKey val phoneNumber: Int, // unik telefonnummer
    val name: String?,
    val birthday: LocalDate
)


