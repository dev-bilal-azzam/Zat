import androidx.compose.runtime.Composable
import com.devbilal.designsystem.util.AppTheme


@Composable
expect fun SetSystemBarsAppearance(appTheme: AppTheme, isSystemInDarkTheme: Boolean)