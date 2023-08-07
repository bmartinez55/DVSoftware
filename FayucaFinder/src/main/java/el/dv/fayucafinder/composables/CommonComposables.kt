package el.dv.fayucafinder.composables

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.imePadding
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import el.dv.compose_uikit.extension.ifThen
import el.dv.compose_uikit.theme.fayucafinder.FayucaFinderTheme

@Composable
fun FayucaFinderScaffold(
    addStatusBarPadding: Boolean = true,
    addNavigationBarPadding: Boolean = true,
    addImePadding: Boolean = true,
    topBar: @Composable (scaffoldState: ScaffoldState, scrollState: ScrollState) -> Unit,
    content: @Composable (
        paddingValues: PaddingValues,
        scaffoldState: ScaffoldState,
        scrollState: ScrollState
    ) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberScrollState()

    ProvideWindowInsets {
        FayucaFinderTheme {
            Scaffold(
                modifier = Modifier
                    .ifThen(addStatusBarPadding) { Modifier.statusBarsPadding() }
                    .ifThen(addNavigationBarPadding) { Modifier.navigationBarsPadding() }
                    .ifThen(addImePadding) { Modifier.imePadding() },
                topBar = {
                    topBar(
                        scaffoldState = scaffoldState,
                        scrollState = scrollState
                    )
                }
            ) { paddingValues ->
                content(
                    paddingValues = paddingValues,
                    scaffoldState = scaffoldState,
                    scrollState = scrollState
                )
            }
        }
    }
}
