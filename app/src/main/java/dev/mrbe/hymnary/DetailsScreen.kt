package dev.mrbe.hymnary

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HymnDetails(hymn: Hymn?) {

    Column {
        Row(modifier = Modifier.padding(12.dp)) {
            Column {
                hymn?.title?.let {
                    Text(
                        text = it, style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    )
                }

                hymn?.hymnNumber?.let {
                    Text(
                        text = it, style = TextStyle(
                            fontWeight = FontWeight.Light,
                            fontSize = 12.sp
                        )
                    )
                }

                //break lines just before numbers in content
                val modifiedStr = hymn?.content?.replace(Regex("""\d"""), "\n $0")

                modifiedStr?.let {
                    Text(
                        text = it,
                        style = TextStyle(fontWeight = FontWeight.Normal, fontSize = 16.sp)
                    )
                }

            }
        }
    }
}