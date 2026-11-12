package lat.pam.kopidannyemil

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FoodViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FoodRepository
    val allFoods: LiveData<List<FoodItem>>

    init {
        val dao = AppDatabase.getDatabase(application).foodDao()
        repository = FoodRepository(dao)
        allFoods = repository.allFoods
        viewModelScope.launch { repository.seedIfEmpty() }
    }

    fun insert(food: FoodItem) = viewModelScope.launch { repository.insert(food) }
    fun update(food: FoodItem) = viewModelScope.launch { repository.update(food) }
    fun delete(food: FoodItem) = viewModelScope.launch { repository.delete(food) }
}