package app.pokemon.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import app.pokemon.domain.PokemonInteractor
import app.pokemon.presentation.model.Pokemon

class PokemonPagingSource(private val pokemonInteractor: PokemonInteractor) :
    PagingSource<Int, Pokemon>() {
    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        return try {
            val nextPage = params.key ?: 0
            val itemsPerPage = 20

            val listPokemonsFromDatabase = pokemonInteractor.getPokemonListFromDatabase()
            val shouldLoadFromNetwork =
                listPokemonsFromDatabase.isEmpty() || listPokemonsFromDatabase.size < (nextPage + 1) * itemsPerPage

            val listPokemons = if (shouldLoadFromNetwork) {
                pokemonInteractor.getPokemonListFromNetwork(nextPage, itemsPerPage)
            } else {
                listPokemonsFromDatabase.subList(nextPage * itemsPerPage,
                    minOf((nextPage + 1) * itemsPerPage, listPokemonsFromDatabase.size))
            }

            LoadResult.Page(
                data = listPokemons,
                prevKey = if (nextPage == 0) null else nextPage - 1,
                nextKey = if (listPokemons.isEmpty()) null else nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
