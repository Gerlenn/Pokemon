package app.pokemon.domain

import app.pokemon.data.model.PokemonDetailsResponse
import app.pokemon.presentation.model.Pokemon

interface PokemonRepository {
    suspend fun getPokemonListFromNetwork(offset: Int, limit: Int): List<Pokemon>

    suspend fun getPokemonDetails(pokemonId: Int): PokemonDetailsResponse

    suspend fun getPokemonListFromDatabase(): List<Pokemon>
}