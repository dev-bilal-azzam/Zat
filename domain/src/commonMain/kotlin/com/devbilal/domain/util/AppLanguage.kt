package com.devbilal.domain.util

enum class AppLanguage(val iso:String){
    ENGLISH("en"),
    ARABIC("ar"),
    DEFAULT("en");

    companion object {
        fun fromIso(iso: String): AppLanguage {
            return when (iso.lowercase()) {
                ENGLISH.iso -> ENGLISH
                ARABIC.iso -> ARABIC
                else -> ENGLISH
            }
        }
    }

}