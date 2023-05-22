package com.example.mobileapplication

import CustomClass.LoadingDialog
import RecyclerViewData.JurusanAdapter
import CustomInterface.RecyclerViewInterface
import KeyStore.Preferences
import Model.TugasAkhir
import Model.TugasAkhirResponse
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
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_lend.*
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
 * Use the [Form.newInstance] factory method to
 * create an instance of this fragment.
 */
class Lend : Fragment(), RecyclerViewInterface{
    private lateinit var varBtnTambah: Button
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

        loadingDialog.startLoadingDialog()
        var handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            lifecycleScope.launch {
                val response = async { getAllDataFromDatabase() }
                loadingDialog.dismissDialog()
            }
        }, 5000)
    }

    private suspend fun getAllDataFromDatabase(): Boolean{
        val deferred = CompletableDeferred<Boolean>()
        val apiService = ApiService().endPoint().create(ApiEndpoint::class.java)
        context?.let {
            preferences.getProdiId(it)?.let {
                apiService.getTugasAkhir("Bearer " + context?.let { preferences.getToken(it) }, it).enqueue(object : Callback<ArrayList<TugasAkhirResponse>> {
                    override fun onResponse(
                        call: Call<ArrayList<TugasAkhirResponse>>,
                        response: Response<ArrayList<TugasAkhirResponse>>
                    ) {
                        if(response.isSuccessful){
                            val adapter = PostAdapter(response.body()!!)
                            contentPeminjaman.adapter = adapter
                            deferred.complete(true)
                        } else {
                            deferred.complete(false)
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<TugasAkhirResponse>>, t: Throwable) {
                        deferred.complete(false)
                    }
                })
            }
        }
        return deferred.await()
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }
}