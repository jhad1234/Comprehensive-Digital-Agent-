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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudQueue
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.ConnectorEntity
import com.example.data.local.ExtensionEntity
import com.example.ui.AppLanguage

@Composable
fun ConnectorsHubScreen(
    connectors: List<ConnectorEntity>,
    extensions: List<ExtensionEntity>,
    language: AppLanguage,
    onToggleConnector: (String, Boolean) -> Unit,
    onToggleExtension: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedSection by remember { mutableStateOf("CONNECTORS") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Section Header
        item {
            Column {
                Text(
                    text = if (language == AppLanguage.ARABIC) "مركز الموصلات والتكملات (Connectors & Extensions)" else "Connectors & Extensions Hub",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (language == AppLanguage.ARABIC) "بنية معيارية تسمح بالربط الديناميكي مع كافة الأنظمة والخدمات الخارجية بدون إعادة بناء" else "Modular architecture integrating external APIs, systems & capabilities dynamically.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChipPill(
                        label = if (language == AppLanguage.ARABIC) "الموصلات الخارجية (${connectors.size})" else "Connectors (${connectors.size})",
                        isSelected = selectedSection == "CONNECTORS",
                        onClick = { selectedSection = "CONNECTORS" }
                    )
                    FilterChipPill(
                        label = if (language == AppLanguage.ARABIC) "التكملات والمهارات (${extensions.size})" else "Extensions (${extensions.size})",
                        isSelected = selectedSection == "EXTENSIONS",
                        onClick = { selectedSection = "EXTENSIONS" }
                    )
                }
            }
        }

        if (selectedSection == "CONNECTORS") {
            items(connectors, key = { it.connectorKey }) { connector ->
                ConnectorCardItem(
                    connector = connector,
                    language = language,
                    onToggle = { isChecked -> onToggleConnector(connector.connectorKey, isChecked) }
                )
            }
        } else {
            items(extensions, key = { it.extensionKey }) { extension ->
                ExtensionCardItem(
                    extension = extension,
                    language = language,
                    onToggle = { isChecked -> onToggleExtension(extension.extensionKey, isChecked) }
                )
            }
        }
    }
}

@Composable
fun ConnectorCardItem(
    connector: ConnectorEntity,
    language: AppLanguage,
    onToggle: (Boolean) -> Unit
) {
    val icon = when (connector.category) {
        "EMAIL_CALENDAR" -> Icons.Default.Email
        "CLOUD_STORAGE" -> Icons.Default.CloudQueue
        "DATABASE" -> Icons.Default.Storage
        "ERP_CRM" -> Icons.Default.Work
        "MESSAGING" -> Icons.Default.Message
        "WEB_SEARCH" -> Icons.Default.Language
        "DEV_CLI" -> Icons.Default.Code
        "IOT" -> Icons.Default.Sensors
        else -> Icons.Default.Hub
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
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
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(
                            if (connector.isEnabled) MaterialTheme.colorScheme.primaryContainer
                            else MaterialTheme.colorScheme.surfaceVariant
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = if (connector.isEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = if (language == AppLanguage.ARABIC) connector.nameAr else connector.name,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = if (language == AppLanguage.ARABIC) connector.descriptionAr else connector.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Switch(
                checked = connector.isEnabled,
                onCheckedChange = onToggle,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                    checkedTrackColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}

@Composable
fun ExtensionCardItem(
    extension: ExtensionEntity,
    language: AppLanguage,
    onToggle: (Boolean) -> Unit
) {
    val icon = when (extension.category) {
        "TEXT_AI" -> Icons.Default.AutoAwesome
        "VISION_OCR" -> Icons.Default.Analytics
        "CODE_RUNNER" -> Icons.Default.Code
        "AUDIO_SPEECH" -> Icons.Default.Mic
        "TRANSLATION" -> Icons.Default.Translate
        "DATA_ANALYTICS" -> Icons.Default.Analytics
        "MEMORY" -> Icons.Default.Memory
        else -> Icons.Default.Extension
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
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
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(
                            if (extension.isEnabled) MaterialTheme.colorScheme.secondaryContainer
                            else MaterialTheme.colorScheme.surfaceVariant
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = if (extension.isEnabled) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = if (language == AppLanguage.ARABIC) extension.nameAr else extension.name,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = if (language == AppLanguage.ARABIC) extension.descriptionAr else extension.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Switch(
                checked = extension.isEnabled,
                onCheckedChange = onToggle,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                    checkedTrackColor = MaterialTheme.colorScheme.secondary
                )
            )
        }
    }
}
