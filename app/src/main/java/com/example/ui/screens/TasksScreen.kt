package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Task
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.AgentTaskEntity
import com.example.ui.AppLanguage

@Composable
fun TasksScreen(
    tasks: List<AgentTaskEntity>,
    language: AppLanguage,
    onDeleteTask: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf("ALL") }

    val filteredTasks = when (selectedFilter) {
        "IN_PROGRESS" -> tasks.filter { it.status == "IN_PROGRESS" }
        "COMPLETED" -> tasks.filter { it.status == "COMPLETED" }
        "DEV_BUILD" -> tasks.filter { it.category == "DEV_BUILD" }
        else -> tasks
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header & Filter Row
        item {
            Column {
                Text(
                    text = if (language == AppLanguage.ARABIC) "إدارة المهام المتقدمة وسجل التنفيذ" else "Task Planner & Execution Log",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (language == AppLanguage.ARABIC) "متابعة الخطوات المتسلسلة والمخرجات الناتجة عن الوكيل الرقمي" else "Monitor multi-step execution logs, AI reasoning, and outputs.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChipPill(
                        label = if (language == AppLanguage.ARABIC) "الكل (${tasks.size})" else "All (${tasks.size})",
                        isSelected = selectedFilter == "ALL",
                        onClick = { selectedFilter = "ALL" }
                    )
                    FilterChipPill(
                        label = if (language == AppLanguage.ARABIC) "جارٍ التنفيذ" else "In Progress",
                        isSelected = selectedFilter == "IN_PROGRESS",
                        onClick = { selectedFilter = "IN_PROGRESS" }
                    )
                    FilterChipPill(
                        label = if (language == AppLanguage.ARABIC) "المكتملة" else "Completed",
                        isSelected = selectedFilter == "COMPLETED",
                        onClick = { selectedFilter = "COMPLETED" }
                    )
                    FilterChipPill(
                        label = if (language == AppLanguage.ARABIC) "بناء التطبيق" else "Dev Builds",
                        isSelected = selectedFilter == "DEV_BUILD",
                        onClick = { selectedFilter = "DEV_BUILD" }
                    )
                }
            }
        }

        if (filteredTasks.isEmpty()) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Task,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = if (language == AppLanguage.ARABIC) "لا توجد مهام مسجلة في هذه الفئة" else "No tasks found in this filter",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        } else {
            items(filteredTasks, key = { it.id }) { task ->
                TaskDetailCard(
                    task = task,
                    language = language,
                    onDelete = { onDeleteTask(task.id) }
                )
            }
        }
    }
}

@Composable
fun FilterChipPill(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
        contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium.copy(fontSize = 12.sp),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

@Composable
fun TaskDetailCard(
    task: AgentTaskEntity,
    language: AppLanguage,
    onDelete: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
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
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(
                                if (task.status == "COMPLETED") Color(0xFF10B981).copy(alpha = 0.15f)
                                else MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (task.status == "COMPLETED") Icons.Default.CheckCircle else Icons.Default.HourglassTop,
                            contentDescription = null,
                            tint = if (task.status == "COMPLETED") Color(0xFF10B981) else MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = if (language == AppLanguage.ARABIC) task.titleAr else task.title,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${task.assignedAgent} • ${task.category}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onDelete) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = "Expand",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            LinearProgressIndicator(
                progress = { task.progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = if (task.status == "COMPLETED") Color(0xFF10B981) else MaterialTheme.colorScheme.primary
            )

            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                ) {
                    Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = if (language == AppLanguage.ARABIC) "سجل الاستدلال والتنفيذ (Executive Reasoning):" else "Executive Reasoning Log:",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = task.reasoningLog,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = if (language == AppLanguage.ARABIC) "المخرجات والنتائج الناتجة (Generated Output):" else "Generated Output Result:",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF10B981)
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = task.outputResult,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
        }
    }
}
