package RecyclerViewData

import Model.TugasAkhirResponse
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileapplication.R

class PostAdapter(private val list: List<TugasAkhirResponse>?, private var listener: OnItemClickListener? = null): RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    private val tugasAkhirList = list?: emptyList()

    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val varButtonJudulDokumen: Button = itemView.findViewById(R.id.buttonJudulDokumen)

        init {
            itemView.setOnClickListener {
                listener?.onButtonClick(adapterPosition)
            }
        }

        fun bind(tugasAkhirResponse: TugasAkhirResponse){
            with(itemView){
                varButtonJudulDokumen.text = tugasAkhirResponse.tugas_akhir?.judul
            }
        }
    }

    interface OnItemClickListener {
        fun onButtonClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_tugas_akhir, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentData = tugasAkhirList[position]

        holder.varButtonJudulDokumen.text = currentData?.tugas_akhir?.judul

        // Mengatur aksi pada button ketika di klik
        holder.varButtonJudulDokumen.setOnClickListener {
            // Memanggil fungsi onButtonClick pada listener
            listener?.onButtonClick(position)
        }
    }

    override fun getItemCount(): Int = tugasAkhirList.size
}
