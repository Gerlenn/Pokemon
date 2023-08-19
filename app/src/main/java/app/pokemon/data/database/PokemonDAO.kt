package app.pokemon.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.pokemon.data.database.entities.PokemonDetailsEntity
import app.pokemon.data.database.entities.PokemonEntity

@Dao
interface PokemonDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(pokemons: List<PokemonEntity>)

    @Query("SELECT * FROM pokemon_table")
    suspend fun getAllPokemons(): List<PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailsById(pokemonDetails: PokemonDetailsEntity)

    @Query("SELECT * FROM pokemon_details WHERE id = :pokemonId")
    suspend fun getDetailsById(pokemonId: Int): PokemonDetailsEntity?
}