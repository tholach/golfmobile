package ca.cegepthetford.golfmobile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GolfCourse
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
@Composable
fun FicheTrouGolf(controleurNav : NavHostController,trou : TrouGolf, parcours : ParcoursGolfViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.GolfCourse,
            contentDescription = "Golf",
            modifier = Modifier.size(150.dp)
        )
        Text(text="Trou ${trou.noTrou}", fontSize = 48.sp, fontWeight = FontWeight.Bold)
        Text(text="Par ${trou.par}", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        repeat(8) {indice ->
            val pluriel = if (indice == 0) "" else "s"
            val score = indice -trou.par + 1
            val signe = if (score > 0) "+" else ""
            Button(
                onClick = {
                    parcours.inscrireScore(trou.noTrou, indice + 1)
                    controleurNav.popBackStack()
                },
                modifier = Modifier.padding(vertical = 8.dp)
            ){
                Text(text = "${indice+1} coup${pluriel} (${signe}${score})", fontSize = 24.sp)
            }
        }
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun Fiche() {
// GolfMobileTheme {
// FicheTrouGolf(trou = TrouGolf(2,2))
// }
//}