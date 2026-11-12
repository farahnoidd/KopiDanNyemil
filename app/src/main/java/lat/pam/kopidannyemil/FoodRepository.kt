package lat.pam.kopidannyemil

import androidx.lifecycle.LiveData

class FoodRepository(private val dao: FoodDao) {

    val allFoods: LiveData<List<FoodItem>> = dao.getAllFoods()

    // Isi data awal hanya jika tabel masih kosong
    suspend fun seedIfEmpty() {
        if (dao.count() == 0) dao.insertAll(DummyData.foods)
    }

    suspend fun insert(food: FoodItem) = dao.insert(food)
    suspend fun update(food: FoodItem) = dao.update(food)
    suspend fun delete(food: FoodItem) = dao.delete(food)
}