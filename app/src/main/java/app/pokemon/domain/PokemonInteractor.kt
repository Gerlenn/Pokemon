package app.pokemon.domain

import app.pokemon.presentation.model.Pokemon
import app.pokemon.presentation.model.PokemonDetails
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

    suspend fun getPokemonDetails(pokemonId: Int): PokemonDetails {
        return pokemonRepository.getPokemonDetails(pokemonId)
    }

    suspend fun getPokemonDetailsFromDatabase(pokemonId: Int): PokemonDetails {
        return pokemonRepository.getPokemonDetailsFromDatabase(pokemonId)
    }
}