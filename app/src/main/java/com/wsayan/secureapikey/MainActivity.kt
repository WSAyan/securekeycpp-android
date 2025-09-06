package com.wsayan.secureapikey

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.wsayan.secureapikey.ui.component.AnimeList
import com.wsayan.secureapikey.ui.component.ErrorContent
import com.wsayan.secureapikey.ui.theme.SecureApiKeyTheme
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val key = ApiKeyExtractor.getKey()
        Log.d("API_KEY", "---------> $key")

        splashScreen.setKeepOnScreenCondition { viewModel.uiState.value.shouldKeepSplashScreen() }

        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            SecureApiKeyTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) { innerPadding ->
                    when (uiState) {
                        is MainActivityUiState.Error -> {
                            ErrorContent(
                                modifier = Modifier
                                    .padding(innerPadding),
                                message = (uiState as MainActivityUiState.Error).message
                            )
                        }

                        is MainActivityUiState.Loading -> {}
                        is MainActivityUiState.Success -> {
                            AnimeList(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding),
                                animeList = (uiState as MainActivityUiState.Success).animeList
                            )
                        }
                    }
                }
            }
        }
    }
}

