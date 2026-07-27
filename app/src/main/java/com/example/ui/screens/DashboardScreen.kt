package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Task
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.AgentTaskEntity
import com.example.data.local.BuildReleaseEntity
import com.example.data.local.ConnectorEntity
import com.example.data.local.ExtensionEntity
import com.example.ui.AppLanguage
import com.example.ui.NavigationTab

data class SubAgentInfo(
    val name: String,
    val nameAr: String,
    val role: String,
    val roleAr: String,
    val icon: ImageVector,
    val status: String,
    val statusAr: String,
    val activeTasks: Int
)

@Composable
fun DashboardScreen(
    tasks: List<AgentTaskEntity>,
    connectors: List<ConnectorEntity>,
    extensions: List<ExtensionEntity>,
    recentRelease: BuildReleaseEntity?,
    language: AppLanguage,
    onNavigateTab: (NavigationTab) -> Unit,
    onQuickPrompt: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val subAgents = listOf(
        SubAgentInfo(
            name = "General Executive Agent",
            nameAr = "الوكيل التنفيذي العام",
            role = "Planning & Task Orchestration",
            roleAr = "التخطيط وتنسيق المهام المعقدة",
            icon = Icons.Default.Psychology,
            status = "ACTIVE",
            statusAr = "نشط ومستعد",
            activeTasks = tasks.count { it.status == "IN_PROGRESS" || it.status == "PENDING" }
        ),
        SubAgentInfo(
            name = "Developer Build Agent",
            nameAr = "وكيل البناء والتطوير",
            role = "Android Gradle, KSP & Release Signer",
            roleAr = "بناء الحزم وتوقيع الإصدارات",
            icon = Icons.Default.Build,
            status = "READY",
            statusAr = "جاهز للبناء",
            activeTasks = tasks.count { it.category == "DEV_BUILD" }
        ),
        SubAgentInfo(
            name = "Data & Analytics Agent",
            nameAr = "وكيل تحليل البيانات",
            role = "SQL, Room DB & Analytics Query",
            roleAr = "تحليل الاستعلامات وقواعد البيانات",
            icon = Icons.Default.Analytics,
            status = "READY",
            statusAr = "جاهز للتحليل",
            activeTasks = tasks.count { it.category == "ANALYTICS" }
        ),
        SubAgentInfo(
            name = "Content & Document Agent",
            nameAr = "وكيل المستندات والمحتوى",
            role = "Multilingual Drafts & OCR Reader",
            roleAr = "تحرير المستندات والتعرف الضوئي",
            icon = Icons.Default.Description,
            status = "READY",
            statusAr = "جاهز للتحرير",
            activeTasks = tasks.count { it.category == "CONTENT" }
        )
    )

    val activeConnectorsCount = connectors.count { it.isEnabled }
    val activeExtensionsCount = extensions.count { it.isEnabled }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Overview Card
        item {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (language == AppLanguage.ARABIC) "منصة الوكيل الرقمي العام" else "General Digital Executive Core",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (language == AppLanguage.ARABIC) "منظومة بديل رقمي شامل لتخطيط وتنفيذ الأعمال وبناء التطبيقات" else "Integrated AI alternative executing digital operations & Android release builds.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.RocketLaunch,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Metrics Grid Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricBadge(
                            title = if (language == AppLanguage.ARABIC) "المهام الكلية" else "Total Tasks",
                            value = "${tasks.size}",
                            icon = Icons.Default.Task,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        MetricBadge(
                            title = if (language == AppLanguage.ARABIC) "الموصلات" else "Connectors",
                            value = "$activeConnectorsCount/${connectors.size}",
                            icon = Icons.Default.Hub,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        MetricBadge(
                            title = if (language == AppLanguage.ARABIC) "التكملات" else "Extensions",
                            value = "$activeExtensionsCount/${extensions.size}",
                            icon = Icons.Default.Extension,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        // Sub-Agents Operational Team Row
        item {
            Column {
                Text(
                    text = if (language == AppLanguage.ARABIC) "الوكلاء الفرعيون المترابطون" else "Interconnected Sub-Agents Team",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(10.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(subAgents) { agent ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.width(220.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.secondaryContainer),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = agent.icon,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }

                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color(0xFF10B981).copy(alpha = 0.15f))
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Text(
                                            text = if (language == AppLanguage.ARABIC) agent.statusAr else agent.status,
                                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                            color = Color(0xFF10B981)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Text(
                                    text = if (language == AppLanguage.ARABIC) agent.nameAr else agent.name,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = if (language == AppLanguage.ARABIC) agent.roleAr else agent.role,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 2
                                )
                            }
                        }
                    }
                }
            }
        }

        // Recent Build Release Banner
        item {
            Card(
                onClick = { onNavigateTab(NavigationTab.BUILD_RELEASE) },
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Icon(
                            imageVector = Icons.Default.Android,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = if (language == AppLanguage.ARABIC) "نظام البناء والإصدار (Build & Release)" else "Build & Release Engine",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = if (recentRelease != null) {
                                    if (language == AppLanguage.ARABIC) "آخر إصدار: v${recentRelease.versionName} (${recentRelease.buildType}) - جاهز للتوزيع" else "Latest Release: v${recentRelease.versionName} (${recentRelease.buildType}) - Ready"
                                } else {
                                    if (language == AppLanguage.ARABIC) "جاهز لبناء وتوقيع حزم APK و AAB" else "Ready to build & sign APK / AAB bundles"
                                },
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                            )
                        }
                    }
                    Button(
                        onClick = { onNavigateTab(NavigationTab.BUILD_RELEASE) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            text = if (language == AppLanguage.ARABIC) "البناء والإصدار" else "Build Release",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }

        // Recent Tasks Overview
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (language == AppLanguage.ARABIC) "أحدث المهام والتنفيذ المباشر" else "Recent Tasks & Execution",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Button(
                    onClick = { onNavigateTab(NavigationTab.TASKS) },
                    colors = ButtonDefaults.textButtonColors()
                ) {
                    Text(
                        text = if (language == AppLanguage.ARABIC) "عرض الكل" else "View All",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }

        items(tasks.take(3)) { task ->
            TaskSummaryCard(task = task, language = language) {
                onNavigateTab(NavigationTab.TASKS)
            }
        }
    }
}

@Composable
fun MetricBadge(
    title: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun TaskSummaryCard(
    task: AgentTaskEntity,
    language: AppLanguage,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (language == AppLanguage.ARABIC) task.titleAr else task.title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (task.status == "COMPLETED") Color(0xFF10B981).copy(alpha = 0.15f)
                            else MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = task.status,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = if (task.status == "COMPLETED") Color(0xFF10B981) else MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = { task.progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = if (task.status == "COMPLETED") Color(0xFF10B981) else MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = task.outputResult.take(120) + if (task.outputResult.length > 120) "..." else "",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
