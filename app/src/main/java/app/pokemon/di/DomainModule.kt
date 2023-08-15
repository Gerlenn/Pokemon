package app.pokemon.di

import app.pokemon.domain.PokemonInteractor
import app.pokemon.domain.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun providePokemonInteractor(
        pokemonRepository: PokemonRepository,
    ): PokemonInteractor {
        return PokemonInteractor(pokemonRepository)
    }
}