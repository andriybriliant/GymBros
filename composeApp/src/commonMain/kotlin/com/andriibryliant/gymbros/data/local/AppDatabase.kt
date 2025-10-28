package com.andriibryliant.gymbros.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.andriibryliant.gymbros.data.local.converter.Converters
import com.andriibryliant.gymbros.data.local.dao.ExerciseDao
import com.andriibryliant.gymbros.data.local.dao.WorkoutDao
import com.andriibryliant.gymbros.data.local.entity.ExerciseEntity
import com.andriibryliant.gymbros.data.local.entity.ExerciseMuscleGroupCrossRef
import com.andriibryliant.gymbros.data.local.entity.MuscleGroupEntity
import com.andriibryliant.gymbros.data.local.entity.SetEntity
import com.andriibryliant.gymbros.data.local.entity.WorkoutEntity
import com.andriibryliant.gymbros.data.local.entity.WorkoutExerciseEntity

@Database(
    entities = [
        ExerciseEntity::class,
        WorkoutEntity::class,
        WorkoutExerciseEntity::class,
        MuscleGroupEntity::class,
        SetEntity::class,
        ExerciseMuscleGroupCrossRef::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase: RoomDatabase() {
    abstract val workoutDao: WorkoutDao
    abstract val exerciseDao: ExerciseDao

    companion object{
        const val DB_NAME = "app.db"
    }
}