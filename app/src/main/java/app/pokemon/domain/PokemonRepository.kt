package app.pokemon.domain

import app.pokemon.presentation.model.Pokemon
import app.pokemon.presentation.model.PokemonDetails

interface PokemonRepository {
    suspend fun getPokemonListFromNetwork(offset: Int, limit: Int): List<Pokemon>

    suspend fun getPokemonDetails(pokemonId: Int): PokemonDetails

    suspend fun getPokemonListFromDatabase(): List<Pokemon>

    suspend fun getPokemonDetailsFromDatabase(pokemonId: Int): PokemonDetails
}