package app.pokemon.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import app.pokemon.databinding.PokemonItemBinding
import app.pokemon.presentation.adapter.listener.PokemonListener
import app.pokemon.presentation.model.Pokemon
import com.squareup.picasso.Picasso

class PokemonViewHolder(
    private val viewBinding: PokemonItemBinding,
    private var pokemonListener: PokemonListener,
) : RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(pokemon: Pokemon) {
        viewBinding.pokemonName.text = pokemon.name

        Picasso.get()
            .load(pokemon.spriteUrl)
            .resize(300, 300)
            .into(viewBinding.pokemonImage)

        itemView.setOnClickListener {
            pokemonListener.onPokemonSelected(adapterPosition)
        }
    }
}