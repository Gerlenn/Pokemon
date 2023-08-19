package app.pokemon.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import app.pokemon.R
import app.pokemon.databinding.PokemonItemBinding
import app.pokemon.presentation.adapter.listener.PokemonListener
import app.pokemon.presentation.model.Pokemon
import app.pokemon.utils.AppConstants.SYMBOL
import com.squareup.picasso.Picasso

class PokemonViewHolder(
    private val viewBinding: PokemonItemBinding,
    private var pokemonListener: PokemonListener,
) : RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(pokemon: Pokemon) {
        viewBinding.pokemonName.text = pokemon.name
        viewBinding.pokemonID.text = "$SYMBOL${pokemon.id}"

        Picasso.get()
            .load(pokemon.spriteUrl)
            .resize(300, 300)
            .placeholder(R.drawable.loadimage)
            .into(viewBinding.pokemonImage)

        itemView.setOnClickListener {
            pokemonListener.onPokemonSelected(pokemon.id)
        }
    }
}