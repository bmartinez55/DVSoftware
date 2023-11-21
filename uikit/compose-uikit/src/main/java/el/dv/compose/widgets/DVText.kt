package el.dv.compose.widgets

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit

@Composable
fun H1Text(
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight = FontWeight.Bold,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    lineHeight: TextUnit = MaterialTheme.typography.h1.lineHeight,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.typography.h1.color,
    maxLines: Int = Int.MAX_VALUE,
    overFlow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    onTextLayoutResult: ((TextLayoutResult) -> Unit)? = null
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overFlow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayoutResult ?: {},
        style = MaterialTheme.typography.h1
    )
}

@Composable
fun H2Text(
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight = FontWeight.Bold,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    lineHeight: TextUnit = MaterialTheme.typography.h2.lineHeight,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.typography.h2.color,
    maxLines: Int = Int.MAX_VALUE,
    overFlow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    onTextLayoutResult: ((TextLayoutResult) -> Unit)? = null
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overFlow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayoutResult ?: {},
        style = MaterialTheme.typography.h2
    )
}

@Composable
fun H3Text(
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight = FontWeight.Bold,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    lineHeight: TextUnit = MaterialTheme.typography.h3.lineHeight,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.typography.h3.color,
    maxLines: Int = Int.MAX_VALUE,
    overFlow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    onTextLayoutResult: ((TextLayoutResult) -> Unit)? = null
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overFlow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayoutResult ?: {},
        style = MaterialTheme.typography.h3
    )
}

@Composable
fun BodyText(
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight = FontWeight.Normal,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    lineHeight: TextUnit = MaterialTheme.typography.body1.lineHeight,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colors.onSurface,
    maxLines: Int = Int.MAX_VALUE,
    overFlow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    onTextLayoutResult: ((TextLayoutResult) -> Unit)? = null
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overFlow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayoutResult ?: {},
        style = when (fontWeight) {
            FontWeight.Bold -> MaterialTheme.typography.body2
            else -> MaterialTheme.typography.body1
        }
    )
}

@Composable
fun CaptionText(
    modifier: Modifier = Modifier,
    text: String,
    lineHeight: TextUnit = MaterialTheme.typography.caption.lineHeight,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colors.onSurface,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayoutResult: ((TextLayoutResult) -> Unit)? = null
) {
    Text(
        text = text,
        modifier = modifier,
        style = when (fontWeight) {
            FontWeight.Bold -> MaterialTheme.typography.overline
            else -> MaterialTheme.typography.caption
        },
        lineHeight = lineHeight,
        color = color,
        textAlign = textAlign,
        maxLines = maxLines,
        onTextLayout = onTextLayoutResult ?: {}
    )
}

@Preview
@Composable
fun PreviewH1Text() {
    H1Text(
        text = "H1 Text"
    )
}

@Preview
@Composable
fun PreviewH2Text() {
    H2Text(
        text = "H2 Text"
    )
}

@Preview
@Composable
fun PreviewH3Text() {
    H3Text(
        text = "H3 Text"
    )
}

@Preview
@Composable
fun PreviewBody1Text() {
    BodyText(
        text = "Body1 Text"
    )
}

@Preview
@Composable
fun PreviewBody2Text() {
    BodyText(
        text = "Body2 Text",
        fontWeight = FontWeight.Bold
    )
}

@Preview
@Composable
fun PreviewCaptionText() {
    CaptionText(
        text = "Caption Text"
    )
}
