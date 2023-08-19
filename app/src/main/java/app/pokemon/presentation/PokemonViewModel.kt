package app.pokemon.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import app.pokemon.R
import app.pokemon.data.paging.PokemonPagingSource
import app.pokemon.domain.PokemonInteractor
import app.pokemon.presentation.model.Pokemon
import app.pokemon.utils.NavigateWithId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val pokemonInteractor: PokemonInteractor,
) : ViewModel() {

    private val _navigateWithID = MutableLiveData<NavigateWithId?>()
    val navigateWithID: LiveData<NavigateWithId?> = _navigateWithID

    val pagingData: Flow<PagingData<Pokemon>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { PokemonPagingSource(pokemonInteractor) }
    ).flow.cachedIn(viewModelScope)

    fun pokemonSelected() {
        _navigateWithID.value = null
    }

    fun setSelectedPokemonId(pokemonId: Int) {
        _navigateWithID.value = NavigateWithId(
            R.id.pokemonDetailsFragment, pokemonId)
    }
}