package com.example.mobileapplication

import CustomClass.LoadingDialog
import CustomInterface.RecyclerViewInterface
import KeyStore.Preferences
import Model.ProgramStudi
import RecyclerViewData.PostAdapter
import RecyclerViewData.ProgramStudiAdapter
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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home_filter_jurusan.*
import kotlinx.android.synthetic.main.fragment_home_filter_prodi.*
import kotlinx.android.synthetic.main.item_row_program_studi.*
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
 * Use the [HomeFragmentFilterProdi.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragmentFilterProdi : Fragment(), RecyclerViewInterface {
    private val preferences = Preferences()
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog = LoadingDialog(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewProgramStudi.setHasFixedSize(true)
        recyclerViewProgramStudi.layoutManager = LinearLayoutManager(context)

        loadingDialog.startLoadingDialog()
        var handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            lifecycleScope.launch {
                val response = async { getProgramStudiFromDatabase() }.await()
                loadingDialog.dismissDialog()
            }
        }, 5000)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_filter_prodi, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragmentFilterProdi().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private suspend fun getProgramStudiFromDatabase(): Boolean{
        val deferred = CompletableDeferred<Boolean>()
        val apiService = ApiService().endPoint().create(ApiEndpoint::class.java)
        context?.let {
            preferences.getJurusan(it)?.let {
                apiService.getProgramStudi("Bearer " + context?.let { preferences.getToken(it) }, it).enqueue(object : Callback<ArrayList<ProgramStudi>>{
                    override fun onResponse(
                        call: Call<ArrayList<ProgramStudi>>,
                        response: Response<ArrayList<ProgramStudi>>
                    ) {
                        if(response.isSuccessful){
                            val adapter = ProgramStudiAdapter(response.body()!!)
                            adapter.setOnItemClickListener(object : ProgramStudiAdapter.OnItemClickListener{
                                override fun onButtonClick(position: Int) {
                                    val clickedPosition = response.body()?.get(position)
                                    clickedPosition?.nama?.let { it1 ->
                                        preferences.setProdi(
                                            context!!,
                                            it1
                                        )
                                    }
                                    clickedPosition?.nomor?.let { it1 ->
                                        preferences.setProdiId(
                                            context!!,
                                            it1
                                        )
                                    }
                                    val fragmentManager = requireActivity().supportFragmentManager
                                    val homeFragment = Home()
                                    fragmentManager.beginTransaction().replace(R.id.frame_layout, homeFragment).commit()
                                }
                            })
                            deferred.complete(true)
                            recyclerViewProgramStudi.adapter = adapter
                        } else {
                            deferred.complete(false)
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<ProgramStudi>>, t: Throwable) {
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