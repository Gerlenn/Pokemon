package app.pokemon.data.pokemon

import app.pokemon.data.database.PokemonDAO
import app.pokemon.data.database.entities.PokemonDetailsEntity
import app.pokemon.data.database.entities.PokemonEntity
import app.pokemon.data.service.ApiService
import app.pokemon.domain.PokemonRepository
import app.pokemon.presentation.model.Pokemon
import app.pokemon.presentation.model.PokemonDetails
import app.pokemon.presentation.model.TypeName
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
                    val spriteUrl = pokemonDetails.imageUrl
                    Pokemon(
                        pokemonId,
                        pokemon.name,
                        pokemon.url,
                        spriteUrl,
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

    override suspend fun getPokemonDetails(pokemonId: Int): PokemonDetails {
        return withContext(Dispatchers.IO) {
            val response = apiService.getPokemonDetails(pokemonId)
            val details = response.body()?.let { pokemon ->
                val type = pokemon.types.joinToString(", ") { it.type.name }
                PokemonDetails(
                    pokemon.id,
                    pokemon.name,
                    pokemon.weight,
                    pokemon.height,
                    type,
                    pokemon.sprites.other.official_artwork.front_default
                )
            }
            details?.let {pokemon ->
                val entity = PokemonDetailsEntity(
                    pokemonId,
                    pokemon.name,
                    pokemon.weight,
                    pokemon.height,
                    pokemon.types,
                    pokemon.imageUrl
                )
                pokemonDAO.insertDetailsById(entity)
            }

            details ?: throw IllegalStateException("Pokemon details not found")
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

    override suspend fun getPokemonDetailsFromDatabase(pokemonId: Int): PokemonDetails {
        return withContext(Dispatchers.IO) {
            val pokemonDetailsEntity = pokemonDAO.getDetailsById(pokemonId)
            pokemonDetailsEntity?.let {
                PokemonDetails(
                    it.id,
                    it.name,
                    it.weight,
                    it.height,
                    it.types,
                    it.imageUrl
                )
            } ?: throw IllegalStateException("Pokemon details not found in the database")
        }
    }
}