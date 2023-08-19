package app.pokemon.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import app.pokemon.presentation.model.Type

@Entity(tableName = "pokemon_details")
data class PokemonDetailsEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val weight: Double,
    val height: Int,
    val types: String,
    val imageUrl: String,
)
