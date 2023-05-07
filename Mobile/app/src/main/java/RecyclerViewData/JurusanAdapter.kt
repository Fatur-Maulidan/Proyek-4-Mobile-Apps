package RecyclerViewData

import Model.JurusanResponse
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileapplication.R

class JurusanAdapter(private val list: JurusanResponse?, private var listener: OnItemClickListener? = null): RecyclerView.Adapter<JurusanAdapter.JurusanViewHolder>() {
    private val jurusanList = list?.jurusan ?: emptyList()


    inner class JurusanViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val varButtonJurusan: Button = itemView.findViewById(R.id.buttonNamaJurusan)

        init {
            itemView.setOnClickListener {
                listener?.onButtonClick(adapterPosition)
            }
        }

        fun bind(data: JurusanResponse){
            with(itemView){
                varButtonJurusan.text = data.jurusan.toString()
            }
        }
    }

    interface OnItemClickListener {
        fun onButtonClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JurusanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_jurusan, parent, false)
        return JurusanViewHolder(view)
    }

    override fun onBindViewHolder(holder: JurusanViewHolder, position: Int) {
        val currentData = jurusanList[position]

        holder.varButtonJurusan.text = currentData

        // Mengatur aksi pada button ketika di klik
        holder.varButtonJurusan.setOnClickListener {
            // Memanggil fungsi onButtonClick pada listener
            listener?.onButtonClick(position)
        }
    }

    override fun getItemCount(): Int = jurusanList.size
}
