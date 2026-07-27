package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Task
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.AgentViewModel
import com.example.ui.AppLanguage
import com.example.ui.NavigationTab
import com.example.ui.components.PromptBar
import com.example.ui.components.TopBarHeader
import com.example.ui.screens.BuildReleaseScreen
import com.example.ui.screens.ConnectorsHubScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.MemoryScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.TasksScreen
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    private val viewModel: AgentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                AgentAppContent(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun AgentAppContent(viewModel: AgentViewModel) {
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val language by viewModel.language.collectAsStateWithLifecycle()
    val selectedModel by viewModel.selectedModel.collectAsStateWithLifecycle()

    val tasks by viewModel.tasks.collectAsStateWithLifecycle()
    val connectors by viewModel.connectors.collectAsStateWithLifecycle()
    val extensions by viewModel.extensions.collectAsStateWithLifecycle()
    val memories by viewModel.memories.collectAsStateWithLifecycle()
    val buildReleases by viewModel.buildReleases.collectAsStateWithLifecycle()

    val promptInput by viewModel.promptInput.collectAsStateWithLifecycle()
    val isExecutingPrompt by viewModel.isExecutingPrompt.collectAsStateWithLifecycle()
    val buildUiState by viewModel.buildUiState.collectAsStateWithLifecycle()

    val recentRelease = buildReleases.firstOrNull()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarHeader(
                language = language,
                selectedModel = selectedModel,
                onToggleLanguage = { viewModel.toggleLanguage() }
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                // Persistent Prompt Input Bar
                PromptBar(
                    promptValue = promptInput,
                    onValueChange = { viewModel.updatePromptInput(it) },
                    onSendPrompt = { promptText -> viewModel.executeUserPrompt(promptText) },
                    isExecuting = isExecutingPrompt,
                    language = language,
                    selectedModel = selectedModel
                )

                // Navigation Bar
                NavigationBar {
                    NavigationItemPill(
                        tab = NavigationTab.DASHBOARD,
                        labelAr = "الرئيسية",
                        labelEn = "Dashboard",
                        icon = Icons.Default.Dashboard,
                        currentTab = currentTab,
                        language = language,
                        onSelect = { viewModel.selectTab(NavigationTab.DASHBOARD) }
                    )

                    NavigationItemPill(
                        tab = NavigationTab.TASKS,
                        labelAr = "المهام",
                        labelEn = "Tasks",
                        icon = Icons.Default.Task,
                        currentTab = currentTab,
                        language = language,
                        onSelect = { viewModel.selectTab(NavigationTab.TASKS) }
                    )

                    NavigationItemPill(
                        tab = NavigationTab.CONNECTORS,
                        labelAr = "الموصلات",
                        labelEn = "Connectors",
                        icon = Icons.Default.Hub,
                        currentTab = currentTab,
                        language = language,
                        onSelect = { viewModel.selectTab(NavigationTab.CONNECTORS) }
                    )

                    NavigationItemPill(
                        tab = NavigationTab.BUILD_RELEASE,
                        labelAr = "الإصدار",
                        labelEn = "Release",
                        icon = Icons.Default.Android,
                        currentTab = currentTab,
                        language = language,
                        onSelect = { viewModel.selectTab(NavigationTab.BUILD_RELEASE) }
                    )

                    NavigationItemPill(
                        tab = NavigationTab.MEMORY,
                        labelAr = "الذاكرة",
                        labelEn = "Memory",
                        icon = Icons.Default.Memory,
                        currentTab = currentTab,
                        language = language,
                        onSelect = { viewModel.selectTab(NavigationTab.MEMORY) }
                    )

                    NavigationItemPill(
                        tab = NavigationTab.SETTINGS,
                        labelAr = "الإعدادات",
                        labelEn = "Settings",
                        icon = Icons.Default.Settings,
                        currentTab = currentTab,
                        language = language,
                        onSelect = { viewModel.selectTab(NavigationTab.SETTINGS) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                NavigationTab.DASHBOARD -> DashboardScreen(
                    tasks = tasks,
                    connectors = connectors,
                    extensions = extensions,
                    recentRelease = recentRelease,
                    language = language,
                    onNavigateTab = { viewModel.selectTab(it) },
                    onQuickPrompt = { viewModel.executeUserPrompt(it) }
                )

                NavigationTab.TASKS -> TasksScreen(
                    tasks = tasks,
                    language = language,
                    onDeleteTask = { viewModel.deleteTask(it) }
                )

                NavigationTab.CONNECTORS -> ConnectorsHubScreen(
                    connectors = connectors,
                    extensions = extensions,
                    language = language,
                    onToggleConnector = { key, isEnabled -> viewModel.toggleConnector(key, isEnabled) },
                    onToggleExtension = { key, isEnabled -> viewModel.toggleExtension(key, isEnabled) }
                )

                NavigationTab.BUILD_RELEASE -> BuildReleaseScreen(
                    buildUiState = buildUiState,
                    buildReleases = buildReleases,
                    language = language,
                    onRunDiagnostics = { viewModel.runPreflightDiagnostics() },
                    onStartBuild = { name, code, type, notes ->
                        viewModel.startBuildAndRelease(name, code, type, notes)
                    }
                )

                NavigationTab.MEMORY -> MemoryScreen(
                    memories = memories,
                    language = language,
                    onAddMemory = { title, content, cat, tags ->
                        viewModel.addMemory(title, content, cat, tags)
                    },
                    onDeleteMemory = { viewModel.deleteMemory(it) }
                )

                NavigationTab.SETTINGS -> SettingsScreen(
                    selectedModel = selectedModel,
                    language = language,
                    onSetModel = { viewModel.setModel(it) },
                    onToggleLanguage = { viewModel.toggleLanguage() }
                )
            }
        }
    }
}

@Composable
fun RowScope.NavigationItemPill(
    tab: NavigationTab,
    labelAr: String,
    labelEn: String,
    icon: ImageVector,
    currentTab: NavigationTab,
    language: AppLanguage,
    onSelect: () -> Unit
) {
    NavigationBarItem(
        selected = currentTab == tab,
        onClick = onSelect,
        icon = { Icon(imageVector = icon, contentDescription = labelEn) },
        label = {
            Text(
                text = if (language == AppLanguage.ARABIC) labelAr else labelEn,
                fontSize = 11.sp
            )
        }
    )
}
