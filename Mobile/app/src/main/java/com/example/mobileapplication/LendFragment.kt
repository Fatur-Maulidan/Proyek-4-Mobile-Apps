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
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_lend.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Form.newInstance] factory method to
 * create an instance of this fragment.
 */
class Lend : Fragment(), RecyclerViewInterface{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val list = ArrayList<TugasAkhir>()
    private lateinit var varBtnTambah: Button
    private val preferences = Preferences()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_lend, container, false)

        varBtnTambah = view.findViewById(R.id.btnTambah)
        varBtnTambah.setOnClickListener(View.OnClickListener {
            goToAnotherActicity()
        })

        // Inflate the layout for this fragment
        return view
    }

    private fun goToAnotherActicity(){
        startActivity(Intent(activity, LendPageActivity::class.java))
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Form.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Lend().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contentPeminjaman.setHasFixedSize(true)
        contentPeminjaman.layoutManager = LinearLayoutManager(context)

        val apiService = ApiService().endPoint().create(ApiEndpoint::class.java)
        apiService.getTugasAkhir("Bearer " + context?.let { preferences.getToken(it) }).enqueue(object : Callback<ArrayList<TugasAkhir>> {
            override fun onResponse(
                call: Call<ArrayList<TugasAkhir>>,
                response: Response<ArrayList<TugasAkhir>>
            ) {
                response.body()?.let { list.addAll(it) }
                val adapter = PostAdapter(list)
                contentPeminjaman.adapter = adapter
            }

            override fun onFailure(call: Call<ArrayList<TugasAkhir>>, t: Throwable) {

            }
        })
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }
}