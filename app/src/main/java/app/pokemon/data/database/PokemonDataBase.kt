package app.pokemon.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.pokemon.data.database.entities.PokemonEntity

@Database(entities = [PokemonEntity::class],
    version = 1,
    exportSchema = false)
abstract class PokemonDataBase : RoomDatabase() {

    abstract fun getPokemonDAO(): PokemonDAO

    companion object {
        private const val DATABASE_NAME = "pokemon_database"
        private var DATABASE_INSTANCE: PokemonDataBase? = null

        fun getPokemonDatabaseInstance(context: Context): PokemonDataBase {
            return DATABASE_INSTANCE ?: Room
                .databaseBuilder(
                    context.applicationContext,
                    PokemonDataBase::class.java,
                    DATABASE_NAME
                )
                .build()
                .also { DATABASE_INSTANCE = it }
        }
    }
}