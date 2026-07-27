package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class AgentTaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val titleAr: String,
    val description: String,
    val status: String, // PENDING, IN_PROGRESS, COMPLETED, FAILED
    val category: String, // EXECUTIVE, DEV_BUILD, ANALYTICS, CONTENT, SYSTEM
    val priority: String, // HIGH, MEDIUM, LOW
    val assignedAgent: String, // General Agent, Dev Agent, Analyst Agent
    val progress: Float = 0f,
    val createdAt: Long = System.currentTimeMillis(),
    val completedAt: Long? = null,
    val reasoningLog: String = "",
    val outputResult: String = ""
)

@Entity(tableName = "task_steps")
data class TaskStepEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val taskId: Long,
    val stepIndex: Int,
    val stepTitle: String,
    val stepTitleAr: String,
    val isCompleted: Boolean = false,
    val toolUsed: String = "",
    val logOutput: String = ""
)

@Entity(tableName = "connectors")
data class ConnectorEntity(
    @PrimaryKey val connectorKey: String,
    val name: String,
    val nameAr: String,
    val category: String, // EMAIL_CALENDAR, CLOUD_STORAGE, DATABASE, ERP_CRM, MESSAGING, WEB_SEARCH, DEV_CLI, IOT
    val description: String,
    val descriptionAr: String,
    val isEnabled: Boolean = true,
    val isConnected: Boolean = true,
    val configSummary: String = "Auto-configured",
    val lastSyncTime: Long = System.currentTimeMillis()
)

@Entity(tableName = "extensions")
data class ExtensionEntity(
    @PrimaryKey val extensionKey: String,
    val name: String,
    val nameAr: String,
    val description: String,
    val descriptionAr: String,
    val category: String, // TEXT_AI, VISION_OCR, CODE_RUNNER, AUDIO_SPEECH, TRANSLATION, DATA_ANALYTICS, MEMORY
    val isEnabled: Boolean = true,
    val usageCount: Int = 0
)

@Entity(tableName = "memories")
data class MemoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val keyTitle: String,
    val content: String,
    val category: String, // KNOWLEDGE, SYSTEM_RULE, TASK_CONTEXT, USER_PREF
    val tags: String, // comma-separated
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "build_releases")
data class BuildReleaseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val versionName: String,
    val versionCode: Int,
    val buildType: String, // DEBUG_APK, RELEASE_APK, PLAY_STORE_AAB
    val status: String, // SUCCESS, IN_PROGRESS, FAILED
    val artifactSizeMb: Float,
    val sha256Checksum: String,
    val buildDurationSec: Int,
    val changelog: String,
    val signatureStatus: String, // V1/V2/V3 Signed
    val timestamp: Long = System.currentTimeMillis()
)
