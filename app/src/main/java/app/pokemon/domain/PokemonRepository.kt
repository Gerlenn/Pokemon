package app.pokemon.domain

import app.pokemon.data.model.PokemonDetailsResponse
import app.pokemon.presentation.model.Pokemon

interface PokemonRepository {

    suspend fun getPokemonList(offset: Int, limit: Int): List<Pokemon>

    suspend fun getPokemonDetails(id: Int): PokemonDetailsResponse

}