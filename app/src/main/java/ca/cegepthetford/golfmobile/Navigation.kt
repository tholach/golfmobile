package ca.cegepthetford.golfmobile

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
@Composable
fun Navigation(parcours: ParcoursGolfViewModel) {
    val controleurNav = rememberNavController()
    NavHost(
        navController = controleurNav,
        startDestination = "EcranPrincipal"
    ) {
        composable(route = "EcranPrincipal") {
            EcranPrincipal(controleurNav, parcours )
        }
        composable(
            route = "FicheTrou/{noTrou}",
            arguments = listOf(
                navArgument(name = "noTrou") {
                    type = NavType.IntType
                }
            )
        ) {entree ->
            FicheTrouGolf(controleurNav, parcours.trous[entree.arguments?.getInt("noTrou")!!-1], parcours)
        }
    }
}