package com.polimi.dima.uniquizapp.ui.composables

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.api.client.extensions.android.json.AndroidJsonFactory
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import com.google.api.services.calendar.model.CalendarList
import com.google.api.services.calendar.model.CalendarListEntry
import com.polimi.dima.uniquizapp.BottomNavigationBar
import com.polimi.dima.uniquizapp.GoogleSignInActivity
import com.polimi.dima.uniquizapp.MainActivity
import com.polimi.dima.uniquizapp.R
import com.polimi.dima.uniquizapp.data.model.Exam
import com.polimi.dima.uniquizapp.ui.theme.*
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
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

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("Range", "CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Calendar(navController: NavController, sharedViewModel: SharedViewModel) {

    var user = sharedViewModel.user
    //println("user $user")
    var googleAccount = sharedViewModel.googleAccount
    //println("google account $googleAccount")
    var calendarService = sharedViewModel.calendarService
    //println("service $calendarService")
    var calExamsId = sharedViewModel.calendarId
    //println("calid $calExamsId")
    val notNow = remember { mutableStateOf(false) }
    //println("not now ${notNow.value}")

    sharedViewModel.subjectViewModel.getSubjectsByUser(sharedViewModel.user!!.id)
    val userSubjectState by sharedViewModel.subjectViewModel.userSubjectsState.collectAsState()

    val isLoading by sharedViewModel.isLoading.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

    val context = LocalContext.current
    val activity = LocalContext.current as MainActivity
    val signInGoogle = GoogleSignInActivity()
    signInGoogle.initialize(activity)
    val signInIntent = signInGoogle.googleSignInClient.signInIntent

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                signInGoogle.handleResults(task)
                sharedViewModel.addGoogleAccount(signInGoogle.googleAccount!!)
                //Log.d("TASK_TOKEN", task.result.idToken.toString())
                //Log.d("TASK_EMAIL", task.result.email.toString())
            }
        }
    if (googleAccount != null && (calendarService == null)){//} || calExamsId == null)) {
        val googleAccountCredential = GoogleAccountCredential.usingOAuth2(
            context,
            listOf(
                CalendarScopes.CALENDAR,
                CalendarScopes.CALENDAR_READONLY,
                CalendarScopes.CALENDAR_EVENTS_READONLY,
                CalendarScopes.CALENDAR_EVENTS,
                CalendarScopes.CALENDAR_SETTINGS_READONLY)
        )
        googleAccountCredential.selectedAccount = googleAccount!!.account
        val service: Calendar = Calendar.Builder(
            GoogleNetHttpTransport.newTrustedTransport(),
            AndroidJsonFactory.getDefaultInstance(),
            googleAccountCredential
        ).setApplicationName("UniQuiz").build()
        sharedViewModel.addCalendarService(service)
        //println("CREATED service $service")
        //println("saved service ${sharedViewModel.calendarService}")

        val coroutineScope = CoroutineScope(Dispatchers.IO)
        coroutineScope.launch {
            var pageToken: String? = null
            do {
                val calendarList: CalendarList =
                    service.calendarList().list().setShowHidden(true)
                        .setShowDeleted(true).setPageToken(pageToken).execute()
                val items: List<CalendarListEntry> = calendarList.items
                var size = items.size
                for (calendar in items) {
                    size--
                    if (calendar.summary == "Exams") {
                        sharedViewModel.addCalendarId(calendar.id)
                        calExamsId = sharedViewModel.calendarId
                        break
                    }
                    if (size == 0) {
                        notNow.value = true
                    }
                }
                pageToken = calendarList.nextPageToken
            } while (pageToken != null)
        }
    }


    Scaffold(
        topBar = { AppBar(navController = navController, true, false) },
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { padding ->
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(customizedBlue)
                    .align(Alignment.CenterHorizontally)
                    .padding(end = 20.dp, start = 20.dp)
            ) {
                Text(
                    text = "Your Exams",
                    fontWeight = FontWeight.Bold,
                    color = customLightGray,
                    modifier = Modifier
                        .weight(0.8f)
                        .padding(top = 8.dp, bottom = 8.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp
                )
            }
            Divider(color = customizedBlue, thickness = 2.dp)
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxSize()) {
                SwipeRefresh(
                    state = swipeRefreshState,
                    onRefresh = {
                        if(googleAccount != null && calendarService != null && calExamsId != null){
                            sharedViewModel.loadStuff()
                        }
                    },
                    indicator = { state, refreshTrigger ->
                        SwipeRefreshIndicator(
                            state = state,
                            refreshTriggerDistance = refreshTrigger,
                            backgroundColor = customizedBlue,
                            contentColor = customLightGray
                        )
                    }
                ) {
                    if (user!!.exams.isEmpty() && (googleAccount != null)) {
                        Column(
                            modifier = Modifier.height(200.dp)//.fillMaxSize()
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Swipe to sync your exams",
                                color = Color.Gray,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp)
                            )
                        }
                    } else {
                        LazyVerticalGrid(columns = GridCells.Fixed(1),
                            contentPadding = PaddingValues(
                                start = 0.dp,
                                top = 0.dp,
                                end = 0.dp,
                                bottom = 0.dp
                            ),
                            modifier = Modifier.background(Color.White).fillMaxSize(),
                            content = {
                                items(onlyFutureExams(user!!.exams)) { item ->
                                    Log.d("NOW", LocalDate.now().toString())
                                    val dateNow = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())
                                    Log.d("DATE", dateNow.toString() )
                                    Log.d("CONDITION", item.date.before(dateNow).toString())
                                    if(!item.date.before(dateNow)){
                                        val name = runBlocking {
                                                sharedViewModel.subjectViewModel.getSubjectById(
                                                    item!!.subjectId
                                                )!!.name }
                                        val date = getStringDate(item.date)
                                        Card(
                                            onClick = { /* TO DO */ },
                                            backgroundColor = Color.White,
                                            border = BorderStroke(1.dp, customLightGray),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(100.dp)
                                        ) {
                                            Row(
                                                verticalAlignment = CenterVertically,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(horizontal = 8.dp)
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .padding(end = 8.dp)
                                                        .align(CenterVertically),
                                                ) {
                                                    Text(
                                                        text = date,
                                                        lineHeight = 25.sp,
                                                        fontSize = 30.sp,
                                                        color = customizedBlue,
                                                        fontWeight = FontWeight.Normal,
                                                        fontFamily = FontFamily.Monospace,
                                                        textAlign = TextAlign.Center,
                                                        modifier = Modifier
                                                            .fillMaxSize()
                                                            .padding(2.dp)
                                                            .wrapContentHeight()
                                                    )
                                                }
                                                Text(
                                                    text = name,
                                                    lineHeight = 25.sp,
                                                    fontSize = 20.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier
                                                        .weight(2f)
                                                        .padding(2.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            })
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                 //   .border(2.dp, Color.Red, RectangleShape)
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .align(CenterVertically)
                       // .border(2.dp, Color.Green, RectangleShape)
                ) {
                    if (googleAccount == null) {
                        googleLoginButton(
                            text = "Log in Google Calendar",
                            loadingText = "Logging in...",
                            launcher = launcher,
                            signInIntent  = signInIntent,
                            calendarService = null,
                            sharedViewModel = sharedViewModel
                        )
                    } else if (calExamsId == null && notNow.value) {
                        println("creare")
                        googleLoginButton(
                            text = "Create the Exam Calendar",
                            loadingText = "Creating...",
                            launcher = null,
                            signInIntent = null,
                            calendarService = calendarService!!,
                            sharedViewModel = sharedViewModel
                        )
                    }
                }
                if (googleAccount != null) { //&& calendarService != null?
                    FloatingActionButton(
                        onClick = { signInGoogle.updateUI(googleAccount!!) },
                        backgroundColor = Color.White,
                        modifier = Modifier
                            .padding(top = 20.dp, end = 20.dp, bottom = 20.dp)
                            .align(Alignment.CenterVertically)
                            .size(60.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.google_calendar_new_logo_icon_159141),
                            contentDescription = "Google Calendar"
                        )
                    }
                }
            }
        }
    }
}

