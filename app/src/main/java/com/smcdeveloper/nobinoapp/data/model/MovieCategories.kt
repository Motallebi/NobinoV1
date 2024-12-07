package com.smcdeveloper.nobinoapp.data.model

enum class Category(val displayName: String) {
    SERIES("SERIES"),
    SEASON("SEASON"),
    EPISODE("EPISODE"),
    MOVIE("MOVIE"),
    OTHER("OTHER"),
    COURSE("COURSE"),
    CERTIFICATED_COURSE("CERTIFICATED COURSE");


    companion object {
        // Utility function to find a category by its display name
        fun fromDisplayName(name: String): Category? {
            return values().find { it.displayName.equals(name, ignoreCase = true) }
        }
    }
}