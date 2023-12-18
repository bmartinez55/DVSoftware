package el.dv.dvproperties.feature.newproperty.composables

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import el.dv.compose.theme.dvpropeerties.DVPropertiesTheme
import el.dv.compose.theme.paddingBottomX2Large
import el.dv.compose.theme.paddingBottomX4Large
import el.dv.compose.theme.paddingBottomXLarge
import el.dv.compose.theme.paddingEndLarge
import el.dv.compose.theme.paddingEndMedium
import el.dv.compose.theme.paddingEndSmall
import el.dv.compose.theme.paddingEndX4Large
import el.dv.compose.theme.paddingEndXLarge
import el.dv.compose.theme.paddingMedium
import el.dv.compose.widgets.BodyText
import el.dv.compose.widgets.CaptionText
import el.dv.compose.widgets.DVSpacer
import el.dv.compose.widgets.DVTextInputField
import el.dv.compose.widgets.FilledRoundedButton
import el.dv.compose.widgets.H3Text
import el.dv.dvproperties.R
import el.dv.presentation.R as PresentationR

@Composable
fun NewPropertyScreen(
    scrollState: ScrollState,
    address: MutableState<String>,
    bedroomDropDownClick: (String) -> Unit,
    bathroomDropDownClick: (String) -> Unit,
    onSubmitClick: () -> Unit
) {
    Column (
        modifier = Modifier
            .paddingMedium()
            .verticalScroll(scrollState, enabled = true)
    ) {
        H3Text(text = stringResource(id = R.string.add_property))
        
        CaptionText(text = stringResource(id = R.string.new_property_desc))

        DVSpacer(modifier = Modifier.paddingBottomX2Large())

        DVTextInputField(
            value = address.value,
            onValueChanged = {
                 address.value = it
            },
            label = { Text(text = stringResource(id = R.string.address))}
        )

        DVSpacer(modifier = Modifier.paddingBottomXLarge())

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DVTextInputField(
                value = address.value,
                onValueChanged = {
                    address.value = it
                },
                label = { Text(text = stringResource(id = R.string.city)) },
                matchScreenWidth = false
            )

            DVSpacer(modifier = Modifier.paddingEndXLarge())

            DVTextInputField(
                value = address.value,
                onValueChanged = {
                    address.value = it
                },
                label = { Text(text = stringResource(id = R.string.state)) },
                matchScreenWidth = false
            )

            DVSpacer(modifier = Modifier.paddingEndXLarge())

            DVTextInputField(
                value = address.value,
                onValueChanged = {
                    address.value = it
                },
                label = { Text(text = stringResource(id = R.string.zip_code)) },
                matchScreenWidth = false
            )
        }

        DVSpacer(modifier = Modifier.paddingBottomXLarge())

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BedroomDropDownMenu(
                dropDownList = bedroomsList,
                onClick = { bedroomDropDownClick(it) }
            )

            DVSpacer(modifier = Modifier.paddingEndLarge())

            BathroomDropDownMenu(
                dropDownList = bathroomList,
                onClick = { bathroomDropDownClick(it) }
            )
        }

        DVSpacer(modifier = Modifier.paddingBottomX4Large())

        FilledRoundedButton(
            onClick = { onSubmitClick() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = PresentationR.string.submit),
                color = MaterialTheme.colors.background
            )
        }
    }
}

@Composable
fun BedroomDropDownMenu(
    dropDownList: List<String> = emptyList(),
    onClick: (String) -> Unit
) {
    var expandedState by remember {
        mutableStateOf(false)
    }
    val dropDownValue = remember { mutableStateOf("") }
    val icon = when (expandedState) {
        true -> Icons.Filled.KeyboardArrowUp
        false -> Icons.Filled.KeyboardArrowDown
    }

    Column(
        modifier = Modifier
            .width(80.dp)
            .height(60.dp)
    ) {
        BodyText(text = stringResource(id = R.string.bedrooms))
        OutlinedTextField(
            value = dropDownValue.value,
            onValueChange = { dropDownValue.value = it},
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        expandedState = !expandedState
                    }
                )
            }
        )
        DropdownMenu(expanded = expandedState, onDismissRequest = { expandedState = false }) {
            dropDownList.forEach {
                DropdownMenuItem(
                    onClick = {
                        onClick(it)
                        dropDownValue.value = it
                        expandedState = false
                    },
                    enabled = true,
                    content = {
                        Text(text = it)
                    }
                )
            }
        }
    }
}

@Composable
fun BathroomDropDownMenu(
    dropDownList: List<String> = emptyList(),
    onClick: (String) -> Unit
) {
    var expandedState by remember {
        mutableStateOf(false)
    }
    val dropDownValue = remember { mutableStateOf("") }
    val icon = when (expandedState) {
        true -> Icons.Filled.KeyboardArrowUp
        false -> Icons.Filled.KeyboardArrowDown
    }

    Column(
        modifier = Modifier
            .width(80.dp)
            .height(60.dp)
    ) {
        BodyText(text = stringResource(id = R.string.bathrooms))
        OutlinedTextField(
            value = dropDownValue.value,
            onValueChange = { dropDownValue.value = it},
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        expandedState = !expandedState
                    }
                )
            }
        )
        DropdownMenu(expanded = expandedState, onDismissRequest = { expandedState = false }) {
            dropDownList.forEach {
                DropdownMenuItem(
                    onClick = {
                        onClick(it)
                        dropDownValue.value = it
                        expandedState = false
                    },
                    enabled = true,
                    content = {
                        Text(text = it)
                    }
                )
            }
        }
    }
}

@Preview(
    name = "NewPropertyScreenPreview",
    showSystemUi = true,
    device = "spec:width=1440px,height=3040px,dpi=320"
)
@Composable
fun NewPropertyScreenPreview() {
    DVPropertiesTheme {
        NewPropertyScreen(
            rememberScrollState(),
            remember { mutableStateOf("") },
            {},
            {},
            onSubmitClick = {}
        )
    }
}

private val bedroomsList = listOf("1", "2", "3", "4", "5", "6")

private val bathroomList = listOf("1", "1.5", "2", "2.5", "3", "3.5", "4")
