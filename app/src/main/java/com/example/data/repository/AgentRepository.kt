package com.example.data.repository

import com.example.data.local.AgentDao
import com.example.data.local.AgentTaskEntity
import com.example.data.local.BuildReleaseEntity
import com.example.data.local.ConnectorEntity
import com.example.data.local.ExtensionEntity
import com.example.data.local.MemoryEntity
import com.example.data.local.TaskStepEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class AgentRepository(private val agentDao: AgentDao) {

    val allTasks: Flow<List<AgentTaskEntity>> = agentDao.getAllTasks()
    val allConnectors: Flow<List<ConnectorEntity>> = agentDao.getAllConnectors()
    val allExtensions: Flow<List<ExtensionEntity>> = agentDao.getAllExtensions()
    val allMemories: Flow<List<MemoryEntity>> = agentDao.getAllMemories()
    val allBuildReleases: Flow<List<BuildReleaseEntity>> = agentDao.getAllBuildReleases()

    fun getStepsForTask(taskId: Long): Flow<List<TaskStepEntity>> = agentDao.getStepsForTask(taskId)

    suspend fun seedDefaultsIfEmpty() {
        val existingConnectors = agentDao.getAllConnectors().first()
        if (existingConnectors.isEmpty()) {
            val defaultConnectors = listOf(
                ConnectorEntity(
                    connectorKey = "email_calendar",
                    name = "Email & Calendar",
                    nameAr = "البريد الإلكتروني والتقويم",
                    category = "EMAIL_CALENDAR",
                    description = "Automated email processing, meeting scheduling & event management",
                    descriptionAr = "معالجة البريد الإلكتروني آليًا، جدولة الاجتماعات والمواعيد"
                ),
                ConnectorEntity(
                    connectorKey = "cloud_storage",
                    name = "Cloud Drive & Files",
                    nameAr = "التخزين السحابي والملفات",
                    category = "CLOUD_STORAGE",
                    description = "Google Drive, Cloud Storage & local file system sync",
                    descriptionAr = "مزامنة التخزين السحابي والمستندات المحلية"
                ),
                ConnectorEntity(
                    connectorKey = "database_sql",
                    name = "Databases & Analytics",
                    nameAr = "قواعد البيانات والتحليل",
                    category = "DATABASE",
                    description = "Room DB, Cloud SQL, Spanner & BigQuery connectors",
                    descriptionAr = "مكامن قواعد البيانات والتحليلات المؤسسية"
                ),
                ConnectorEntity(
                    connectorKey = "erp_crm",
                    name = "Enterprise ERP & CRM",
                    nameAr = "أنظمة المؤسسات (ERP & CRM)",
                    category = "ERP_CRM",
                    description = "Salesforce, SAP, Zoho CRM & customer workflows",
                    descriptionAr = "سجلات العملاء وإدارة موارد المؤسسات"
                ),
                ConnectorEntity(
                    connectorKey = "messaging_slack",
                    name = "Messaging & Chat",
                    nameAr = "التواصل والرسائل الفورية",
                    category = "MESSAGING",
                    description = "Slack, WhatsApp, Telegram & Chatbot automation",
                    descriptionAr = "أتمتة الرسائل الفورية وإشعارات القنوات"
                ),
                ConnectorEntity(
                    connectorKey = "web_search",
                    name = "Web & Search Engine",
                    nameAr = "المتصفح والبحث السريع",
                    category = "WEB_SEARCH",
                    description = "Real-time web search, maps navigation & data scraper",
                    descriptionAr = "البحث المباشر في الويب وجلب البيانات من الخرائط"
                ),
                ConnectorEntity(
                    connectorKey = "dev_cli",
                    name = "Dev Tools & GitHub CLI",
                    nameAr = "أدوات التطوير ومستودعات الكود",
                    category = "DEV_CLI",
                    description = "Git repos, terminal CLI & build pipeline integration",
                    descriptionAr = "إدارة المستودعات وأوامر الطرفية وأنابيب البناء"
                ),
                ConnectorEntity(
                    connectorKey = "iot_sensors",
                    name = "IoT & Hardware Telemetry",
                    nameAr = "إنترنت الأشياء والحساسات",
                    category = "IOT",
                    description = "Sensors, camera, GPS telemetry & smart device control",
                    descriptionAr = "قراءة الحساسات والتحكم بالأجهزة الذكية"
                )
            )
            agentDao.insertConnectors(defaultConnectors)
        }

        val existingExtensions = agentDao.getAllExtensions().first()
        if (existingExtensions.isEmpty()) {
            val defaultExtensions = listOf(
                ExtensionEntity(
                    extensionKey = "text_gen",
                    name = "Generative AI Text Engine",
                    nameAr = "محرك الذكاء الاصطناعي للنصوص",
                    category = "TEXT_AI",
                    description = "Gemini 3.5 Flash reasoning & deep content writing",
                    descriptionAr = "صياغة النصوص والاستدلال العميق"
                ),
                ExtensionEntity(
                    extensionKey = "ocr_vision",
                    name = "Vision OCR & Document Reader",
                    nameAr = "التعرف الضوئي وتحليل المستندات",
                    category = "VISION_OCR",
                    description = "PDF, image text extraction & invoice parser",
                    descriptionAr = "استخراج النصوص من الصور والفواتير والملفات"
                ),
                ExtensionEntity(
                    extensionKey = "code_runner",
                    name = "Code Sandbox & Debugger",
                    nameAr = "محلل ومنفذ الأكواد البرمجية",
                    category = "CODE_RUNNER",
                    description = "Kotlin, Python script execution & bug finder",
                    descriptionAr = "تنفيذ الأكواد واكتشاف الأخطاء البرمجية"
                ),
                ExtensionEntity(
                    extensionKey = "speech_audio",
                    name = "Voice & Speech Engine",
                    nameAr = "تحويل النص لصوت وبالعكس",
                    category = "AUDIO_SPEECH",
                    description = "Speech synthesis & audio transcription",
                    descriptionAr = "توليد الصوت الطبيعي وتفريغ التسجيلات"
                ),
                ExtensionEntity(
                    extensionKey = "translation",
                    name = "Multilingual Translator",
                    nameAr = "المترجم الذكي متعدد اللغات",
                    category = "TRANSLATION",
                    description = "Instant Arabic/English contextual translation",
                    descriptionAr = "ترجمة دقيقة وسريعة بين العربية والإنجليزية"
                ),
                ExtensionEntity(
                    extensionKey = "data_analytics",
                    name = "Data Analytics & Charts",
                    nameAr = "تحليل البيانات والرسوم البيانية",
                    category = "DATA_ANALYTICS",
                    description = "Chart rendering, stats extraction & reporting",
                    descriptionAr = "تحليل الجداول وبناء الرسوم البيانية"
                ),
                ExtensionEntity(
                    extensionKey = "smart_memory",
                    name = "Long-Term Digital Memory",
                    nameAr = "الذاكرة الرقمية طويلة المدى",
                    category = "MEMORY",
                    description = "Contextual storage & semantic vector recall",
                    descriptionAr = "حفظ السياق واسترجاع المعلومات السابقة"
                )
            )
            agentDao.insertExtensions(defaultExtensions)
        }

        val existingTasks = agentDao.getAllTasks().first()
        if (existingTasks.isEmpty()) {
            val taskId = agentDao.insertTask(
                AgentTaskEntity(
                    title = "Generate Executive Weekly Operations Report & Build Release APK v1.0",
                    titleAr = "إعداد تقرير العمليات الأسبوعي وبناء حزمة الإصدار APK v1.0",
                    description = "Analyze team tasks, compile business analytics, and execute Android APK release build.",
                    status = "COMPLETED",
                    category = "DEV_BUILD",
                    priority = "HIGH",
                    assignedAgent = "General Executive Agent",
                    progress = 1.0f,
                    completedAt = System.currentTimeMillis(),
                    reasoningLog = "Executive goal parsed successfully. Connectors (Email, Cloud, Dev CLI) queried. Build diagnostics executed with 0 fatal errors.",
                    outputResult = "Report compiled and published. Android Release APK v1.0 generated successfully (SHA256 verified)."
                )
            )
            agentDao.insertSteps(
                listOf(
                    TaskStepEntity(
                        taskId = taskId,
                        stepIndex = 1,
                        stepTitle = "Context Analysis & Requirement Decomposition",
                        stepTitleAr = "تحليل السياق وتفكيك الأهداف",
                        isCompleted = true,
                        toolUsed = "General Agent Planner",
                        logOutput = "Goal separated into 3 sub-tasks: Data collection, Gemini text synthesis, Android Gradle Build pipeline."
                    ),
                    TaskStepEntity(
                        taskId = taskId,
                        stepIndex = 2,
                        stepTitle = "Connector Query & Data Retrieval",
                        stepTitleAr = "استعلام الموصلات وجلب البيانات",
                        isCompleted = true,
                        toolUsed = "Connectors: Cloud Drive & Database",
                        logOutput = "Retrieved 45 activity records from Room Database & 3 project update documents."
                    ),
                    TaskStepEntity(
                        taskId = taskId,
                        stepIndex = 3,
                        stepTitle = "Android Release Build Execution",
                        stepTitleAr = "تنفيذ بناء حزمة الإصدار Android",
                        isCompleted = true,
                        toolUsed = "Build & Release System",
                        logOutput = "Compiled Kotlin Compose UI, KSP Room symbols, signed with Keystore upload key. File size: 18.2 MB."
                    )
                )
            )
        }

        val existingReleases = agentDao.getAllBuildReleases().first()
        if (existingReleases.isEmpty()) {
            agentDao.insertBuildRelease(
                BuildReleaseEntity(
                    versionName = "1.0.0",
                    versionCode = 1,
                    buildType = "RELEASE_APK",
                    status = "SUCCESS",
                    artifactSizeMb = 18.2f,
                    sha256Checksum = "SHA256-9A8B7C6D5E4F3E2D",
                    buildDurationSec = 4,
                    changelog = "Initial stable release of General Digital Agent with Build & Release System.",
                    signatureStatus = "V1/V2/V3 Signed (Release Key)"
                )
            )
        }

        val existingMemories = agentDao.getAllMemories().first()
        if (existingMemories.isEmpty()) {
            agentDao.insertMemory(
                MemoryEntity(
                    keyTitle = "System Architecture Rule",
                    content = "Universal Integration Layer with dynamic connector registration & zero-downtime extension loading.",
                    category = "SYSTEM_RULE",
                    tags = "architecture,connectors,core"
                )
            )
            agentDao.insertMemory(
                MemoryEntity(
                    keyTitle = "Build Release Configuration",
                    content = "Release APK & AAB bundles signed via Secrets Gradle plugin and Keystore V2 scheme.",
                    category = "KNOWLEDGE",
                    tags = "build,gradle,keystore"
                )
            )
        }
    }

    suspend fun insertTaskWithSteps(
        task: AgentTaskEntity,
        steps: List<TaskStepEntity>
    ): Long {
        val taskId = agentDao.insertTask(task)
        val stepsWithTaskId = steps.map { it.copy(taskId = taskId) }
        agentDao.insertSteps(stepsWithTaskId)
        return taskId
    }

    suspend fun updateTask(task: AgentTaskEntity) {
        agentDao.updateTask(task)
    }

    suspend fun deleteTask(taskId: Long) {
        agentDao.deleteStepsForTask(taskId)
        agentDao.deleteTask(taskId)
    }

    suspend fun toggleConnector(connectorKey: String, isEnabled: Boolean) {
        val connectors = agentDao.getAllConnectors().first()
        val match = connectors.find { it.connectorKey == connectorKey }
        if (match != null) {
            agentDao.updateConnector(match.copy(isEnabled = isEnabled, lastSyncTime = System.currentTimeMillis()))
        }
    }

    suspend fun toggleExtension(extensionKey: String, isEnabled: Boolean) {
        val extensions = agentDao.getAllExtensions().first()
        val match = extensions.find { it.extensionKey == extensionKey }
        if (match != null) {
            agentDao.updateExtension(match.copy(isEnabled = isEnabled))
        }
    }

    suspend fun saveMemory(memory: MemoryEntity) {
        agentDao.insertMemory(memory)
    }

    suspend fun deleteMemory(id: Long) {
        agentDao.deleteMemory(id)
    }

    suspend fun saveBuildRelease(buildRelease: BuildReleaseEntity): Long {
        return agentDao.insertBuildRelease(buildRelease)
    }
}
