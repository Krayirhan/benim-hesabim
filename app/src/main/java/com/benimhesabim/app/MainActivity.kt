package com.benimhesabim.app.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import com.benimhesabim.app.core.designsystem.theme.BenimHesabimTheme
import com.benimhesabim.app.core.navigation.AppNavHost

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BenimHesabimTheme {
                AppNavHost()
            }
        }
    }
}
