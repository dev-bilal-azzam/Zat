package com.devbilal.designsystem.theme.color

import androidx.compose.ui.graphics.Color
import com.devbilal.designsystem.theme.color.palette.ColorPalette
import com.devbilal.designsystem.theme.color.palette.ColorPalette.ColorScale

val colorPalette = ColorPalette(
    navy = ColorScale(
        shade50 = Color(0xFFE6F0F2),
        shade100 = Color(0xFFBFDCE1),
        shade200 = Color(0xFF99C7CF),
        shade300 = Color(0xFF73B3BC),
        shade400 = Color(0xFF4D9FA9),
        shade500 = Color(0xFF327285),
        shade600 = Color(0xFF2C5C66),
        shade700 = Color(0xFF244851),
        shade800 = Color(0xFF1E293B),
        shade900 = Color(0xFF0A171B)
    ),
    coffee = ColorScale(
        shade50 = Color(0xFFFCF9F5),
        shade100 = Color(0xFFF5F0E9),
        shade200 = Color(0xFFF0E7DA),
        shade300 = Color(0xFFE0D1BC),
        shade400 = Color(0xFFCCB89B),
        shade500 = Color(0xFFB29B79),
        shade600 = Color(0xFF997D56),
        shade700 = Color(0xFF806338),
        shade800 = Color(0xFF66481F),
        shade900 = Color(0xFF4D330F)
    ),
    gray = ColorScale(
        shade50 = Color(0xFFFFFFFF),
        shade100 = Color(0xFFF1F5F9),
        shade200 = Color(0xFFFF2F4F7),
        shade300 = Color(0xFFEAECF0),
        shade400 = Color(0xFFBEC0CC),
        shade500 = Color(0xFF94A3B8),
        shade600 = Color(0xFF334155),
        shade700 = Color(0xFF12141C),
        shade800 = Color(0xFF0E1017),
        shade900 = Color(0xFF000000),
    ),
    red = ColorScale(
        shade50 = Color(0xFFFEE4E2),
        shade100 = Color(0xFFFEE4E2),
        shade200 = Color(0xFFFECDCA),
        shade300 = Color(0xFFFDA29B),
        shade400 = Color(0xFFF97066),
        shade500 = Color(0xFFF04438),
        shade600 = Color(0xFFD92D20),
        shade700 = Color(0xFFB42318),
        shade800 = Color(0xFF912018),
        shade900 = Color(0xFF7A271A)
    ),
    yellow = ColorScale(
        shade50 = Color(0xFFFFFAEB),
        shade100 = Color(0xFFFEF0C7),
        shade200 = Color(0xFFFEDF89),
        shade300 = Color(0xFFFEC84B),
        shade400 = Color(0xFFFDB022),
        shade500 = Color(0xFFF79009),
        shade600 = Color(0xFFDC6803),
        shade700 = Color(0xFFB54708),
        shade800 = Color(0xFF93370D),
        shade900 = Color(0xFF7A2E0E)
    ),
    green = ColorScale(
        shade50 = Color(0xFFE6F6EA),
        shade100 = Color(0xFFC3E8CB),
        shade200 = Color(0xFF9BD9A9),
        shade300 = Color(0xFF71CB87),
        shade400 = Color(0xFF4EBF6D),
        shade500 = Color(0xFF23B353),
        shade600 = Color(0xFF19A44A),
        shade700 = Color(0xFF06923E),
        shade800 = Color(0xFF008133),
        shade900 = Color(0xFF00621F)
    ),
    magenta = ColorScale(
        shade50 = Color(0xFFFCE6F4),
        shade100 = Color(0xFFF8C2E6),
        shade200 = Color(0xFFF39AD7),
        shade300 = Color(0xFFED70C8),
        shade400 = Color(0xFFE94DBD),
        shade500 = Color(0xFFE423B0),
        shade600 = Color(0xFFD81FA8),
        shade700 = Color(0xFFC51A9D),
        shade800 = Color(0xFFB31693),
        shade900 = Color(0xFF940F82)
    ),
    violet = ColorScale(
        shade50 = Color(0xFFF1E9FF),
        shade100 = Color(0xFFD9C7FF),
        shade200 = Color(0xFFBFA2FF),
        shade300 = Color(0xFFA57CFF),
        shade400 = Color(0xFF8F5EFF),
        shade500 = Color(0xFF7A3FF7),
        shade600 = Color(0xFF6C36E6),
        shade700 = Color(0xFF5C2FD1),
        shade800 = Color(0xFF4E27B8),
        shade900 = Color(0xFF3B1F8A)
    )
)
internal val White = Color(0xFFFFFFFF)
internal val White60 = White.copy(alpha = 0.6f)
internal val White38 = White.copy(alpha = 0.38f)