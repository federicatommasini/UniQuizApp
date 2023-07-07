package com.polimi.dima.uniquizapp.ui.composables

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.api.client.extensions.android.json.AndroidJsonFactory
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import com.google.api.services.calendar.model.CalendarList
import com.google.api.services.calendar.model.CalendarListEntry
import com.google.api.services.calendar.model.Event
import com.google.api.services.calendar.model.Events
import com.polimi.dima.uniquizapp.BottomNavigationBar
import com.polimi.dima.uniquizapp.GoogleSignInActivity
import com.polimi.dima.uniquizapp.MainActivity
import com.polimi.dima.uniquizapp.ui.theme.*
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


@Composable
fun Home(navController: NavController, sharedViewModel: SharedViewModel){

    val user = sharedViewModel.user

    Scaffold(
        topBar = {AppBar(navController = navController,true,false,sharedViewModel, false)},
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) {padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
                .padding(padding)
        ) {
            Text(text = "Welcome ${user?.username} to the UniQuiz app!",
                fontWeight = FontWeight.Bold,
                color = customizedBlue,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(30.dp),
                textAlign = TextAlign.Center,
                fontSize = 30.sp)
            Text(text = "With this app you can schedule your study for your university exams, learning using simple and fun quizzes!",
                    fontWeight = FontWeight.Bold,
                color = customizedBlue,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(30.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp)
            /*Image(painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                   modifier = Modifier.size(300.dp).align(Alignment.CenterHorizontally).padding(10.dp,0.dp)
            )*/
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ){

               /* Box(

                ) {
                    Image(painter = painterResource(id = R.drawable.books),
                        contentDescription = "books",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.clickable { //navController.navigate(route = BottomNavItem.Subjects.screen_route)
                        }
                            .fillMaxWidth()
                            .padding(30.dp, 10.dp)
                            .border(
                                BorderStroke(2.dp, customizedBlue),
                                RoundedCornerShape(500.dp)
                            )
                            .clip(RoundedCornerShape(500.dp))
                    )
                    Text(text = "Your Subjects",
                        fontWeight = FontWeight.Bold,
                        color = customizedBlue,
                        modifier = Modifier.align(Alignment.CenterStart).padding(50.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp)
                }
                Image(painter = painterResource(id = R.drawable.groups),
                    contentDescription = "groups",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.clickable { //navController.navigate(route = BottomNavItem.Subjects.screen_route)
                         }
                        .fillMaxWidth()
                        .padding(30.dp,10.dp)
                        .border(
                            BorderStroke(2.dp, customizedBlue),
                            RoundedCornerShape(500.dp)
                        )
                        .clip(RoundedCornerShape(500.dp))
                )*/
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun Subjects(navController: NavController, sharedViewModel: SharedViewModel) {

    sharedViewModel.subjectViewModel.getState()
    val allSubjectState by sharedViewModel.subjectViewModel.allSubjectsState.collectAsState()
    val userSubjectState by sharedViewModel.subjectViewModel.userSubjectsState.collectAsState()

    Scaffold(
        topBar = {AppBar(navController = navController,true,false,sharedViewModel, false)},
        bottomBar = { BottomNavigationBar(navController = navController) },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                CustomSearchBar(allSubjectState,navController)
                Spacer(modifier = Modifier.padding(15.dp))
                Text(
                    text = "Your Subjects",
                    fontWeight = FontWeight.Bold,
                    color = customizedBlue,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
                /*if(userSubjectState[0] != null) {*/
                    LazyGrid(
                        route = "subject_screen/",
                        navController,
                        sharedViewModel
                    )
                //}
            }
        }
    )
}

@Composable
fun Groups(navController: NavController, sharedViewModel: SharedViewModel){
    Scaffold(
        topBar = {AppBar(navController = navController,true,false,sharedViewModel, false)},
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
                .padding(padding)
        ) {

            Text(
                text = "groups screen",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }
    }
}



@SuppressLint("Range")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Calendar(navController: NavController, sharedViewModel: SharedViewModel){

    val context = LocalContext.current
    val activity = LocalContext.current as MainActivity
    val signInGoogle = GoogleSignInActivity()
    signInGoogle.initialize(activity)
    val signInIntent = signInGoogle.googleSignInClient.signInIntent
    var googleAccount : GoogleSignInAccount? = null
    val showButton = remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            signInGoogle.handleResults(task)
            googleAccount = signInGoogle.googleAccount

            Log.d("TASK_TOKEN", task.result.idToken.toString())
            Log.d("TASK_EMAIL", task.result.email.toString())

            if (googleAccount != null) {
                showButton.value = true
                val googleAccountCredential = GoogleAccountCredential.usingOAuth2(
                    context,
                    listOf(CalendarScopes.CALENDAR,
                        CalendarScopes.CALENDAR_READONLY,
                        CalendarScopes.CALENDAR_EVENTS_READONLY,
                        CalendarScopes.CALENDAR_EVENTS,
                        CalendarScopes.CALENDAR_SETTINGS_READONLY))
                googleAccountCredential.selectedAccount = googleAccount!!.account

                val service: Calendar = Calendar.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    AndroidJsonFactory.getDefaultInstance(),
                    googleAccountCredential)
                    .setApplicationName("UniQuiz").build()

                val coroutineScope = CoroutineScope(Dispatchers.IO)
                coroutineScope.launch {
                    Log.d("BOH DENTRO", "ok")

                    var pageToken: String? = null

                    do {
                        Log.d("dentro do", "uh uh")
                        val calendarList: CalendarList = service.calendarList().list().setShowHidden(true).setShowDeleted(true).setPageToken(pageToken).execute()

                        Log.d("list", calendarList.toString())
                        val items: List<CalendarListEntry> = calendarList.getItems()

                        Log.d("items", items.toString())

                        for (calendarListEntry in items) {
                            Log.d("CALENDDAR", calendarListEntry.summary)

                            println(calendarListEntry.summary)
                        }
                        pageToken = calendarList.getNextPageToken()
                        if(pageToken != null) { Log.d("tok", pageToken.toString()) } else {
                            println("ELSE")
                        }
                    } while (pageToken != null)
                    var pageToken2: String? = null
                    do {
                        val events: Events =
                            service.events().list("primary").setPageToken(pageToken2).execute()
                        val items: List<Event> = events.items
                        Log.d("EVENT ITEMS", items.toString())
                        for (event in items) {
                            Log.d("EVENTO", event.summary.toString())
                        }
                        pageToken2 = events.nextPageToken
                    } while (pageToken2 != null)

                }
            }
        }
    }

    Scaffold(
        topBar = {AppBar(navController = navController,true,false,sharedViewModel, false)},
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) {padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
                .padding(padding)
        ) {
            Text(
                text = "Log in Google Calendar",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        launcher.launch(signInIntent)
                    },
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
            if(showButton.value){
                Button(
                    onClick = { signInGoogle.updateUI(googleAccount!!)},
                    colors = ButtonDefaults.buttonColors(backgroundColor = customizedBlue),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(58.dp)
                        .padding(start = 60.dp, end = 60.dp))
                {
                    Text(text = "Access your Google Calendar", fontSize = 28.sp, color = Color.White)
                }
            }
        }
    }
}
