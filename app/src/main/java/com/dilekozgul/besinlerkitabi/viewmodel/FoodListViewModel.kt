package com.dilekozgul.besinlerkitabi.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dilekozgul.besinlerkitabi.model.Food
import com.dilekozgul.besinlerkitabi.room.FoodDatabase
import com.dilekozgul.besinlerkitabi.servis.FoodAPIservice
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FoodListViewModel(application: Application): BaseViewModel(application) {

    val foods = MutableLiveData<List<Food>>()
    val errorMessage = MutableLiveData<Boolean>()
    val uploadProgress= MutableLiveData<Boolean>()
    private val foodApiService = FoodAPIservice()
    private val disposable = CompositeDisposable()


    fun refreshData()
    {
        getDataInternet()

    }

    fun getDataInternet(){
        uploadProgress.value = true

        disposable.add(
            foodApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Food>>(){
                    override fun onSuccess(t: List<Food>) {
                        //Başarılı olursa
                        saveDatabase(t)


                    }

                    override fun onError(e: Throwable) {
                        errorMessage.value=true
                        uploadProgress.value=false
                        e.printStackTrace()
                    }

                }

                )

        )


    }

    private  fun getDataDatabase(){

        launch {
            val foodList= FoodDatabase(getApplication()).foodDao().getAllFood()
            showFoods(foodList)
        }

    }




    private fun showFoods(foodList : List<Food>){
        foods.value = foodList
        uploadProgress.value=false
        errorMessage.value=false
    }

    private fun saveDatabase(foodList: List<Food>){

        launch {

            val dao = FoodDatabase(getApplication()).foodDao()
            dao.deleteAllFood()
           val uuidLists= dao.insertAll(*foodList.toTypedArray())
            var i =0
            while (i<foodList.size){
                foodList[i].uuid = uuidLists[i].toInt()
                i=i+1
            }
            showFoods(foodList)
        }

    }

}