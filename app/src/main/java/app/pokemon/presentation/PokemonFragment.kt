package app.pokemon.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import app.pokemon.databinding.FragmentPokemonBinding
import app.pokemon.presentation.adapter.PokemonAdapter
import app.pokemon.presentation.adapter.listener.PokemonListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonFragment : Fragment(), PokemonListener {

    private val viewModel: PokemonViewModel by viewModels()

    private var _viewBinding: FragmentPokemonBinding? = null
    private val viewBinding get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _viewBinding = FragmentPokemonBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = viewBinding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        val pagingAdapter = PokemonAdapter(this)
        recyclerView.adapter = pagingAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pagingData.collect { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }

        viewModel.selectedPokemonId.observe(viewLifecycleOwner) { pokemonId ->
            Toast.makeText(requireContext(), "PokemonID: $pokemonId", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPokemonSelected(pokemonId: Int) {
        viewModel.setSelectedPokemonId(pokemonId)
    }
}