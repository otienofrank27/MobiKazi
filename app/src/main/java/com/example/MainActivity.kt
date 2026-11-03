package com.example

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.model.JobApplication
import com.example.model.QuestionnaireData
import com.example.ui.JobApplicationScreen
import com.example.ui.LoginScreen
import com.example.ui.QuestionnaireScreen
import com.example.ui.SubmissionDisplayScreen
import com.example.ui.theme.MyApplicationTheme

enum class AppScreen {
    LOGIN,
    APPLICATION_FORM,
    DASHBOARD,
    QUESTIONNAIRE
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MobiKaziApp()
                }
            }
        }
    }
}

@Composable
fun MobiKaziApp() {
    val context = LocalContext.current
    var currentScreen by remember { mutableStateOf(AppScreen.LOGIN) }
    var loggedInUser by remember { mutableStateOf("") }
    var currentApplication by remember {
        mutableStateOf(
            JobApplication(
                name = "Marvin Frank",
                phone = "0712345678",
                address = "P.O. Box 143, Kirinyaga",
                education = "BSc. Information Technology"
            )
        )
    }
    var questionnaireResult by remember { mutableStateOf<QuestionnaireData?>(null) }

    // Intent launcher for requirement (iv): Activity Intent to QuestionnaireActivity
    val questionnaireLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            if (data != null && data.hasExtra(QuestionnaireActivity.EXTRA_GENDER)) {
                questionnaireResult = QuestionnaireData(
                    gender = data.getStringExtra(QuestionnaireActivity.EXTRA_GENDER) ?: "Male",
                    age = data.getStringExtra(QuestionnaireActivity.EXTRA_AGE) ?: "21",
                    speaksEnglish = data.getBooleanExtra(QuestionnaireActivity.EXTRA_ENGLISH, true),
                    speaksKiswahili = data.getBooleanExtra(QuestionnaireActivity.EXTRA_KISWAHILI, true),
                    otherLanguage = data.getStringExtra(QuestionnaireActivity.EXTRA_OTHER) ?: ""
                )
            }
            // Returns directly to Dashboard
            currentScreen = AppScreen.DASHBOARD
        }
    }

    when (currentScreen) {
        AppScreen.LOGIN -> {
            LoginScreen(
                onLoginSuccess = { user ->
                    loggedInUser = user
                    // Prefill details for the authenticated user
                    if (user.equals("marvin frank", ignoreCase = true) || user.equals("marvin", ignoreCase = true)) {
                        currentApplication = JobApplication(
                            name = "Marvin Frank",
                            phone = "0712345678",
                            address = "P.O. Box 143, Kirinyaga",
                            education = "BSc. Information Technology"
                        )
                    } else if (user.equals("tom", ignoreCase = true)) {
                        currentApplication = JobApplication(
                            name = "Tom",
                            phone = "7129120923",
                            address = "451278, Nairobi",
                            education = "BscIT"
                        )
                    } else if (user.equals("fred", ignoreCase = true)) {
                        currentApplication = JobApplication(
                            name = "Fred Loudina",
                            phone = "0716900000",
                            address = "2910-0200 Nairobi",
                            education = "Msc AI"
                        )
                    } else {
                        currentApplication = currentApplication.copy(name = user)
                    }
                    currentScreen = AppScreen.APPLICATION_FORM
                }
            )
        }

        AppScreen.APPLICATION_FORM -> {
            BackHandler {
                currentScreen = AppScreen.LOGIN
            }

            JobApplicationScreen(
                initialApplicant = currentApplication,
                onSubmit = { appData ->
                    currentApplication = appData
                    // Upon submitting details, navigates to the Dashboard
                    currentScreen = AppScreen.DASHBOARD
                },
                onQuit = {
                    currentScreen = AppScreen.LOGIN
                }
            )
        }

        AppScreen.DASHBOARD -> {
            BackHandler {
                currentScreen = AppScreen.APPLICATION_FORM
            }

            SubmissionDisplayScreen(
                applicant = currentApplication,
                questionnaireResult = questionnaireResult,
                onOpenQuestionnaireIntent = {
                    // Launches the QuestionnaireActivity via explicit Android Intent
                    val intent = QuestionnaireActivity.createIntent(context, currentApplication)
                    questionnaireLauncher.launch(intent)
                },
                onEdit = {
                    currentScreen = AppScreen.APPLICATION_FORM
                },
                onLogout = {
                    loggedInUser = ""
                    questionnaireResult = null
                    currentScreen = AppScreen.LOGIN
                }
            )
        }

        AppScreen.QUESTIONNAIRE -> {
            BackHandler {
                currentScreen = AppScreen.DASHBOARD
            }

            QuestionnaireScreen(
                applicant = currentApplication,
                onNavigateBack = {
                    currentScreen = AppScreen.DASHBOARD
                },
                onSubmitSuccess = { qData ->
                    questionnaireResult = qData
                    currentScreen = AppScreen.DASHBOARD
                }
            )
        }
    }
}
