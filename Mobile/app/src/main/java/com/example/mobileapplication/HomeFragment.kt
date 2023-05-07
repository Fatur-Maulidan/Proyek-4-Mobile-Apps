package com.example.mobileapplication

import CustomClass.LoadingDialog
import CustomInterface.RecyclerViewInterface
import KeyStore.Preferences
import Model.TugasAkhir
import RecyclerViewData.PostAdapter
import Retrofit.ApiEndpoint
import Retrofit.ApiService
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
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
    private val preferences = Preferences()
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog = LoadingDialog(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        var preferences = Preferences()

        var varTextViewJurusan: TextView = view.findViewById(R.id.textViewHomeJurusan)
        varTextViewJurusan.text = context?.let { preferences.getJurusan(it) }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contentView.setHasFixedSize(true)
        contentView.layoutManager = LinearLayoutManager(context)

        loadingDialog.startLoadingDialog()
        var handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            lifecycleScope.launch {
                val response = async { getAllDataFromDatabase() }.await()
                loadingDialog.dismissDialog()
            }
        }, 5000)

        textViewHomeJurusan.setOnClickListener({
            val fragmentManager = requireActivity().supportFragmentManager
            val homeFragmentFilterJurusan = HomeFragmentFilterJurusan()
            fragmentManager.beginTransaction().replace(R.id.frame_layout, homeFragmentFilterJurusan).commit()
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private suspend fun getAllDataFromDatabase(): Boolean{
        val deferred = CompletableDeferred<Boolean>()
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
                    deferred.complete(true)
                    contentView.adapter = adapter
                } else {
                    deferred.complete(false)
                }
            }

            override fun onFailure(call: Call<ArrayList<TugasAkhir>>, t: Throwable) {
                deferred.complete(false)
            }
        })
        return deferred.await()
    }

    override fun onItemClick(position: Int) {
    }
}