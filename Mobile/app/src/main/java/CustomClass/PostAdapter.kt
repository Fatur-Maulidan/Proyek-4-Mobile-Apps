package CustomClass

import Model.PostResponse
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileapplication.R
import kotlinx.android.synthetic.main.item_row_tugas_akhir.view.*

class PostAdapter(private val list: ArrayList<PostResponse>): RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(postResponse: PostResponse){
            with(itemView){
                title.text = "Test"
                jurusan.text = "Test"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_tugas_akhir, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(list[position])

    }

    override fun getItemCount(): Int = list.size
}