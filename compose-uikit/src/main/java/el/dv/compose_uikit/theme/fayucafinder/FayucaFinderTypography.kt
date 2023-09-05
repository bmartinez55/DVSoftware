package el.dv.compose_uikit.theme.fayucafinder

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import el.dv.compose_uikit.theme.Dimens

val FayucaFinderLightTypography = Typography(
    h1 = TextStyle(
        fontSize = Dimens.FontSize.x2large,
        fontWeight = FontWeight.Bold,
        lineHeight = 1.3.em,
        color = FayucaFinderColor.Palette.black
    ),
    h2 = TextStyle(
        fontSize = Dimens.FontSize.xlarge,
        fontWeight = FontWeight.Bold,
        lineHeight = 1.5.em,
        color = FayucaFinderColor.Palette.black
    ),
    h3 = TextStyle(
        fontSize = Dimens.FontSize.large,
        fontWeight = FontWeight.Bold,
        lineHeight = 1.5.em,
        color = FayucaFinderColor.Palette.black
    ),
    button = TextStyle(
        fontSize = Dimens.FontSize.large,
        fontWeight = FontWeight.Bold,
        color = FayucaFinderColor.Palette.black
    ),
    subtitle1 = TextStyle(
        fontSize = Dimens.FontSize.medium,
        fontWeight = FontWeight.Bold,
        lineHeight = 1.6.em,
        color = FayucaFinderColor.Palette.black
    ),
    body1 = TextStyle(
        fontSize = Dimens.FontSize.medium,
        lineHeight = 1.6.em,
        color = FayucaFinderColor.Palette.black
    ),
    body2 = TextStyle(
        fontSize = Dimens.FontSize.medium,
        fontWeight = FontWeight.Bold,
        lineHeight = 1.6.em,
        color = FayucaFinderColor.Palette.black
    ),
    caption = TextStyle(
        fontSize = Dimens.FontSize.small,
        lineHeight = 1.5.em,
        color = FayucaFinderColor.Palette.grey500
    )
)

val FayucaFinderDarkTypography = FayucaFinderLightTypography