fun getStringDate(date : Date) : String {
    val dateFormatter = SimpleDateFormat("dd MMM", Locale.getDefault())
    return dateFormatter.format(date)
}

private fun createCalendarExams(calendarService : Calendar, sharedViewModel: SharedViewModel){
    var calendarId : String? = null
    val coroutineScope = CoroutineScope(Dispatchers.IO)
    coroutineScope.launch {
        var calendar = com.google.api.services.calendar.model.Calendar()
        calendar.summary = "Exams"
        calendar.timeZone = "Europe/Rome"
        val createdCalendar: com.google.api.services.calendar.model.Calendar? =
            calendarService.calendars().insert(calendar).execute()
        sharedViewModel.addCalendarId(createdCalendar!!.id)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun googleLoginButton(
    text: String,
    loadingText: String,
    launcher: ManagedActivityResultLauncher<Intent, ActivityResult>?,
    signInIntent: Intent?,
    calendarService: Calendar?,
    sharedViewModel: SharedViewModel
){
    var fraction : Float
    if(launcher != null){ fraction = 0.8F }
    else{ fraction = 0.95F }
    var clicked by remember { mutableStateOf(false) }

    Surface(
        onClick = {
            clicked = !clicked
            if(launcher == null && signInIntent == null && calendarService != null){
                createCalendarExams(calendarService = calendarService, sharedViewModel)!!
                clicked = !clicked
            }
            else{
                launcher!!.launch(signInIntent)
            }},
        shape = RoundedCornerShape(30.dp),
        border = BorderStroke(1.dp, color = Color.LightGray),
        color = MaterialTheme.colors.surface,
        modifier = Modifier
            .fillMaxWidth(fraction)
            .padding(start = 12.dp, end = 8.dp, top = 8.dp, bottom = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 8.dp)
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                )
        ){
            Icon(
                painter = painterResource(id = R.drawable.google_logo),
                contentDescription = "Google Button",
                tint = Color.Unspecified,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if(clicked) loadingText else text,
            )
            if(clicked){
                Spacer(modifier = Modifier.width(16.dp))
                CircularProgressIndicator(
                    modifier = Modifier
                        .height(16.dp)
                        .width(16.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}

fun deleteCalendar(calendarService : Calendar) {

    val coroutineScope = CoroutineScope(Dispatchers.IO)
    coroutineScope.launch {
        var pageToken: String? = null
        do {
            val calendarList: CalendarList =
                calendarService.calendarList().list().setShowHidden(true)
                    .setShowDeleted(true).setPageToken(pageToken).execute()
            val items: List<CalendarListEntry> = calendarList.items
            for (calendar in items) {
                if (calendar.summary == "Exams") {
                    calendarService.calendars().delete(calendar.id).execute()
                }
            }
            pageToken = calendarList.nextPageToken
        } while (pageToken != null)
    }

}

@RequiresApi(Build.VERSION_CODES.O)
private fun onlyFutureExams(allExams : List<Exam>) : List<Exam>{
    val dateNow = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())
    var onlyFutureExams = mutableListOf<Exam>()
    for(exam in allExams){
        if(!exam.date.before(dateNow)){
            onlyFutureExams.add(exam)
        }
    }
    return onlyFutureExams
}