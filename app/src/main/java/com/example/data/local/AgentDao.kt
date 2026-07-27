package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AgentDao {
    // Tasks
    @Query("SELECT * FROM tasks ORDER BY createdAt DESC")
    fun getAllTasks(): Flow<List<AgentTaskEntity>>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: Long): AgentTaskEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: AgentTaskEntity): Long

    @Update
    suspend fun updateTask(task: AgentTaskEntity)

    @Query("DELETE FROM tasks WHERE id = :taskId")
    suspend fun deleteTask(taskId: Long)

    // Task Steps
    @Query("SELECT * FROM task_steps WHERE taskId = :taskId ORDER BY stepIndex ASC")
    fun getStepsForTask(taskId: Long): Flow<List<TaskStepEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSteps(steps: List<TaskStepEntity>)

    @Query("DELETE FROM task_steps WHERE taskId = :taskId")
    suspend fun deleteStepsForTask(taskId: Long)

    // Connectors
    @Query("SELECT * FROM connectors")
    fun getAllConnectors(): Flow<List<ConnectorEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConnectors(connectors: List<ConnectorEntity>)

    @Update
    suspend fun updateConnector(connector: ConnectorEntity)

    // Extensions
    @Query("SELECT * FROM extensions")
    fun getAllExtensions(): Flow<List<ExtensionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExtensions(extensions: List<ExtensionEntity>)

    @Update
    suspend fun updateExtension(extension: ExtensionEntity)

    // Memory
    @Query("SELECT * FROM memories ORDER BY timestamp DESC")
    fun getAllMemories(): Flow<List<MemoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemory(memory: MemoryEntity)

    @Query("DELETE FROM memories WHERE id = :id")
    suspend fun deleteMemory(id: Long)

    // Build Releases
    @Query("SELECT * FROM build_releases ORDER BY timestamp DESC")
    fun getAllBuildReleases(): Flow<List<BuildReleaseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBuildRelease(buildRelease: BuildReleaseEntity): Long
}
