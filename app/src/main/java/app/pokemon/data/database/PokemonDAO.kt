package app.pokemon.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.pokemon.data.database.entities.PokemonEntity

@Dao
interface PokemonDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemons: List<PokemonEntity>)

    @Query("SELECT(SELECT COUNT(*) FROM pokemon_table) !=0")
    fun doesPokemonEntityExist(): Boolean

}