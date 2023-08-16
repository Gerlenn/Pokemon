package app.pokemon.data.pokemon

import app.pokemon.data.service.ApiService
import app.pokemon.domain.PokemonRepository
import app.pokemon.presentation.model.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : PokemonRepository {

    override suspend fun getPokemonList(offset: Int, limit: Int): List<Pokemon> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getPokemonList(offset * limit, limit)
            response.body()?.results.let { listPokemon ->
                listPokemon?.map { pokemon ->
                    Pokemon(
                        pokemon.name,
                        pokemon.url
                    )
                }
            } ?: emptyList()
        }
    }
}