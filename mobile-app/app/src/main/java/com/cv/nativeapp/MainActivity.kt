package com.cv.nativeapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// =========================================================================
// PALETA DE COLORES PREMIUM (DARK MODE MATERIAL DESIGN 3)
// =========================================================================
val DarkBg = Color(0F0F172A)
val SurfaceBg = Color(0F1E293B)
val SurfaceCard = Color(0F334155)
val AccentPrimary = Color(0F6366F1) // Indigo
val AccentSecondary = Color(0F10B981) // Teal / Emerald
val TextWhite = Color(0FF8FAFC)
val TextMuted = Color(0F94A3B8)
val GoldBadge = Color(0FF59E0B)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BioAppNativeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = DarkBg
                ) {
                    BioAppMainScreen()
                }
            }
        }
    }
}

@Composable
fun BioAppNativeTheme(content: @Composable () -> Unit) {
    val colorScheme = darkColorScheme(
        background = DarkBg,
        surface = SurfaceBg,
        primary = AccentPrimary,
        secondary = AccentSecondary,
        onBackground = TextWhite,
        onSurface = TextWhite
    )
    MaterialTheme(colorScheme = colorScheme, content = content)
}

// =========================================================================
// MODELOS DE DATOS DE LA HOJA DE VIDA
// =========================================================================
data class WorkExperience(
    val role: String,
    val company: String,
    val period: String,
    val description: String,
    val achievements: List<String>,
    val tags: List<String>
)

data class Education(
    val title: String,
    val institution: String,
    val period: String,
    val details: String
)

data class Skill(
    val name: String,
    val percentage: Float, // 0.0f to 1.0f
    val category: String // Mobile, Backend, DevOps, Architecture
)

data class Certification(
    val title: String,
    val issuer: String,
    val year: String
)

// =========================================================================
// PANTALLA PRINCIPAL INTERACTIVA
// =========================================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BioAppMainScreen() {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Perfil", "Experiencia", "Educación", "Skills")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Badge,
                            contentDescription = null,
                            tint = AccentSecondary,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = "BioApp Native",
                            fontWeight = FontWeight.Bold,
                            color = TextWhite,
                            fontSize = 20.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SurfaceBg)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = SurfaceBg) {
                tabTitles.forEachIndexed { index, title ->
                    val icon = when (index) {
                        0 -> Icons.Default.Person
                        1 -> Icons.Default.Work
                        2 -> Icons.Default.School
                        else -> Icons.Default.Code
                    }
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = { Icon(icon, contentDescription = title) },
                        label = { Text(title) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = TextWhite,
                            indicatorColor = AccentPrimary,
                            unselectedIconColor = TextMuted,
                            unselectedTextColor = TextMuted,
                            selectedTextColor = TextWhite
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(DarkBg)
        ) {
            // Header del Postulante
            ProfileHeaderCard()

            // Navegación por pestañas
            AnimatedContent(
                targetState = selectedTab,
                label = "TabTransition"
            ) { targetTab ->
                when (targetTab) {
                    0 -> ProfileTabContent()
                    1 -> ExperienceTabContent()
                    2 -> EducationTabContent()
                    3 -> SkillsTabContent()
                }
            }
        }
    }
}

