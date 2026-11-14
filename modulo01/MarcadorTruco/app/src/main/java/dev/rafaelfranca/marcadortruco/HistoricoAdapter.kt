package dev.rafaelfranca.marcadortruco

import android.graphics.Typeface
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import dev.rafaelfranca.marcadortruco.databinding.ItemHistoricoBinding

class HistoricoAdapter(
    // Entrada
    private val listaJogadas: List<String>
) : RecyclerView.Adapter<HistoricoAdapter.HistoricoViewHolder>() {

    class HistoricoViewHolder(val binding: ItemHistoricoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricoViewHolder {
        // Saída
        val binding = ItemHistoricoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HistoricoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoricoViewHolder, position: Int) {
        // Entrada
        val jogada = listaJogadas[position]

        // Processamento
        val context = holder.itemView.context
        val ehVitoria = jogada.contains("VENCEU A PARTIDA") || jogada.contains("★")

        val corTexto: Int
        val estiloFonte: Typeface

        if (ehVitoria) {
            corTexto = ContextCompat.getColor(context, R.color.historico_vitoria_text)
            estiloFonte = Typeface.DEFAULT_BOLD
        } else {
            val typedValue = TypedValue()
            context.theme.resolveAttribute(android.R.attr.textColorPrimary, typedValue, true)
            corTexto = if (typedValue.resourceId != 0) {
                ContextCompat.getColor(context, typedValue.resourceId)
            } else {
                typedValue.data
            }
            estiloFonte = Typeface.DEFAULT
        }

        // Saída
        holder.binding.txtDescricaoJogada.text = jogada
        holder.binding.txtDescricaoJogada.setTextColor(corTexto)
        holder.binding.txtDescricaoJogada.typeface = estiloFonte
    }

    override fun getItemCount(): Int = listaJogadas.size
}