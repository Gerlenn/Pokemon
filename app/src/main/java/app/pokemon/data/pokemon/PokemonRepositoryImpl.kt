package app.pokemon.data.pokemon

import app.pokemon.data.database.PokemonDAO
import app.pokemon.data.database.entities.PokemonEntity
import app.pokemon.data.model.PokemonDetailsResponse
import app.pokemon.data.service.ApiService
import app.pokemon.domain.PokemonRepository
import app.pokemon.presentation.model.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val pokemonDAO: PokemonDAO,
) : PokemonRepository {
    override suspend fun getPokemonListFromNetwork(offset: Int, limit: Int): List<Pokemon> {
        return withContext(Dispatchers.IO) {

            val response = apiService.getPokemonList(offset * limit, limit)
            val listPokemon = response.body()?.results
            if (!listPokemon.isNullOrEmpty()) {
                val pokemonList = listPokemon.map { pokemon ->
                    val pokemonId = getPokemonIdFromUrl(pokemon.url)
                    val pokemonDetails = getPokemonDetails(pokemonId)
                    val spriteUrl = pokemonDetails.sprites.front_default
                    Pokemon(
                        pokemonId,
                        pokemon.name,
                        pokemon.url,
                        spriteUrl
                    )
                }

                val pokemonEntities = pokemonList.map { pokemon ->
                    PokemonEntity(
                        pokemon.id,
                        pokemon.name,
                        pokemon.url,
                        pokemon.spriteUrl
                    )
                }
                pokemonDAO.insertAll(pokemonEntities)

                pokemonList
            } else {
                emptyList()
            }
        }
    }

    override suspend fun getPokemonDetails(pokemonId: Int): PokemonDetailsResponse {
        return withContext(Dispatchers.IO) {
            val response = apiService.getPokemonDetails(pokemonId)
            response.body() ?: throw Exception("Failed to fetch Pokemon details.")
        }
    }

    //getting pokemon id from url
    private fun getPokemonIdFromUrl(url: String): Int {
        val segments = url.split("/")
        return segments[segments.size - 2].toInt()
    }

    override suspend fun getPokemonListFromDatabase(): List<Pokemon> {
        return withContext(Dispatchers.IO) {
            val pokemonEntities = pokemonDAO.getAllPokemons()
            val pokemonListFromDB = pokemonEntities.map { pokemonEntity ->
                Pokemon(
                    pokemonEntity.id,
                    pokemonEntity.name,
                    pokemonEntity.url,
                    pokemonEntity.spriteUrl
                )
            }
            pokemonListFromDB
        }
    }
}