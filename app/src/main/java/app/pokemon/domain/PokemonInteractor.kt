package app.pokemon.domain

import app.pokemon.presentation.model.Pokemon
import javax.inject.Inject

class PokemonInteractor @Inject constructor(
    private val pokemonRepository: PokemonRepository,
) {
    suspend fun getPokemonList(offset: Int, limit: Int): List<Pokemon> {
        val listPokemon = pokemonRepository.getPokemonList(offset, limit)

        return listPokemon.map { pokemon ->
            val pokemonDetails =
                pokemonRepository.getPokemonDetails(getPokemonIdFromUrl(pokemon.url))
            val spriteUrl = pokemonDetails.sprites.front_default
            pokemon.copy(spriteUrl = spriteUrl)
        }
    }

    private fun getPokemonIdFromUrl(url: String): Int {
        val segments = url.split("/")
        return segments[segments.size - 2].toInt()
    }
}