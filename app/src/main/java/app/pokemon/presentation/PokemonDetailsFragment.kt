package app.pokemon.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import app.pokemon.R
import app.pokemon.databinding.FragmentPokemonDetailsBinding
import app.pokemon.utils.AppConstants.HEIGHT
import app.pokemon.utils.AppConstants.POKEMON_HEIGHT
import app.pokemon.utils.AppConstants.POKEMON_ID
import app.pokemon.utils.AppConstants.POKEMON_NAME
import app.pokemon.utils.AppConstants.POKEMON_TYPES
import app.pokemon.utils.AppConstants.POKEMON_WEIGHT
import app.pokemon.utils.AppConstants.WEIGHT
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetailsFragment : Fragment() {

    private val viewModel: PokemonDetailsViewModel by viewModels()

    private var _viewBinding: FragmentPokemonDetailsBinding? = null
    private val viewBinding get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _viewBinding = FragmentPokemonDetailsBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        bundle?.let { safeBundle ->
            val id = safeBundle.getInt(POKEMON_ID)
            id.let { pokemonId ->
                viewModel.getPokemonDetails(pokemonId)
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMsg ->
            Toast.makeText(context, getString(errorMsg), Toast.LENGTH_SHORT).show()
        }

        viewModel.pokemonDetails.observe(viewLifecycleOwner) { pokemonDetails ->
            viewBinding.name.text = POKEMON_NAME
            viewBinding.pokemonName.text = pokemonDetails.name
            viewBinding.weight.text = POKEMON_WEIGHT
            viewBinding.pokemonWeight.text = "${pokemonDetails.weight / 10} $WEIGHT"
            viewBinding.height.text = POKEMON_HEIGHT
            viewBinding.pokemonHeight.text = "${pokemonDetails.height * 10} $HEIGHT"
            viewBinding.types.text = POKEMON_TYPES
            viewBinding.pokemonTypes.text = pokemonDetails.types

            Picasso.get()
                .load(pokemonDetails.imageUrl)
                .resize(300, 300)
                .placeholder(R.drawable.loadimage)
                .into(viewBinding.pokemonImage)

            viewBinding.backButton.visibility = View.VISIBLE
        }

        viewBinding.backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}