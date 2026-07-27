package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        AgentTaskEntity::class,
        TaskStepEntity::class,
        ConnectorEntity::class,
        ExtensionEntity::class,
        MemoryEntity::class,
        BuildReleaseEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun agentDao(): AgentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "general_digital_agent.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
