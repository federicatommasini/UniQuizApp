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
import com.google.api.client.util.DateTime
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import com.google.api.services.calendar.model.CalendarList
import com.google.api.services.calendar.model.CalendarListEntry
import com.google.api.services.calendar.model.Event
import com.google.api.services.calendar.model.Events
import com.polimi.dima.uniquizapp.BottomNavigationBar
import com.polimi.dima.uniquizapp.GoogleSignInActivity
import com.polimi.dima.uniquizapp.MainActivity
import com.polimi.dima.uniquizapp.data.model.ExamRequest
import com.polimi.dima.uniquizapp.data.model.User
import com.polimi.dima.uniquizapp.ui.theme.*
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun Home(navController: NavController, sharedViewModel: SharedViewModel){

    val user = sharedViewModel.user

    Scaffold(
        topBar = {AppBar(navController = navController,true,false)},
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
        topBar = {AppBar(navController = navController,true,false)},
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
        topBar = {AppBar(navController = navController,true,false)},
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
fun Calendar(navController: NavController, sharedViewModel: SharedViewModel) {

    val user = sharedViewModel.user
    println("user $user")

    sharedViewModel.subjectViewModel.getSubjectsByUser(sharedViewModel.user!!.id)
    val userSubjectState by sharedViewModel.subjectViewModel.userSubjectsState.collectAsState()
    println("sub ${userSubjectState.toString()}")


    val context = LocalContext.current
    val activity = LocalContext.current as MainActivity
    val signInGoogle = GoogleSignInActivity()
    signInGoogle.initialize(activity)
    val signInIntent = signInGoogle.googleSignInClient.signInIntent
    var googleAccount: GoogleSignInAccount? = null
    val showButton = remember { mutableStateOf(false) }
    val infoText = remember { mutableStateOf(true) }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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
                        listOf(
                            CalendarScopes.CALENDAR,
                            CalendarScopes.CALENDAR_READONLY,
                            CalendarScopes.CALENDAR_EVENTS_READONLY,
                            CalendarScopes.CALENDAR_EVENTS,
                            CalendarScopes.CALENDAR_SETTINGS_READONLY
                        )
                    )
                    googleAccountCredential.selectedAccount = googleAccount!!.account

                    val service: Calendar = Calendar.Builder(
                        GoogleNetHttpTransport.newTrustedTransport(),
                        AndroidJsonFactory.getDefaultInstance(),
                        googleAccountCredential
                    )
                        .setApplicationName("UniQuiz").build()

                    val coroutineScope = CoroutineScope(Dispatchers.IO)
                    coroutineScope.launch {
                        var pageToken: String? = null
                        var calExamsId: String? = null

                        do {
                            val calendarList: CalendarList =
                                service.calendarList().list().setShowHidden(true)
                                    .setShowDeleted(true).setPageToken(pageToken).execute()
                            val items: List<CalendarListEntry> = calendarList.items
                            for (calendar in items) {
                                if (calendar.summary == "Exams") {
                                    calExamsId = calendar.id
                                    println(calExamsId)
                                }
                            }
                            pageToken = calendarList.getNextPageToken()
                        } while (pageToken != null)


                        if (calExamsId != null) {
                            var pageTokenEvents: String? = null
                            do {
                                val eventsList: Events =
                                    service.events().list(calExamsId).setPageToken(pageTokenEvents)
                                        .execute()
                                val eventItems: List<Event> = eventsList.items
                                for (event in eventItems) {
                                    println("event $event")
                                    val subjectNameInCalendar = event.summary
                                    println(user!!.id)

                                    for (subject in userSubjectState) {
                                        Log.d("MATERIA", subject!!.toString())
                                        if (subject!!.name.compareTo(subjectNameInCalendar) == 0) {
                                            Log.d("SIoigjoei", "ho la materia")
                                            val day = event.start.date.toString()
                                            val pattern = "yyyy-MM-dd"
                                            val formatter = SimpleDateFormat(pattern, Locale.ENGLISH)
                                            println("DATA " + day)
                                            val date: Date = formatter.parse(day)
                                            println("DATA " + date)
                                            //if in the calendar there is an exam of a subject i added on uniquiz, i create the exam on backend
                                            val examRequest = ExamRequest(
                                                subject.id,
                                                day
                                            )
                                            Log.d("EXAM", examRequest.toString())
                                            Log.d("USERID", user!!.id)

                                            val userToUpdate =
                                            runBlocking {
                                                sharedViewModel.examViewModel.addExam(
                                                    user.id, examRequest)
                                                }
                                            Log.d("REGISTERED", userToUpdate.toString())
                                            userToUpdate?.let { sharedViewModel.addUser(it) }

                                            }
                                    }
                                }
                                pageTokenEvents = eventsList.nextPageToken
                            } while (pageTokenEvents != null)
                        }


                    }
                }
            }
        }

    Scaffold(
        topBar = { AppBar(navController = navController, true, false) },
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
                .padding(padding)
        ) {
            if (infoText.value) {
                Text(
                    text = "UniQuiz is synced with your Google Calendar named \"Exams\", create the Calendar and schedule your exams!",
                    color = customizedBlue,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .align(Alignment.CenterHorizontally)
                        .border(1.dp, color = customizedBlue, shape = RoundedCornerShape(20.dp))
                        .background(grayBackground),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }
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
            if (showButton.value) {
                Button(
                    onClick = { signInGoogle.updateUI(googleAccount!!) },
                    colors = ButtonDefaults.buttonColors(backgroundColor = customizedBlue),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(58.dp)
                        .padding(start = 60.dp, end = 60.dp)
                )
                {
                    Text(
                        text = "Access your Google Calendar",
                        fontSize = 28.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}
