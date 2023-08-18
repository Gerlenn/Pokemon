package app.pokemon.domain

import app.pokemon.presentation.model.Pokemon
import javax.inject.Inject

class PokemonInteractor @Inject constructor(
    private val pokemonRepository: PokemonRepository,
) {
    suspend fun getPokemonListFromNetwork(offset: Int, limit: Int): List<Pokemon> {
        return pokemonRepository.getPokemonListFromNetwork(offset, limit)
    }

    suspend fun getPokemonListFromDatabase(): List<Pokemon> {
        return pokemonRepository.getPokemonListFromDatabase()
    }
}