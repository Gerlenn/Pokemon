package app.pokemon.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import app.pokemon.databinding.PokemonItemBinding
import app.pokemon.presentation.adapter.listener.PokemonListener
import app.pokemon.presentation.model.Pokemon

class PokemonViewHolder(
    private val viewBinding: PokemonItemBinding,
    private var pokemonListener: PokemonListener,
) : RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(pokemon: Pokemon) {
        viewBinding.pokemonName.text = pokemon.name

        itemView.setOnClickListener {
            pokemonListener.onPokemonSelected(position)
        }
    }
}