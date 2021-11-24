package com.dilekozgul.besinlerkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dilekozgul.besinlerkitabi.R
import com.dilekozgul.besinlerkitabi.util.downloadImage
import com.dilekozgul.besinlerkitabi.util.makePlaceholder
import com.dilekozgul.besinlerkitabi.viewmodel.FoodDetailViewModel
import kotlinx.android.synthetic.main.fragment_food_detail.*

class FoodDetailFragment : Fragment() {

    private lateinit var viewModel: FoodDetailViewModel
    private var foodId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            foodId = FoodDetailFragmentArgs.fromBundle(it).foodId
        }


        viewModel = ViewModelProvider(this).get(FoodDetailViewModel::class.java)
        viewModel.roomGetData(foodId)

        observeLiveData()

    }

    fun observeLiveData(){

        viewModel.foodLiveData.observe(viewLifecycleOwner, Observer { food->
            food?.let {
                txtBesinIsim.text = it.name
                txtBesinKalori.text= it.calorie
                txtBesinKarbonhidrat.text = it.carbohydrate
                txtBesinProtein.text = it.foodProtein
                txtBesinYag.text = it.oil
                context?.let {
                    imgFood.downloadImage(food.foodImage, makePlaceholder(it))
                }

            }

        })
    }

}