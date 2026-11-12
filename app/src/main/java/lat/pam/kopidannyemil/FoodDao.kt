package lat.pam.kopidannyemil

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface FoodDao {
    @Query("SELECT * FROM foods ORDER BY id ASC")
    fun getAllFoods(): LiveData<List<FoodItem>>

    @Query("SELECT COUNT(*) FROM foods")
    suspend fun count(): Int

    @Insert
    suspend fun insert(food: FoodItem)

    @Insert
    suspend fun insertAll(foods: List<FoodItem>)

    @Update
    suspend fun update(food: FoodItem)

    @Delete
    suspend fun delete(food: FoodItem)
}