// =========================================================================
// COMPONENTE: HEADER DE PERFIL CON ACCIONES RÁPIDAS
// =========================================================================
@Composable
fun ProfileHeaderCard() {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceBg)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Avatar circular
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(AccentPrimary, AccentSecondary)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Foto Perfil",
                        tint = TextWhite,
                        modifier = Modifier.size(44.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Ing. Desarrollador Senior",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                    Text(
                        text = "Arquitecto de Software & Mobile Dev",
                        fontSize = 13.sp,
                        color = TextMuted
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Chip Status
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = AccentSecondary.copy(alpha = 0.2f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, AccentSecondary)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(AccentSecondary)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Disponible para Contratación",
                                fontSize = 10.sp,
                                color = AccentSecondary,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = SurfaceCard)
            Spacer(modifier = Modifier.height(12.dp))

            // Botones de acción directa (Quick Action Buttons)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                QuickActionButton(
                    icon = Icons.Default.Email,
                    label = "Email",
                    onClick = {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:contacto@profesional.com")
                        }
                        runCatching { context.startActivity(intent) }.onFailure {
                            Toast.makeText(context, "contacto@profesional.com", Toast.LENGTH_SHORT).show()
                        }
                    }
                )

                QuickActionButton(
                    icon = Icons.Default.Phone,
                    label = "Llamar",
                    onClick = {
                        Toast.makeText(context, "Tel: +593 99 999 9999", Toast.LENGTH_SHORT).show()
                    }
                )

                QuickActionButton(
                    icon = Icons.Default.Code,
                    label = "GitHub",
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com"))
                        runCatching { context.startActivity(intent) }
                    }
                )

                QuickActionButton(
                    icon = Icons.Default.Share,
                    label = "vCard",
                    onClick = {
                        Toast.makeText(context, "Contacto exportado exitosamente", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}

@Composable
fun QuickActionButton(icon: ImageVector, label: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Surface(
            shape = CircleShape,
            color = SurfaceCard,
            modifier = Modifier.size(40.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(icon, contentDescription = label, tint = AccentPrimary, modifier = Modifier.size(20.dp))
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, fontSize = 11.sp, color = TextMuted)
    }
}

// =========================================================================
// TAB 1: PERFIL PROFESIONAL & ESTADÍSTICAS
// =========================================================================
@Composable
fun ProfileTabContent() {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
    ) {
        item {
            Text(
                text = "Resumen Ejecutivo",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceBg),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Ingeniero de Software con experiencia sólida en el desarrollo de aplicaciones móviles nativas para Android (Kotlin, Jetpack Compose), arquitectura Clean Architecture y contenedorización con Docker. Enfocado en alto rendimiento, usabilidad (Material 3) y entrega continua bajo estándares rigurosos de software.",
                    fontSize = 13.sp,
                    color = TextMuted,
                    lineHeight = 18.sp,
                    modifier = Modifier.padding(14.dp)
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Métricas de Desempeño",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatCard(value = "5+", label = "Años Exp.", modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(8.dp))
                StatCard(value = "20+", label = "Proyectos", modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(8.dp))
                StatCard(value = "6", label = "Certificados", modifier = Modifier.weight(1f))
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Competencias Clave",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            val competencies = listOf(
                "Desarrollo Nativo Android con Kotlin & Jetpack Compose",
                "Patrones de Diseño MVVM / MVI y Clean Architecture",
                "Virtualización y Entornos Aislados con Docker & Linux",
                "Integración Continua (CI/CD) & Pruebas Unitarias/UI",
                "Gestión de Requisitos bajo Estándar IEEE 830"
            )

            competencies.forEach { item ->
                Row(
                    modifier = Modifier.padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = AccentSecondary, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = item, fontSize = 13.sp, color = TextWhite)
                }
            }
        }
    }
}

@Composable
fun StatCard(value: String, label: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = SurfaceBg),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = value, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = AccentPrimary)
            Text(text = label, fontSize = 11.sp, color = TextMuted)
        }
    }
}

