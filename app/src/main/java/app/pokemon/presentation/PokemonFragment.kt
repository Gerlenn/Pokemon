package app.pokemon.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.pokemon.databinding.FragmentPokemonBinding
import app.pokemon.presentation.adapter.PokemonAdapter
import app.pokemon.presentation.adapter.listener.PokemonListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonFragment : Fragment(), PokemonListener {

    private val viewModel: PokemonViewModel by viewModels()

    private var _viewBinding: FragmentPokemonBinding? = null
    private val viewBinding get() = _viewBinding!!

    private lateinit var pokemonAdapter: PokemonAdapter
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _viewBinding = FragmentPokemonBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pokemonAdapter = PokemonAdapter(this)
        val recyclerView = viewBinding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = pokemonAdapter

        viewModel.pokemonList.observe(viewLifecycleOwner) { listPokemon ->
            isLoading = false
            pokemonAdapter.submitList(listPokemon)
        }

        viewModel.selectedPokemonName.observe(viewLifecycleOwner) { pokemonName ->
            Toast.makeText(requireContext(), "pokemon selected: $pokemonName.", Toast.LENGTH_SHORT)
                .show()
        }
        viewModel.loadNextPage()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && visibleItemCount + firstVisibleItemPosition >=
                    totalItemCount && firstVisibleItemPosition >= 0
                ) {
                    isLoading = true
                    viewModel.loadNextPage()
                }
            }
        })
    }

    override fun onPokemonSelected(pokemonId: Int) {
        viewModel.setSelectedPokemonName(pokemonId + 1)
    }
}