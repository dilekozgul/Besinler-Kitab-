package com.dilekozgul.besinlerkitabi.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dilekozgul.besinlerkitabi.model.Food
import com.dilekozgul.besinlerkitabi.room.FoodDatabase
import kotlinx.coroutines.launch

class FoodDetailViewModel(application: Application): BaseViewModel(application) {

    val foodLiveData = MutableLiveData<Food>()

    fun roomGetData(uuid: Int){

        launch {

            val dao = FoodDatabase(getApplication()).foodDao()
            val food = dao.getFood(uuid)
            foodLiveData.value=food
        }


    }

}