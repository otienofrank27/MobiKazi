package com.example.model

import java.io.Serializable

data class JobApplication(
    val name: String = "",
    val phone: String = "",
    val address: String = "",
    val education: String = "",
    val avatarUri: String? = null
) : Serializable

data class QuestionnaireData(
    val gender: String = "Male",
    val age: String = "",
    val speaksEnglish: Boolean = false,
    val speaksKiswahili: Boolean = false,
    val otherLanguage: String = ""
) : Serializable {
    fun getLanguagesList(): List<String> {
        val list = mutableListOf<String>()
        if (speaksEnglish) list.add("English")
        if (speaksKiswahili) list.add("Kiswahili")
        if (otherLanguage.isNotBlank()) list.add("Other: ${otherLanguage.trim()}")
        return list
    }
}
