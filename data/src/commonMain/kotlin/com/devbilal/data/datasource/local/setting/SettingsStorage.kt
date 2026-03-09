package com.devbilal.data.datasource.local.setting

import com.russhwolf.settings.Settings
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

class SettingsStorage(
    private val settings: Settings,
    private val json: Json
) {

    fun putString(key: String, value: String) {
        settings.putString(key, value)
    }

    fun getString(key: String, default: String = ""): String {
        return settings.getString(key, default)
    }

    fun putStringOrNull(key: String, value: String?) {
        settings.putString(key, value.orEmpty())
    }

    fun getStringOrNull(key: String, default: String? = null): String? {
        val string = settings.getString(key, "")
        if (string.isBlank() || string.isEmpty()) return default
        return string
    }

    fun remove(key: String) {
        settings.remove(key)
    }

    /**
     * Store any Set of Enum as a comma-separated string
     */
    fun <T : Enum<T>> putEnumSet(key: String, value: Set<T>) {
        val stringValue = value.joinToString(",") { it.name }
        putString(key, stringValue)
    }

    /**
     * Retrieve any Set of Enum from a comma-separated string
     */
    inline fun <reified T : Enum<T>> getEnumSet(key: String, default: Set<T> = emptySet()): Set<T> {
        val stored = getString(key, "")
        return if (stored.isBlank()) default
        else stored.split(",").map { enumValueOf<T>(it) }.toSet()
    }

    /**
     * Retrieve any JSON object as a class should be annotated with @Serializable
     */
    fun <T> getSerializable(
        key: String,
        default: T,
        serializer: KSerializer<T>
    ): T {

        val stored = settings.getStringOrNull(key)
            ?: return default

        return json.decodeFromString(serializer, stored)
    }

    /**
     * Store any class annotated with @Serializable as a JSON object
     */
    fun <T> putSerializable(
        key: String,
        value: T,
        serializer: KSerializer<T>
    ) {
        settings.putString(
            key,
            json.encodeToString(serializer, value)
        )
    }
}