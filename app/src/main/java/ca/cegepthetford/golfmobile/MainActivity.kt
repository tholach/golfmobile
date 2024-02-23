package ca.cegepthetford.golfmobile

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import ca.cegepthetford.golfmobile.ui.theme.GolfmobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GolfmobileTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val parcours: ParcoursGolfViewModel by viewModels<ParcoursGolfViewModel>()
                    Navigation(parcours = parcours)
                }
            }
        }
    }
}

class ParcoursGolfViewModel : ViewModel() {
    val trous = mutableStateListOf<TrouGolf>()
    val banqueTrous = mutableListOf<TrouGolf>()

    init {
        listOf(5, 3, 4, 4, 3, 4, 4, 5, 3).forEachIndexed { indice, par ->
            banqueTrous.add(TrouGolf(indice + 1, par))
        }
        trous.add(banqueTrous[0])
    }

    fun inscrireScore(noTrou: Int, nouveauScore: Int) {
        trous[noTrou - 1].score = nouveauScore
        if (noTrou < 9)
            trous.add(banqueTrous[noTrou])
    }
}

class TrouGolf(val noTrou: Int, val par: Int) {
    var score: Int by mutableStateOf(0)
    fun manqueScore(): Boolean {
        return score == 0
    }

    fun determinerTermeScore(): String {
        return when (score - par) {
            -4 -> "Condor"
            -3 -> "Albatross"
            -2 -> "Eagle"
            -1 -> "Birdie"
            0 -> "Par"
            1 -> "Bogey"
            2 -> "Double bogey"
            3 -> "Triple bogey"
            4 -> "Quadruple bogey"
            else -> "Quintuple bogey"
        }
    }
}

@Composable
fun EcranPrincipal(controleurNav: NavHostController, parcours: ParcoursGolfViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.logogolf), contentDescription = "logo")
        Text(
            text = "Pointage Golf",
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            itemsIndexed(parcours.trous) { index, trouGolf ->
                ItemTrouGolf(controleurNav = controleurNav, trou = trouGolf)
            }
        }
    }

}

@Composable
fun ItemTrouGolf(controleurNav: NavHostController, trou: TrouGolf) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        ListItem(
            colors = ListItemDefaults.colors(
                containerColor =
                if (trou.manqueScore()) {
                    Color.Green
                } else {
                    Color.Gray
                }
            ),
            headlineContent = { Text(text = "Trou #${trou.noTrou}", fontWeight = FontWeight.Bold) },
            supportingContent = {
                Text(text = "Par: ${trou.par}")
                if (!trou.manqueScore())
                    Text(text = "Score: ${trou.score} (${trou.determinerTermeScore()})")
            },
            modifier = Modifier.clickable { controleurNav.navigate("FicheTrou/${trou.noTrou}") }
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun EcranPreview() {
// GolfMobileTheme {
// EcranPrincipal()
// }
//}
//@Preview(showBackground = true)
//@Composable
//fun ItemPreview() {
// GolfMobileTheme {
// Column(
// modifier = Modifier.padding(16.dp)
// ) {
// ItemTrouGolf(trou = TrouGolf(1, 1))
// var x = TrouGolf(7, 4)
// x.score = 2
// ItemTrouGolf(trou = x)
// }
// }
//}