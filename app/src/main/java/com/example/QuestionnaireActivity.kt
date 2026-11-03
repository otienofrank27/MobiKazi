package com.example

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.model.JobApplication
import com.example.model.QuestionnaireData
import com.example.ui.QuestionnaireScreen
import com.example.ui.theme.MyApplicationTheme

/**
 * Practical Project One - Requirement (iv):
 * "Create an Intent that enable the user to view questionnaire screen upon successful displays
 * information entered by the user on another screen."
 */
class QuestionnaireActivity : ComponentActivity() {

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_PHONE = "extra_phone"
        const val EXTRA_ADDRESS = "extra_address"
        const val EXTRA_EDUCATION = "extra_education"

        const val EXTRA_GENDER = "extra_gender"
        const val EXTRA_AGE = "extra_age"
        const val EXTRA_ENGLISH = "extra_english"
        const val EXTRA_KISWAHILI = "extra_kiswahili"
        const val EXTRA_OTHER = "extra_other"

        fun createIntent(
            context: Context,
            applicant: JobApplication
        ): Intent {
            return Intent(context, QuestionnaireActivity::class.java).apply {
                putExtra(EXTRA_NAME, applicant.name)
                putExtra(EXTRA_PHONE, applicant.phone)
                putExtra(EXTRA_ADDRESS, applicant.address)
                putExtra(EXTRA_EDUCATION, applicant.education)
            }
        }
    }

    private var latestResult: QuestionnaireData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val applicantName = intent.getStringExtra(EXTRA_NAME) ?: "Marvin Frank"
        val applicantPhone = intent.getStringExtra(EXTRA_PHONE) ?: "0712345678"
        val applicantAddress = intent.getStringExtra(EXTRA_ADDRESS) ?: "P.O. Box 143, Kirinyaga"
        val applicantEducation = intent.getStringExtra(EXTRA_EDUCATION) ?: "BSc. Information Technology"

        val applicant = JobApplication(
            name = applicantName,
            phone = applicantPhone,
            address = applicantAddress,
            education = applicantEducation
        )

        setContent {
            MyApplicationTheme {
                QuestionnaireScreen(
                    applicant = applicant,
                    onNavigateBack = {
                        dispatchResultAndFinish()
                    },
                    onSubmitSuccess = { qData ->
                        latestResult = qData
                        dispatchResult(qData)
                    }
                )
            }
        }
    }

    private fun dispatchResult(data: QuestionnaireData) {
        val returnIntent = Intent().apply {
            putExtra(EXTRA_GENDER, data.gender)
            putExtra(EXTRA_AGE, data.age)
            putExtra(EXTRA_ENGLISH, data.speaksEnglish)
            putExtra(EXTRA_KISWAHILI, data.speaksKiswahili)
            putExtra(EXTRA_OTHER, data.otherLanguage)
        }
        setResult(Activity.RESULT_OK, returnIntent)
    }

    private fun dispatchResultAndFinish() {
        latestResult?.let { dispatchResult(it) } ?: setResult(Activity.RESULT_OK)
        finish()
    }
}