// =========================================================================
// TAB 2: EXPERIENCIA LABORAL
// =========================================================================
@Composable
fun ExperienceTabContent() {
    val experiences = listOf(
        WorkExperience(
            role = "Senior Native Android Engineer",
            company = "Tech Solutions Corp",
            period = "2023 - Presente",
            description = "Liderazgo técnico en la arquitectura nativa de apps empresariales.",
            achievements = listOf(
                "Reducción del tiempo de inicio (Cold Start) en un 40%.",
                "Implementación de Jetpack Compose y migración a Clean Architecture."
            ),
            tags = listOf("Kotlin", "Jetpack Compose", "Docker", "Coroutines")
        ),
        WorkExperience(
            role = "Mobile Software Developer",
            company = "AppInnovate Studio",
            period = "2021 - 2023",
            description = "Desarrollo y mantenimiento de soluciones móviles nativas para clientes bancarios.",
            achievements = listOf(
                "Implementación de seguridad local cifrada con EncryptedSharedPreferences.",
                "Integración de SDKs de pagos y notificaciones Push."
            ),
            tags = listOf("Android SDK", "Room DB", "REST APIs", "Git")
        )
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
    ) {
        items(experiences) { exp ->
            ExperienceCard(exp)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun ExperienceCard(exp: WorkExperience) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = SurfaceBg),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = exp.role, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextWhite)
                Surface(
                    shape = RoundedCornerShape(50),
                    color = AccentPrimary.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = exp.period,
                        fontSize = 10.sp,
                        color = AccentPrimary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Text(text = exp.company, fontSize = 13.sp, color = AccentSecondary, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = exp.description, fontSize = 12.sp, color = TextMuted)

            Spacer(modifier = Modifier.height(8.dp))
            exp.achievements.forEach { achievement ->
                Row(modifier = Modifier.padding(vertical = 2.dp)) {
                    Text(text = "• ", color = AccentSecondary, fontWeight = FontWeight.Bold)
                    Text(text = achievement, fontSize = 11.sp, color = TextWhite)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            LazyRow {
                items(exp.tags) { tag ->
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = SurfaceCard,
                        modifier = Modifier.padding(end = 6.dp)
                    ) {
                        Text(
                            text = tag,
                            fontSize = 10.sp,
                            color = TextWhite,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

// =========================================================================
// TAB 3: EDUCACIÓN Y CERTIFICACIONES
// =========================================================================
@Composable
fun EducationTabContent() {
    val educationList = listOf(
        Education(
            title = "Ingeniería en Software / Sistemas",
            institution = "Universidad Técnica Nacional",
            period = "2019 - 2024",
            details = "Graduado con honores. Especialización en Sistemas Distribuidos y Desarrollo Móvil."
        )
    )

    val certifications = listOf(
        Certification("Android Certified Application Developer", "Google / Associate", "2023"),
        Certification("Docker Certified Associate (DCA)", "Docker Inc.", "2023"),
        Certification("Scrum Master Certified (SMC)", "Scrum Alliance", "2022")
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
    ) {
        item {
            Text(text = "Formación Académica", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextWhite, modifier = Modifier.padding(vertical = 8.dp))
        }

        items(educationList) { edu ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = SurfaceBg),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(text = edu.title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextWhite)
                    Text(text = edu.institution, fontSize = 12.sp, color = AccentSecondary)
                    Text(text = edu.period, fontSize = 11.sp, color = TextMuted)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = edu.details, fontSize = 12.sp, color = TextMuted)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        item {
            Text(text = "Insignias y Certificaciones", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextWhite, modifier = Modifier.padding(vertical = 8.dp))
        }

        items(certifications) { cert ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceBg),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.WorkspacePremium, contentDescription = null, tint = GoldBadge, modifier = Modifier.size(32.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(text = cert.title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextWhite)
                        Text(text = "${cert.issuer} • ${cert.year}", fontSize = 11.sp, color = TextMuted)
                    }
                }
            }
        }
    }
}

// =========================================================================
// TAB 4: HABILIDADES TÉCNICAS (SKILLS MATRIX)
// =========================================================================
@Composable
fun SkillsTabContent() {
    var selectedCategory by remember { mutableStateOf("Todas") }
    val categories = listOf("Todas", "Mobile", "Backend", "DevOps")

    val skills = listOf(
        Skill("Kotlin / Android Native", 0.95f, "Mobile"),
        Skill("Jetpack Compose UI", 0.90f, "Mobile"),
        Skill("Docker & Virtualización", 0.85f, "DevOps"),
        Skill("Clean Architecture & MVVM", 0.92f, "Architecture"),
        Skill("REST APIs / Microservicios", 0.80f, "Backend"),
        Skill("Git / CI/CD Pipelines", 0.88f, "DevOps")
    )

    val filteredSkills = if (selectedCategory == "Todas") skills else skills.filter { it.category == selectedCategory }

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        // Filter Chips
        LazyRow(modifier = Modifier.padding(vertical = 8.dp)) {
            items(categories) { cat ->
                FilterChip(
                    selected = selectedCategory == cat,
                    onClick = { selectedCategory = cat },
                    label = { Text(cat) },
                    modifier = Modifier.padding(end = 6.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = AccentPrimary,
                        selectedLabelColor = TextWhite
                    )
                )
            }
        }

        LazyColumn {
            items(filteredSkills) { skill ->
                SkillBarItem(skill)
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun SkillBarItem(skill: Skill) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = SurfaceBg),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = skill.name, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextWhite)
                Text(text = "${(skill.percentage * 100).toInt()}%", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = AccentSecondary)
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Barra de Progreso Personalizada
            LinearProgressIndicator(
                progress = { skill.percentage },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(CircleShape),
                color = AccentPrimary,
                trackColor = SurfaceCard,
            )
        }
    }
}
