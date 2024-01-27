package com.example.lista3

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.lista3.ui.theme.Lista3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lista3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Replace this with your content
                    Content()
                }
            }
        }
    }
}

@Composable
fun Content() {
    var index by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var quizCompleted by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0.1f) }
    var selectedOption by remember { mutableStateOf("") }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        if (!quizCompleted) {
            Text(
                text = "Pytanie ${index + 1}/10",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally) // Wysrodkowujemy tekst
            )
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 70.dp),
                progress = progress,
                color = Color(android.graphics.Color.parseColor("#46b32b")),
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f) // Wagi równomierne rozkładają dostępne miejsce
                    .padding(vertical = 8.dp, horizontal = 10.dp)
            ) {
                Text(
                    text = QuestionsAnswers.Questions[index],
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 8.dp, horizontal = 10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    for (option in QuestionsAnswers.Options[index]) {
                        AnswerOption(option = option, onOptionSelected = {
                            selectedOption = it
                        }, selectedOption = selectedOption)
                    }
                }
            }
            Button(
                onClick = {
                    if (selectedOption == QuestionsAnswers.Answers[index]) {
                        selectedOption = ""
                        score += 10
                    }
                    if (index < QuestionsAnswers.Questions.size - 2) {
                        index++
                        progress += 0.1f
                    } else {
                        // Ostatnie pytanie - zakończ quiz i przejdź do MainActivity2
                        quizCompleted = true
                    }
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            ) {
                Text("Next")
            }
        }
        if (quizCompleted) {
            ResultScreen(score)
        }
    }
}

@Composable
fun AnswerOption(option: String, onOptionSelected: (String) -> Unit, selectedOption: String) {
    val isSelected = option == selectedOption

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(if (isSelected) Color.Gray else Color.Transparent)
            .selectable(
                selected = isSelected,
                onClick = {
                    onOptionSelected(option)
                }
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = null,
            modifier = Modifier
                .padding(end = 16.dp)
        )
        Text(text = option)
    }
}



@Composable
fun ResultScreen(score: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Gratulacje!",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(10.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            shape = RoundedCornerShape(11.dp)
        ) {
            Text(
                text = "Zdobyłeś $score/100 pkt",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize(Alignment.Center)
            )
        }
    }
}