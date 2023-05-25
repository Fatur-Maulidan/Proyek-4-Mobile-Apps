package RecyclerViewData

import Model.ProgramStudi
import Model.TugasAkhir
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileapplication.R

// Class ini digunakan untuk menghitung banyaknya data Program Studi dan dimunculkan kedalam tampilan
// dalam bentuk recycler view

class ProgramStudiAdapter(private val list: ArrayList<ProgramStudi>, private var listener: OnItemClickListener? = null): RecyclerView.Adapter<ProgramStudiAdapter.ProgramStudiViewHolder>() {

    inner class ProgramStudiViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val varButtonProgramStudi: Button = itemView.findViewById(R.id.buttonProgramStudi)

        init {
            itemView.setOnClickListener {
                listener?.onButtonClick(adapterPosition)
            }
        }

        fun bind(getProgramStudi: ProgramStudi){
            with(itemView){
                varButtonProgramStudi.text = getProgramStudi.diploma + " " + getProgramStudi.nama
            }
        }
    }

    interface OnItemClickListener {
        fun onButtonClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramStudiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_program_studi, parent, false)
        return ProgramStudiViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProgramStudiViewHolder, position: Int) {
        var currentData = list[position]

        holder.varButtonProgramStudi.text = currentData.diploma + " " + currentData.nama

        // Mengatur aksi pada button ketika di klik
        holder.varButtonProgramStudi.setOnClickListener {
            // Memanggil fungsi onButtonClick pada listener
            listener?.onButtonClick(position)
        }
    }

    override fun getItemCount(): Int = list.size
}
