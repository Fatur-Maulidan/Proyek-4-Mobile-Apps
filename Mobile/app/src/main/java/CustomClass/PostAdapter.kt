package CustomClass

import Model.PostResponse
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileapplication.R
import kotlinx.android.synthetic.main.item_row_tugas_akhir.view.*

class PostAdapter(private val list: ArrayList<PostResponse>, private var listener: OnItemClickListener? = null): RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val imageView: ImageView = itemView.findViewById(R.id.imageViewContent)
        private val tvTitle: TextView = itemView.findViewById(R.id.title)
        private val tvJurusan: TextView = itemView.findViewById(R.id.jurusan)

        init {
            itemView.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }

        fun bind(postResponse: PostResponse){
            with(itemView){
                tvTitle.text = postResponse.title
                Log.d("id", postResponse.id.toString())
                tvJurusan.text = postResponse.text
                Log.d("Text", postResponse.text.toString())
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_tugas_akhir, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        return holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}
