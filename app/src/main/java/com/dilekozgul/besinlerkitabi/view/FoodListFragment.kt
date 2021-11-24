package com.dilekozgul.besinlerkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.dilekozgul.besinlerkitabi.R
import com.dilekozgul.besinlerkitabi.adapter.FoodAdapter
import com.dilekozgul.besinlerkitabi.viewmodel.FoodListViewModel
import kotlinx.android.synthetic.main.fragment_food_list.*


class FoodListFragment : Fragment() {
    private lateinit var viewModel: FoodListViewModel
    private val foodAdapter=FoodAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



     //   viewModel = ViewModelProviders.of(this).get(FoodListViewModel::class.java)
        viewModel = ViewModelProvider(this).get(FoodListViewModel::class.java)
        viewModel.refreshData()



        recyclerViewFoodList.layoutManager = LinearLayoutManager(context)
        recyclerViewFoodList.adapter = foodAdapter

        swipeRefreshLayout.setOnRefreshListener {
            progressBarFoodList.visibility = View.VISIBLE
            txtErrorMessage.visibility = View.GONE
            recyclerViewFoodList.visibility= View.GONE
            viewModel.refreshData()
            swipeRefreshLayout.isRefreshing = false


        }

        observeLiveData()



    }
    fun observeLiveData()
    {
        viewModel.foods.observe(viewLifecycleOwner, Observer { foods->
            foods?.let {

                recyclerViewFoodList.visibility = View.VISIBLE
                foodAdapter.foodListUpdate(foods)

            }

        })
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {error->
            error?.let {
                if (it){
                    txtErrorMessage.visibility = View.VISIBLE
                    recyclerViewFoodList.visibility= View.GONE

                }else{
                    txtErrorMessage.visibility = View.GONE
                }
            }

        })
        viewModel.uploadProgress.observe(viewLifecycleOwner, Observer { upload->
            upload?.let {
                if (it){
                    progressBarFoodList.visibility = View.VISIBLE
                    txtErrorMessage.visibility = View.GONE
                    recyclerViewFoodList.visibility= View.GONE

                }
                else{
                    progressBarFoodList.visibility = View.GONE


                }
            }

        })


    }


}