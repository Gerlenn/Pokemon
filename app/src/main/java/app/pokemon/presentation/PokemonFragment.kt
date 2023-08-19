package app.pokemon.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import app.pokemon.databinding.FragmentPokemonBinding
import app.pokemon.presentation.adapter.PokemonAdapter
import app.pokemon.presentation.adapter.listener.PokemonListener
import app.pokemon.utils.AppConstants.POKEMON_ID
import app.pokemon.utils.InternetConnection
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PokemonFragment : Fragment(), PokemonListener {

    private val viewModel: PokemonViewModel by viewModels()

    @Inject
    lateinit var internetConnection: InternetConnection

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
            val hasInternetConnection = internetConnection.isOnline()
            val hasDataInDatabase = viewModel.hasDataInDatabase()
            if (hasInternetConnection || hasDataInDatabase) {
                viewBinding.recyclerView.visibility = View.VISIBLE
                viewBinding.noConnect.visibility = View.GONE

                viewModel.pagingData.collect { pagingData ->
                    pagingAdapter.submitData(pagingData)
                }
            } else {
                viewBinding.recyclerView.visibility = View.GONE
                viewBinding.noConnect.visibility = View.VISIBLE
            }
        }

        viewModel.navigateWithID.observe(viewLifecycleOwner) { navWithBundle ->
            if (navWithBundle != null) {
                val bundle = Bundle()
                bundle.putInt(POKEMON_ID, navWithBundle.id)
                findNavController().navigate(navWithBundle.destinationId, bundle)
                viewModel.pokemonSelected()
            }
        }
    }

    override fun onPokemonSelected(pokemonId: Int) {
        viewModel.setSelectedPokemonId(pokemonId)
    }
}