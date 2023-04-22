package be.casperverswijvelt.unifiedinternetqs.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PreferenceEntry(
    icon: @Composable () -> Unit = {},
    title: String,
    subTitle: String? = null,
    checked: Boolean? = null,
    onClicked: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clickable { onClicked() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(40.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            icon()
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                fontSize = 18.sp,
                text = title
            )
            subTitle?.let {
                Text(
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    text = subTitle,
                    lineHeight = 18.sp
                )
            }
        }
        checked?.let {
            Switch(
                modifier = Modifier.padding(start = 8.dp),
                checked = it,
                onCheckedChange = null,
            )
        }
    }
}

@Composable
fun RadioEntry(
    title: String,
    enabled: Boolean = false,
    onClicked: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClicked() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(50.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            RadioButton(selected = enabled, onClick = { onClicked() })
        }
        Text(
            modifier = Modifier.padding(bottom = 4.dp).weight(1f),
            fontSize = 18.sp,
            text = title
        )
    }
}

@Composable
fun TogglePreferenceEntry(
    icon: @Composable () -> Unit = {},
    title: String,
    subTitle: String? = null,
    checked: Boolean,
    onToggled: (Boolean) -> Unit = {}
) {
    PreferenceEntry(
        icon = icon,
        title = title,
        subTitle = subTitle,
        checked = checked,
        onClicked = {
            onToggled(!checked)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LargeTopBarPage(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text(title) },
                scrollBehavior = scrollBehavior
            )
        },
    ) {
        Column (
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .verticalScroll(rememberScrollState()),
            content = content
        )
    }
}

@Composable
fun ColorPalette () {
    val colors = arrayOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.onPrimary,
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.onPrimaryContainer,
        MaterialTheme.colorScheme.inversePrimary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.onSecondary,
        MaterialTheme.colorScheme.secondaryContainer,
        MaterialTheme.colorScheme.onSecondaryContainer,
        MaterialTheme.colorScheme.tertiary,
        MaterialTheme.colorScheme.onTertiary,
        MaterialTheme.colorScheme.tertiaryContainer,
        MaterialTheme.colorScheme.onTertiaryContainer,
        MaterialTheme.colorScheme.background,
        MaterialTheme.colorScheme.onBackground,
        MaterialTheme.colorScheme.surface,
        MaterialTheme.colorScheme.onSurface,
        MaterialTheme.colorScheme.surfaceVariant,
        MaterialTheme.colorScheme.onSurfaceVariant,
        MaterialTheme.colorScheme.surfaceTint,
        MaterialTheme.colorScheme.surfaceTint,
        MaterialTheme.colorScheme.surfaceTint,
        MaterialTheme.colorScheme.inverseSurface,
        MaterialTheme.colorScheme.inverseOnSurface,
        MaterialTheme.colorScheme.error,
        MaterialTheme.colorScheme.onError,
        MaterialTheme.colorScheme.errorContainer,
        MaterialTheme.colorScheme.onErrorContainer,
        MaterialTheme.colorScheme.outline,
        MaterialTheme.colorScheme.outlineVariant,
        MaterialTheme.colorScheme.scrim


    )
    colors.forEach {color ->
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .background(color))
    }
}