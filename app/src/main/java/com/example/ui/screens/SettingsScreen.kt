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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.BuildConfig
import com.example.ui.AppLanguage

@Composable
fun SettingsScreen(
    selectedModel: String,
    language: AppLanguage,
    onSetModel: (String) -> Unit,
    onToggleLanguage: () -> Unit,
    modifier: Modifier = Modifier
) {
    val apiKeyConfigured = try {
        BuildConfig.GEMINI_API_KEY.isNotBlank() && BuildConfig.GEMINI_API_KEY != "MY_GEMINI_API_KEY"
    } catch (e: Exception) {
        false
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column {
                Text(
                    text = if (language == AppLanguage.ARABIC) "إعدادات النظام والنماذج الذكية" else "System Settings & Gemini Models",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (language == AppLanguage.ARABIC) "تحديد نماذج الاستدلال ومفاتيح الأمان والسلامة" else "Configure AI reasoning engines & secret API credentials.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Gemini AI Model Selector
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Psychology,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (language == AppLanguage.ARABIC) "اختيار نموذج الذكاء الاصطناعي (Gemini AI Model)" else "Select Active Gemini AI Model",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    ModelSelectionRow(
                        modelKey = "gemini-3.5-flash",
                        modelTitle = "Gemini 3.5 Flash",
                        modelDesc = if (language == AppLanguage.ARABIC) "نموذج فائق السرعة والكفاءة للمهام اليومية والتنفيذ السريع" else "Ultra-fast & responsive model for routine tasks & quick execution.",
                        isSelected = selectedModel == "gemini-3.5-flash",
                        onSelect = { onSetModel("gemini-3.5-flash") }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ModelSelectionRow(
                        modelKey = "gemini-3.1-pro-preview",
                        modelTitle = "Gemini 3.1 Pro Preview",
                        modelDesc = if (language == AppLanguage.ARABIC) "نموذج استدلال معقد وعميق للمهام التنفيذية والتخطيط الهيكلي" else "Advanced reasoning engine for complex multi-stage tasks.",
                        isSelected = selectedModel == "gemini-3.1-pro-preview",
                        onSelect = { onSetModel("gemini-3.1-pro-preview") }
                    )
                }
            }
        }

        // Secrets & API Key Status Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Key,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (language == AppLanguage.ARABIC) "حالة المفاتيح والأسرار البرمجية" else "Secrets & API Key Security",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    if (apiKeyConfigured) Color(0xFF10B981).copy(alpha = 0.15f)
                                    else MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = if (apiKeyConfigured) "Secrets Active" else "Simulated Mode",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = if (apiKeyConfigured) Color(0xFF10B981) else MaterialTheme.colorScheme.secondary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = if (apiKeyConfigured) {
                            if (language == AppLanguage.ARABIC) "مفتاح Gemini API متصل بأمان عبر لوحة الأسرار (Secrets Panel) في AI Studio."
                            else "Gemini API key is securely injected via AI Studio Secrets Panel."
                        } else {
                            if (language == AppLanguage.ARABIC) "يعمل النظام حالياً بالمحرك التنفيذي المحلي التفاعلي المحاكي. يمكنك إضافة GEMINI_API_KEY في Secrets Panel لتمكين التوليد المباشر."
                            else "Running in simulated executive mode. Configure GEMINI_API_KEY in the Secrets Panel for live REST generation."
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Platform & Architecture Info
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (language == AppLanguage.ARABIC) "معلومات النظام والترخيص" else "Platform & System Specs",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    InfoSpecRow("Application ID", "com.aistudio.digitalagent.xqvztr")
                    InfoSpecRow("Database Engine", "Room SQLite Database + KSP")
                    InfoSpecRow("Build System", "Android Gradle Plugin 9.1 + Secrets Plugin")
                    InfoSpecRow("UI Toolkit", "Jetpack Compose M3 (Material Design 3)")
                }
            }
        }
    }
}

@Composable
fun ModelSelectionRow(
    modelKey: String,
    modelTitle: String,
    modelDesc: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Surface(
        onClick = onSelect,
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(selected = isSelected, onClick = onSelect)
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = modelTitle,
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = modelDesc,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
fun InfoSpecRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
