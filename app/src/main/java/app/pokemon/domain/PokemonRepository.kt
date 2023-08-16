package app.pokemon.domain

import app.pokemon.presentation.model.Pokemon

interface PokemonRepository {

    suspend fun getPokemonList(offset: Int, limit: Int): List<Pokemon>
}