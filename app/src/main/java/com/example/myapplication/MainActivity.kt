package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold(
                topBar = {
                    TopAppBar {
                        Text("METANIT.COM", fontSize = 22.sp)
                    }
                }
            ) {
                Column(modifier = Modifier.padding(it)) {
                    Text("Hello Scaffold", fontSize = 28.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipTimeScreen()
}

@Composable
fun LemonadeApp() {
    LemonadeScreen(
        Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )
}

@Composable
fun LemonadeScreen(modifier: Modifier = Modifier) {
    var result by remember {
        mutableStateOf(1)
    }
    var randomValue by remember {
        mutableStateOf(1)
    }
    val lemonadeStep = when (result) {
        1 -> StepOne {
            result = 2
            randomValue = (1..6).random()
        }
        2 -> StepTwo {
            randomValue--
            if (randomValue == 0) {
                result = 3
            }
        }
        3 -> StepThree {
            result = 4
        }
        else -> StepFour {
            result = 1
        }
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LemonadeImageWithTextAndBorders(lemonadeStep)
    }
}

@Composable
fun LemonadeImageWithTextAndBorders(lemonadeStep: LemonadeStep) {
    Text(lemonadeStep.text)
    Spacer(modifier = Modifier.height(16.dp))
    Image(
        painter = painterResource(lemonadeStep.imageResource),
        contentDescription = lemonadeStep.text,
        modifier = Modifier
            .wrapContentSize()
            .clickable(onClick = lemonadeStep.onImageClick)
            .border(
                BorderStroke(2.dp, Color(105, 205, 216)),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(16.dp)
    )
}

@Composable
fun RollDiceApp() {
    DiceWithButtonAndImage(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )
}

@Composable
fun DiceWithButtonAndImage(modifier: Modifier = Modifier) {
    var result by remember { mutableStateOf(1) }
    val imageResource = when (result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(imageResource),
            contentDescription = result.toString()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { result = (1..6).random() }) {
            Text("Roll")
        }
    }
}

abstract class LemonadeStep {
    abstract val text: String
    abstract val imageResource: Int
    abstract val onImageClick: () -> Unit
}

class StepOne(override val onImageClick: () -> Unit) : LemonadeStep() {
    override val text: String
        get() = "Tap the lemon so select a lemon"
    override val imageResource: Int
        get() = R.drawable.lemon_tree
}

class StepTwo(override val onImageClick: () -> Unit) : LemonadeStep() {
    override val text: String
        get() = "Keep tapping to lemon to squeeze it"
    override val imageResource: Int
        get() = R.drawable.lemon_squeeze
}

class StepThree(override val onImageClick: () -> Unit) : LemonadeStep() {
    override val text: String
        get() = "Tap the lemonade to drink it"
    override val imageResource: Int
        get() = R.drawable.lemon_drink
}

class StepFour(override val onImageClick: () -> Unit) : LemonadeStep() {
    override val text: String
        get() = "Tap the empty glass to start again"
    override val imageResource: Int
        get() = R.drawable.lemon_restart
}
