package com.andriibryliant.gymbros.presentation.workout.workout_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andriibryliant.gymbros.domain.model.Exercise
import com.andriibryliant.gymbros.domain.model.Workout
import com.andriibryliant.gymbros.domain.model.WorkoutExercise
import com.andriibryliant.gymbros.domain.usecase.exercise.ExerciseUseCases
import com.andriibryliant.gymbros.domain.usecase.workout.WorkoutUseCases
import com.andriibryliant.gymbros.presentation.workout.workout_detail.components.DeleteDialogState
import gymbros.composeapp.generated.resources.Res
import gymbros.composeapp.generated.resources.no_name_specified
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import org.jetbrains.compose.resources.StringResource
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class WorkoutDetailViewModel(
    private val useCases: WorkoutUseCases,
    private val exerciseUseCases: ExerciseUseCases,
) : ViewModel() {
    var workoutName by mutableStateOf("")
        private set

    var workoutId by mutableStateOf<Long>(0)
        private set

    var workoutDate by mutableStateOf(Clock.System.todayIn(TimeZone.currentSystemDefault()))
        private set

    var state by mutableStateOf<WorkoutDetailState?>(null)
        private set

    var deleteDialogState by mutableStateOf<DeleteDialogState?>(null)
        private set

    var selectedWorkout by mutableStateOf<Workout?>(null)

    private val _exercises = MutableStateFlow<List<WorkoutExercise>>(emptyList())

    var exercises: StateFlow<List<WorkoutExercise>> = _exercises.asStateFlow()

    private val _selectedExerciseId = MutableStateFlow<Long?>(null)
    val selectedExerciseId = _selectedExerciseId.asStateFlow()

    var nameError by mutableStateOf<StringResource?>(null)
        private set

    fun selectState(selectedState: WorkoutDetailState){
        state = selectedState
    }

    fun setDialogState(selectedState: DeleteDialogState){
        deleteDialogState = selectedState
    }

    fun clearDialogState(){
        deleteDialogState = null
    }

    fun onNameChange(value: String){
        workoutName = value
        nameError = null
    }

    fun updateWorkoutDate(date: LocalDate?){
        workoutDate = date?: Clock.System.todayIn(TimeZone.currentSystemDefault())
    }

    fun onAddWorkout(){
        state = WorkoutDetailState.AddWorkout
        if(selectedWorkout != null){
            return
        }
        viewModelScope.launch(Dispatchers.IO){
            val workoutId = useCases.insertWorkoutUseCase(
                Workout(
                    name = "New Workout",
                    date = Clock.System.todayIn(TimeZone.currentSystemDefault())
                )
            )
            onSelectWorkout(workoutId)
        }
    }

    fun onEditWorkout(id: Long){
        state = WorkoutDetailState.EditWorkout
        onSelectWorkout(id)
    }

    fun onSelectWorkout(id: Long){
        viewModelScope.launch(Dispatchers.IO) {
            useCases.getWorkoutByIdUseCase(id).collect { workout ->
                workout?.let {
                    workoutName = workout.name
                    workoutId = workout.id
                    workoutDate = workout.date
                    selectedWorkout = workout
                }
            }
        }
        observeExercises(id)
    }

    fun observeExercises(id: Long){
        viewModelScope.launch(Dispatchers.IO) {
            useCases.getExerciseForWorkoutUseCase(id).collectLatest {
                _exercises.value = it
            }
        }
    }

    fun onAddExercise(exerciseId: Long){
        viewModelScope.launch(Dispatchers.IO){
            exerciseUseCases.getExerciseByIdUseCase(exerciseId).collect { exerciseToAdd ->
                if(_selectedExerciseId.value == null){
                    exerciseToAdd?.let {
                        useCases.insertWorkoutExerciseUseCase(
                            WorkoutExercise(
                                workoutId = workoutId,
                                exercise = exerciseToAdd,
                                orderInWorkout = 0
                            )
                        )
                    }
                }else{
                        useCases.getWorkoutExerciseByIdUseCase(_selectedExerciseId.value!!).collect { workoutExercise ->
                            exerciseToAdd?.let {
                                useCases.updateWorkoutExerciseUseCase(
                                    workoutExercise!!.copy(exercise = exerciseToAdd)
                                )
                                coroutineContext.cancel()
                            }
                        }
                }
            }
        }
    }

    fun onSelectExercise(exerciseId: Long){
        viewModelScope.launch(Dispatchers.IO){
            _selectedExerciseId.value = exerciseId
        }
    }

    fun validateName(): Boolean{
        var isValid = true
        if(workoutName.isBlank()){
            nameError = Res.string.no_name_specified
            isValid = false
        }
        return isValid
    }

    @OptIn(ExperimentalTime::class)
    fun onSaveWorkout(): Boolean{
        if (!validateName()){
            return false
        }
        viewModelScope.launch(Dispatchers.IO){
           selectedWorkout?.let { workout ->
               useCases.insertWorkoutUseCase(workout.copy(
                   id = workoutId,
                   date = workoutDate,
                   name = workoutName,
                   exercises = _exercises.value
               ))
           }
        }
        return true
    }

    fun clearSelectedExercise(){
        viewModelScope.launch(Dispatchers.IO){
            _selectedExerciseId.value = null
        }
    }

    fun onDeleteExercise(exercise: WorkoutExercise){
        viewModelScope.launch(Dispatchers.IO){
            useCases.deleteExerciseForWorkoutUseCase(exercise)
        }
    }

    fun onDeleteWorkout(){
        viewModelScope.launch(Dispatchers.IO){
            useCases.deleteWorkoutUseCase(workoutId)
            state = WorkoutDetailState.WorkoutDeleted
        }
    }
}