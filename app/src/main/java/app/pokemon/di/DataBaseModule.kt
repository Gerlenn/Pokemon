package app.pokemon.di

import android.content.Context
import app.pokemon.data.database.PokemonDAO
import app.pokemon.data.database.PokemonDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    fun pokemonDataBase(context: Context): PokemonDataBase {
        return PokemonDataBase.getPokemonDatabaseInstance(context)
    }

    @Provides
    fun providePokemonDAO(itemsDataBase: PokemonDataBase): PokemonDAO {
        return itemsDataBase.getPokemonDAO()
    }
}