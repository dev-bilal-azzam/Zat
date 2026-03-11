package com.devbilal.designsystem.component.bottomNavigation

import org.jetbrains.compose.resources.DrawableResource

interface BottomNavigationScope {
    fun bottomNavigationItem(
        notSelectedIcon: DrawableResource,
        selectedIcon: DrawableResource,
        title: String,
        isSelected: Boolean,
        onClick: () -> Unit,
    ) {
        error("The method is not implemented")
    }

}

data class BottomNavigationItem(
    val notSelectedIcon: DrawableResource,
    val selectedIcon: DrawableResource,
    val title: String,
    val isSelected: Boolean,
    val onClick: () -> Unit
)

internal class BottomNavigationScopeImpl : BottomNavigationScope {
    val items = mutableListOf<BottomNavigationItem>()

    override fun bottomNavigationItem(
        notSelectedIcon: DrawableResource,
        selectedIcon: DrawableResource,
        title: String,
        isSelected: Boolean,
        onClick: () -> Unit,
    ) {
        items.add(BottomNavigationItem(notSelectedIcon, selectedIcon, title, isSelected, onClick))
    }
}