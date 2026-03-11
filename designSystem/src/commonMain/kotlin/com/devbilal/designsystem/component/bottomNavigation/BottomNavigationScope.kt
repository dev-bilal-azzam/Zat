package com.devbilal.designsystem.component.bottomNavigation

import androidx.compose.runtime.mutableStateListOf
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

    fun clear()
}

data class BottomNavigationItem(
    val notSelectedIcon: DrawableResource,
    val selectedIcon: DrawableResource,
    val title: StringResource,
    val isSelected: Boolean,
    val onClick: () -> Unit
)

internal class BottomNavigationScopeImpl : BottomNavigationScope {
    private val _items = mutableStateListOf<BottomNavigationItem>()
    val items: List<BottomNavigationItem> = _items

    override fun bottomNavigationItem(
        notSelectedIcon: DrawableResource,
        selectedIcon: DrawableResource,
        title: StringResource,
        isSelected: Boolean,
        onClick: () -> Unit,
    ) {
        _items.add(BottomNavigationItem(notSelectedIcon, selectedIcon, title, isSelected, onClick))
    }

    override fun clear() {
        _items.clear()
    }
}