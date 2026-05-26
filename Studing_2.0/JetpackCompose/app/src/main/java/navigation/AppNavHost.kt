package navigation

import UI.Characters.ChartersScreen
import UI.Characters.ViewModel
import UI.Characters.secondScreen
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import navigation.Destination

val FIRST_PAGE = Destination.First.route
val SECOND_PAGE = Destination.Second.route

@Composable
fun AppNavHost(
    navHostController : NavHostController,
    viewModel: ViewModel
){
    NavHost( navController = navHostController,
        startDestination = FIRST_PAGE)
    {
        composable(route = FIRST_PAGE){
            ChartersScreen(viewModel = viewModel,
                onNextClick = {
                    navHostController.navigate(SECOND_PAGE)
                })
        }
        composable(route = SECOND_PAGE){
            secondScreen()
        }
    }
}