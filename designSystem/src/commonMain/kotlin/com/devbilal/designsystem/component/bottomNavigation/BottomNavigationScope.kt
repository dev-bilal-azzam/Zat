package com.devbilal.designsystem.component.bottomNavigation

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

interface BottomNavigationScope {
    fun bottomNavigationItem(
        notSelectedIcon: DrawableResource,
        selectedIcon: DrawableResource,
        title: StringResource,
        isSelected: Boolean,
        onClick: () -> Unit,
    ) {
        error("The method is not implemented")
    }

}

data class BottomNavigationItem(
    val notSelectedIcon: DrawableResource,
    val selectedIcon: DrawableResource,
    val title: StringResource,
    val isSelected: Boolean,
    val onClick: () -> Unit
)

internal class BottomNavigationScopeImpl : BottomNavigationScope {
    val items = mutableListOf<BottomNavigationItem>()

    override fun bottomNavigationItem(
        notSelectedIcon: DrawableResource,
        selectedIcon: DrawableResource,
        title: StringResource,
        isSelected: Boolean,
        onClick: () -> Unit,
    ) {
        items.add(BottomNavigationItem(notSelectedIcon, selectedIcon, title, isSelected, onClick))
    }
}