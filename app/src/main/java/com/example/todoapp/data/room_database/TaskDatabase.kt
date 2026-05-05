package com.example.todoapp.data.room_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TaskItem::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object { //to make singleton pattern, can not make more than one instance, only one instance in the whole app

        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getDatabase(context: Context): TaskDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "tasks_database"
                )
                    .fallbackToDestructiveMigration() //if db version increased old db is deleted and a new one is made
                    //.addMigrations() -> to provide manual migration
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }

}