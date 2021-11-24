package com.dilekozgul.besinlerkitabi.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dilekozgul.besinlerkitabi.model.Food

@Dao
interface FoodDao {

    //Data Access Object

    @Insert
    suspend fun insertAll(vararg food :Food): List<Long>

    //Insert -> Room, insert into
    //suspend -> coroutine scope
    //vararg -> birden fazla ve istediğimiz sayıda besin
    //List<Long>-> long, uuid'ler

    @Query("SELECT *FROM Food")
    suspend fun getAllFood(): List<Food>

    @Query("SELECT *FROM Food WHERE uuid = :foodId")
    suspend fun getFood(foodId:Int) : Food


    @Query("DELETE FROM Food")
    suspend fun deleteAllFood()




}