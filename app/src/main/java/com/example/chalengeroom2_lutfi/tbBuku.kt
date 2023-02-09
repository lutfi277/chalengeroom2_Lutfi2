package com.example.chalengeroom2_lutfi

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class tbBuku (
    @PrimaryKey
    val id_buku: Int,
    val kategori: String,
    val judul: String,
    val pengarang: String,
    val penerbit: String,
    val jml_buku: String
)