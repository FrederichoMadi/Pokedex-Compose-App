package com.fredericho.pokedex.data.pokemon.implementation.database.model

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_tbl")
data class PokemonModel(
    @PrimaryKey
    @ColumnInfo("name")
    val name: String,

    @ColumnInfo("url")
    val url: String
)
