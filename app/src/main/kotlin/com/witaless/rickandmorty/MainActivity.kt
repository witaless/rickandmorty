package com.witaless.rickandmorty

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.material.composethemeadapter.MdcTheme
import com.witaless.rickandmorty.presentation.ui.Page
import com.witaless.rickandmorty.presentation.ui.characterdetails.CharacterDetailsPage
import com.witaless.rickandmorty.presentation.ui.characters.CharactersPage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MdcTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Page.Characters.route) {
                    composable(route = Page.Characters.route) {
                        CharactersPage(viewModel = hiltViewModel(), navController = navController)
                    }
                    composable(
                        route = Page.CharacterDetails.route(),
                        arguments = listOf(navArgument(Page.CharacterDetails.idNavArgument) {
                            type = NavType.IntType
                        })
                    ) {
                        CharacterDetailsPage(
                            viewModel = hiltViewModel(),
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}
