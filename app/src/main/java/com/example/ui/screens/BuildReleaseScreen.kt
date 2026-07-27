package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FolderZip
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.example.data.buildengine.BuildReleaseEngine
import com.example.data.local.BuildReleaseEntity
import com.example.ui.AppLanguage
import com.example.ui.BuildUiState

@Composable
fun BuildReleaseScreen(
    buildUiState: BuildUiState,
    buildReleases: List<BuildReleaseEntity>,
    language: AppLanguage,
    onRunDiagnostics: () -> Unit,
    onStartBuild: (versionName: String, versionCode: Int, buildType: String, changelog: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var versionNameInput by remember { mutableStateOf("1.1.0") }
    var versionCodeInput by remember { mutableStateOf("2") }
    var selectedBuildType by remember { mutableStateOf("RELEASE_APK") } // RELEASE_APK or PLAY_STORE_AAB
    var changelogInput by remember { mutableStateOf("General Digital Agent stable release build with V2/V3 digital signing.") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // System Banner Header
        item {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Android,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (language == AppLanguage.ARABIC) "نظام البناء والإصدار (Build & Release)" else "Android Build & Release System",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (language == AppLanguage.ARABIC) "تحويل الوكيل الرقمي إلى حزم APK و AAB موثقة ومجتازة لفحوصات الأمان" else "Compiles Kotlin, resolves dependencies & builds signed release bundles.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }

        // Pre-flight Diagnostics Section
        item {
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Security,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (language == AppLanguage.ARABIC) "فحص الجاهزية والسلامة (Pre-flight Checks)" else "Pre-flight Diagnostics Checklist",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        IconButton(onClick = onRunDiagnostics) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Run Diagnostics",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    if (buildUiState.isRunningDiagnostics) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = if (language == AppLanguage.ARABIC) "جارٍ فحص بيئة البناء والشهادات..." else "Scanning manifest, dependencies & keystore signatures...",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        buildUiState.diagnostics.forEach { diagnostic ->
                            DiagnosticRowItem(diagnostic = diagnostic, language = language)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }

        // Release Build Configuration
        item {
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
                    Text(
                        text = if (language == AppLanguage.ARABIC) "تكوين إصدار الحزمة (Release Configuration)" else "Release Bundle Configuration",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Version Name & Code Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = versionNameInput,
                            onValueChange = { versionNameInput = it },
                            label = { Text(if (language == AppLanguage.ARABIC) "اسم الإصدار (Version Name)" else "Version Name") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = versionCodeInput,
                            onValueChange = { versionCodeInput = it },
                            label = { Text(if (language == AppLanguage.ARABIC) "رمز الإصدار (Version Code)" else "Version Code") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Build Type Selector
                    Text(
                        text = if (language == AppLanguage.ARABIC) "نوع حزمة الإصدار Target Artifact:" else "Select Target Artifact Type:",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilterChipPill(
                            label = if (language == AppLanguage.ARABIC) "تثبيت مباشر (Release APK)" else "Release APK Bundle",
                            isSelected = selectedBuildType == "RELEASE_APK",
                            onClick = { selectedBuildType = "RELEASE_APK" }
                        )
                        FilterChipPill(
                            label = if (language == AppLanguage.ARABIC) "متجر قوقل (Play Store AAB)" else "Google Play AAB Bundle",
                            isSelected = selectedBuildType == "PLAY_STORE_AAB",
                            onClick = { selectedBuildType = "PLAY_STORE_AAB" }
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = changelogInput,
                        onValueChange = { changelogInput = it },
                        label = { Text(if (language == AppLanguage.ARABIC) "ملاحظات الإصدار (Changelog)" else "Release Notes / Changelog") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        maxLines = 2
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Build Progress Display
                    if (buildUiState.isBuilding) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    MaterialTheme.colorScheme.surfaceVariant,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = buildUiState.currentStepLog,
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "${(buildUiState.buildProgress * 100).toInt()}%",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            LinearProgressIndicator(
                                progress = { buildUiState.buildProgress },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // Build Trigger Button
                    Button(
                        onClick = {
                            val code = versionCodeInput.toIntOrNull() ?: 1
                            onStartBuild(versionNameInput, code, selectedBuildType, changelogInput)
                        },
                        enabled = !buildUiState.isBuilding,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.RocketLaunch,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (language == AppLanguage.ARABIC) "تنفيذ البناء والتوقيع الإلكتروني (Build & Sign Release)" else "Execute Build & Sign Release Bundle",
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
            }
        }

        // Build History List
        item {
            Text(
                text = if (language == AppLanguage.ARABIC) "سجل حزم الإصدار المكتملة (Build Artifacts)" else "Generated Release Artifacts History",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        items(buildReleases) { release ->
            ReleaseArtifactCard(release = release, language = language)
        }
    }
}

@Composable
fun DiagnosticRowItem(
    diagnostic: BuildReleaseEngine.DiagnosticCheck,
    language: AppLanguage
) {
    val icon = when (diagnostic.status) {
        BuildReleaseEngine.DiagnosticStatus.PASSED -> Icons.Default.CheckCircle
        BuildReleaseEngine.DiagnosticStatus.WARNING -> Icons.Default.Warning
        BuildReleaseEngine.DiagnosticStatus.FAILED -> Icons.Default.Info
    }

    val iconColor = when (diagnostic.status) {
        BuildReleaseEngine.DiagnosticStatus.PASSED -> Color(0xFF10B981)
        BuildReleaseEngine.DiagnosticStatus.WARNING -> Color(0xFFF59E0B)
        BuildReleaseEngine.DiagnosticStatus.FAILED -> MaterialTheme.colorScheme.error
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(18.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = if (language == AppLanguage.ARABIC) diagnostic.nameAr else diagnostic.name,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = diagnostic.details,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ReleaseArtifactCard(
    release: BuildReleaseEntity,
    language: AppLanguage
) {
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF10B981).copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.FolderZip,
                            contentDescription = null,
                            tint = Color(0xFF10B981),
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "${release.buildType} v${release.versionName} (Build ${release.versionCode})",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${release.artifactSizeMb} MB • ${release.signatureStatus}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF10B981).copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = release.status,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF10B981)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Checksum: ${release.sha256Checksum}",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Notes: ${release.changelog}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
