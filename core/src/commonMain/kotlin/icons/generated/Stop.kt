package core.icons.generated

import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _Stop: ImageVector? = null

val Stop: ImageVector
    get() {
        if (_Stop != null) {
            return _Stop!!
        }
        _Stop = ImageVector.Builder(
            name = "Stop",
            defaultWidth = 512.dp,
            defaultHeight = 512.dp,
            viewportWidth = 512f,
            viewportHeight = 512f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(106.667f, 0f)
                horizontalLineToRelative(298.667f)
                curveTo(464.244f, 0f, 512f, 47.756f, 512f, 106.667f)
                verticalLineToRelative(298.667f)
                curveTo(512f, 464.244f, 464.244f, 512f, 405.333f, 512f)
                horizontalLineTo(106.667f)
                curveTo(47.756f, 512f, 0f, 464.244f, 0f, 405.333f)
                verticalLineTo(106.667f)
                curveTo(0f, 47.756f, 47.756f, 0f, 106.667f, 0f)
                close()
            }
        }.build()
        return _Stop!!
    }

