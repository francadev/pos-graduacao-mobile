package dev.rafaelfranca.marcadortruco

import android.graphics.Typeface
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import dev.rafaelfranca.marcadortruco.databinding.ItemHistoricoBinding

class HistoricoAdapter (
    private val listaJogadas: List<String>
) : RecyclerView.Adapter<HistoricoAdapter.HistoricoViewHolder>() {

    class HistoricoViewHolder(val binding: ItemHistoricoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricoViewHolder {
        val binding = ItemHistoricoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HistoricoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoricoViewHolder, position: Int) {
        val jogada = listaJogadas[position]
        holder.binding.txtDescricaoJogada.text = jogada

        val context = holder.itemView.context
        if (jogada.contains("VENCEU A PARTIDA") || jogada.contains("★")) {
            val corVitoria = ContextCompat.getColor(context, R.color.historico_vitoria_text)
            holder.binding.txtDescricaoJogada.setTextColor(corVitoria)
            holder.binding.txtDescricaoJogada.typeface = Typeface.DEFAULT_BOLD
        } else {
            val typedValue = TypedValue()
            context.theme.resolveAttribute(android.R.attr.textColorPrimary, typedValue, true)
            val corPadrao = if (typedValue.resourceId != 0) {
                ContextCompat.getColor(context, typedValue.resourceId)
            } else {
                typedValue.data
            }
            holder.binding.txtDescricaoJogada.setTextColor(corPadrao)
            holder.binding.txtDescricaoJogada.typeface = Typeface.DEFAULT
        }
    }

    override fun getItemCount(): Int = listaJogadas.size
}