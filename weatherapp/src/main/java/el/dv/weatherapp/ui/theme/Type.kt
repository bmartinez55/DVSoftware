package el.dv.weatherapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import el.dv.compose.theme.Dimens
import el.dv.compose.theme.fayucafinder.FayucaFinderColor

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontSize = Dimens.FontSize.x2large,
        fontWeight = FontWeight.Bold,
        lineHeight = 1.3.em,
        color = FayucaFinderColor.Palette.black
    ),
    titleMedium = TextStyle(
        fontSize = Dimens.FontSize.xlarge,
        fontWeight = FontWeight.Bold,
        lineHeight = 1.5.em,
        color = FayucaFinderColor.Palette.black
    ),
    titleSmall = TextStyle(
        fontSize = Dimens.FontSize.large,
        fontWeight = FontWeight.Bold,
        lineHeight = 1.5.em,
        color = FayucaFinderColor.Palette.black
    ),
    bodyLarge = TextStyle(
        fontSize = Dimens.FontSize.medium,
        lineHeight = 1.6.em,
        color = FayucaFinderColor.Palette.black
    ),
    bodySmall = TextStyle(
        fontSize = Dimens.FontSize.medium,
        fontWeight = FontWeight.Bold,
        lineHeight = 1.6.em,
        color = FayucaFinderColor.Palette.black
    )
)
