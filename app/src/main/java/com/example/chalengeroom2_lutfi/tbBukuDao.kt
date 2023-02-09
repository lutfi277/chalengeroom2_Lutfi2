package com.example.chalengeroom2_lutfi

import androidx.room.*

@Dao
interface tbBukuDao {
    @Insert
    suspend fun addtbbuku(tbbuk: tbBuku)

    @Update
    suspend fun updatetbbuku(tbbuk: tbBuku)

    @Delete
    suspend fun deletetbbuku(tbbuk: tbBuku)

    @Query("SELECT * FROM tbBuku")
    suspend fun tampilsemua(): List<tbBuku>

    @Query("SELECT * FROM tbBuku WHERE id_buku=:buku_id")
    fun tampilid(buku_id: Int): List<tbBuku>
}