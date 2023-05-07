package com.example.mobileapplication

import CustomClass.LoadingDialog
import CustomInterface.RecyclerViewInterface
import RecyclerViewData.JurusanAdapter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home_filter_jurusan.*
import KeyStore.Preferences
import Model.JurusanResponse
import Retrofit.ApiEndpoint
import Retrofit.ApiService
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import androidx.lifecycle.lifecycleScope
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
 * Use the [HomeFragmentFilterJurusa.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragmentFilterJurusan : Fragment(), RecyclerViewInterface {

    private lateinit var varButtonJurusan: Button
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
        return inflater.inflate(R.layout.fragment_home_filter_jurusan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewJurusan.setHasFixedSize(true)
        recyclerViewJurusan.layoutManager = LinearLayoutManager(context)

        loadingDialog.startLoadingDialog()
        var handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            lifecycleScope.launch {
                val response = async { getJurusanFromDatabase() }.await()
                loadingDialog.dismissDialog()
            }
        }, 5000)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragmentFilterJurusan().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private suspend fun getJurusanFromDatabase(): Boolean {
        val deferred = CompletableDeferred<Boolean>()
        val apiService = ApiService().endPoint().create(ApiEndpoint::class.java)
        apiService.getJurusan("Bearer " + context?.let { preferences.getToken(it) }).enqueue(object : Callback<JurusanResponse>{
            override fun onResponse(
                call: Call<JurusanResponse>,
                response: Response<JurusanResponse>
            ) {
                if(response.isSuccessful){
                    val adapter = JurusanAdapter(response.body())
                    Log.d("jurusan", adapter.toString())
                    adapter.setOnItemClickListener(object : JurusanAdapter.OnItemClickListener{
                        override fun onButtonClick(position: Int) {
                            val clickedPosition = response.body()?.jurusan?.get(position)
                            context?.let {
                                preferences.setJurusan(it, clickedPosition.toString())
                            }
                            val intent = Intent(activity, HomeActivity::class.java)
                            startActivity(intent)
                        }
                    })
                    deferred.complete(true)
                    recyclerViewJurusan.adapter = adapter
                } else {
                    deferred.complete(false)
                }
            }

            override fun onFailure(call: Call<JurusanResponse>, t: Throwable) {
                deferred.complete(false)
            }
        })
        return deferred.await()
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }
}
