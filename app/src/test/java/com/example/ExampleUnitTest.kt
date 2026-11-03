package com.example

import com.example.model.JobApplication
import com.example.model.QuestionnaireData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun testJobApplicationDefaults() {
        val app = JobApplication(
            name = "Marvin Frank",
            phone = "0712345678",
            address = "P.O. Box 143, Kirinyaga",
            education = "BSc. Information Technology"
        )
        assertEquals("Marvin Frank", app.name)
        assertEquals("0712345678", app.phone)
        assertEquals("P.O. Box 143, Kirinyaga", app.address)
        assertEquals("BSc. Information Technology", app.education)
    }

    @Test
    fun testQuestionnaireLanguages() {
        val questionnaire = QuestionnaireData(
            gender = "Male",
            age = "21",
            speaksEnglish = true,
            speaksKiswahili = true,
            otherLanguage = "French"
        )
        val languages = questionnaire.getLanguagesList()
        assertEquals(3, languages.size)
        assertTrue(languages.contains("English"))
        assertTrue(languages.contains("Kiswahili"))
        assertTrue(languages.contains("Other: French"))
    }
}
