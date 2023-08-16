package app.pokemon.domain

import app.pokemon.presentation.model.Pokemon
import javax.inject.Inject

class PokemonInteractor @Inject constructor(
    private val pokemonRepository: PokemonRepository,
) {
    suspend fun getPokemonList(offset: Int, limit: Int): List<Pokemon> {
        return pokemonRepository.getPokemonList(offset, limit)
    }
}