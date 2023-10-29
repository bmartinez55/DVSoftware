package el.dv.compose_uikit.theme.dvpropeerties

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import el.dv.compose_uikit.theme.Dimens

val DVPropertiesLightTypography = Typography(
    h1 = TextStyle(
        fontSize = Dimens.FontSize.x2large,
        fontWeight = FontWeight.Bold,
        lineHeight = 1.3.em,
        color = DVPropertiesColor.onSurface
    ),
    h2 = TextStyle(
        fontSize = Dimens.FontSize.xlarge,
        fontWeight = FontWeight.Bold,
        lineHeight = 1.5.em,
        color = DVPropertiesColor.onSurface
    ),
    h3 = TextStyle(
        fontSize = Dimens.FontSize.large,
        fontWeight = FontWeight.Bold,
        lineHeight = 1.5.em,
        color = DVPropertiesColor.onSurface
    ),
    button = TextStyle(
        fontSize = Dimens.FontSize.large,
        fontWeight = FontWeight.Bold,
        color = DVPropertiesColor.onSurface
    ),
    subtitle1 = TextStyle(
        fontSize = Dimens.FontSize.medium,
        fontWeight = FontWeight.Bold,
        lineHeight = 1.6.em,
        color = DVPropertiesColor.onSurface
    ),
    body1 = TextStyle(
        fontSize = Dimens.FontSize.medium,
        lineHeight = 1.6.em,
        color = DVPropertiesColor.onSurface
    ),
    body2 = TextStyle(
        fontSize = Dimens.FontSize.medium,
        fontWeight = FontWeight.Bold,
        lineHeight = 1.6.em,
        color = DVPropertiesColor.onSurface
    ),
    caption = TextStyle(
        fontSize = Dimens.FontSize.small,
        lineHeight = 1.5.em,
        color = DVPropertiesColor.onSurfaceVariant
    )
)

val DVPropertiesDarkTypography = DVPropertiesLightTypography
