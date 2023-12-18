package el.dv.compose.widgets

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import el.dv.compose.extension.ifThen
import el.dv.compose.theme.dvpropeerties.DVPropertiesTheme
import el.dv.compose.theme.dvpropeerties.dVPropertiesTextFieldColors

@Composable
fun DVTextInputField(
    value: String,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    matchScreenWidth: Boolean = true,
    textStyle: TextStyle = MaterialTheme.typography.body1,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    label: @Composable (() -> Unit)? = null,
    placeHolder: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    onValueChanged: (String) -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    colors: TextFieldColors = dVPropertiesTextFieldColors()
) {
    val focusRequester = remember { FocusRequester() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    TextField(
        value = value,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        label = label,
        placeholder = placeHolder,
        modifier = modifier
            .ifThen(matchScreenWidth) {
                Modifier.fillMaxWidth()
            }
            .ifThen(!matchScreenWidth) {
                Modifier.width(100.dp)
            },
        onValueChange = {
            onValueChanged(it)
        },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        colors = colors
    )
}

@Preview
@Composable
fun DVTextInputFieldPreview() {
    DVPropertiesTheme {
        DVTextInputField(value = "", matchScreenWidth = false, label = { Text(text = "Test")}, onValueChanged = {}, leadingIcon = null, trailingIcon = null)
    }
}
