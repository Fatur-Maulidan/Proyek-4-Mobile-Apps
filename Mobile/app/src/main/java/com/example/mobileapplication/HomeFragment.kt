package com.example.mobileapplication

import CustomClass.PostAdapter
import CustomInterface.RecyclerViewInterface
import KeyStore.Preferences
import Model.TugasAkhir
import Retrofit.ApiEndpoint
import Retrofit.ApiService
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment(), RecyclerViewInterface {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val list = ArrayList<TugasAkhir>()
    private val preferences = Preferences()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contentView.setHasFixedSize(true)
        contentView.layoutManager = LinearLayoutManager(context)

        val apiService = ApiService().endPoint().create(ApiEndpoint::class.java)
        apiService.getTugasAkhir("Bearer " + context?.let { preferences.getToken(it) }).enqueue(object : Callback<ArrayList<TugasAkhir>> {
            override fun onResponse(
                call: Call<ArrayList<TugasAkhir>>,
                response: Response<ArrayList<TugasAkhir>>
            ) {
                if(response.isSuccessful){
                    val adapter = PostAdapter(response.body()!!)
                    adapter.setOnItemClickListener(object : PostAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {
                            val clickedPosition = response.body()?.get(position)
                            val intent = Intent(activity, FinalTaskPageActivity::class.java)
                            intent.putExtra("title", clickedPosition?.judul)
                            intent.putExtra("text", clickedPosition?.id)
                            startActivity(intent)
                        }
                    })
                    contentView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<ArrayList<TugasAkhir>>, t: Throwable) {

            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }
}