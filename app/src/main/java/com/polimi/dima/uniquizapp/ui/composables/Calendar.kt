package com.polimi.dima.uniquizapp.ui.composables

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
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
import com.polimi.dima.uniquizapp.data.model.UserExam
import com.polimi.dima.uniquizapp.ui.theme.*
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import java.util.concurrent.TimeUnit
import androidx.compose.runtime.Composable
import androidx.compose.material.Card

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("Range", "CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Calendar(navController: NavController, sharedViewModel: SharedViewModel) {

    var user = sharedViewModel.user
    var googleAccount = sharedViewModel.googleAccount
    var calendarService = sharedViewModel.calendarService
    var calExamsId = sharedViewModel.calendarId
    val notNow = remember { mutableStateOf(false) }

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
        topBar = { AppBar(navController = navController, true, false, sharedViewModel, false,null) },
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
                            sharedViewModel.loadEvents()
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
                            modifier = Modifier
                                .height(200.dp)
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
                            modifier = Modifier
                                .background(Color.White)
                                .fillMaxSize()
                        ) {
                            items(onlyFutureExams(user!!.exams)) { item ->
                                val name = runBlocking {
                                    sharedViewModel.subjectViewModel.getSubjectById(
                                        item!!.exam.subjectId
                                    )!!.name
                                }
                                val date = getStringDate(item.exam.date)
                                Card(
                                    onClick = {  },
                                    enabled = false,
                                    backgroundColor = Color.White,
                                    border = BorderStroke(1.dp, customLightGray),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(120.dp)
                                        .wrapContentHeight()
                                ) {
                                    Row(
                                        verticalAlignment = CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentHeight()
                                    ) {
                                        Box(contentAlignment = Center,
                                            modifier = Modifier
                                                .weight(0.3f)
                                                .padding(start = 10.dp, end = 8.dp)
                                                .align(CenterVertically),
                                        ) {
                                            Text(
                                                text = date,
                                                fontSize = 28.sp,
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
                                        Column(
                                            modifier = Modifier
                                                .weight(0.7f)
                                                .wrapContentHeight()
                                        ) {

                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .wrapContentHeight()
                                                    .padding(start = 10.dp)
                                            ) {
                                                Text(
                                                    text = name,
                                                    fontSize = if (item.notes == null) 26.sp else 24.sp,
                                                    fontWeight = FontWeight.Normal,
                                                    textAlign = TextAlign.Left,
                                                    modifier = Modifier
                                                        .padding(
                                                            start = 0.dp,
                                                            top = 5.dp,
                                                            end = 8.dp,
                                                            bottom = 0.dp
                                                        )
                                                        .align(Alignment.CenterVertically)
                                                )
                                            }
                                            if (item.notes != null) {
                                                Row(
                                                    modifier = Modifier
                                                        .padding(
                                                            start = 10.dp,
                                                            top = 2.dp,
                                                            end = 8.dp,
                                                            bottom = 5.dp
                                                        )
                                                        .wrapContentHeight()
                                                ) {
                                                    Text(
                                                        text = "Notes:",
                                                        lineHeight = 15.sp,
                                                        fontSize = 13.sp,
                                                        fontFamily = FontFamily.Monospace,
                                                        fontWeight = FontWeight.Normal,
                                                        textAlign = TextAlign.Left,
                                                        color = Color.Gray,
                                                        modifier = Modifier
                                                    )
                                                    Text(
                                                        text = item.notes,
                                                        lineHeight = 14.sp,
                                                        fontSize = 12.sp,
                                                        fontFamily = FontFamily.Monospace,
                                                        fontWeight = FontWeight.Normal,
                                                        textAlign = TextAlign.Left,
                                                        color = Color.Gray,
                                                        modifier = Modifier
                                                            .padding(start = 5.dp)
                                                            .wrapContentHeight()
                                                    )
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (googleAccount != null) {
                    FloatingActionButton(
                        onClick = { signInGoogle.updateUI(googleAccount!!) },
                        backgroundColor = Color.White,
                        modifier = Modifier
                            .padding(top = 20.dp, end = 20.dp, bottom = 20.dp)
                            .align(Alignment.BottomEnd)
                            .size(40.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.google_calendar_new_logo_icon_159141),
                            contentDescription = "Google Calendar"
                        )
                    }
                }
            }

            if(calExamsId == null){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .align(CenterVertically)
                    ) {
                    if (googleAccount == null) {
                        googleLoginButton(
                            text = "Log in Google Calendar",
                            loadingText = "Logging in...",
                            launcher = launcher,
                            signInIntent = signInIntent,
                            calendarService = null,
                            sharedViewModel = sharedViewModel
                        )
                    } else if (calExamsId == null && notNow.value) {
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
    var showAlert by remember { mutableStateOf(false) }

    Surface(
        onClick = {
            clicked = !clicked
            if(launcher == null && signInIntent == null && calendarService != null){
                createCalendarExams(calendarService = calendarService, sharedViewModel)!!
                showAlert = true
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

    if(showAlert){
        AutoDismissAlert(
            message = "Exams Calendar created!",
            durationInSeconds = 3,
            onDismiss = { showAlert = false }
        )
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
private fun onlyFutureExams(allExams : List<UserExam>) : List<UserExam>{
    val dateNow = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())
    var onlyFutureExams = mutableListOf<UserExam>()
    for(userExam in allExams){
        if(!userExam.exam.date.before(dateNow)){
            onlyFutureExams.add(userExam)
        }
    }
    onlyFutureExams.sortBy { item -> item.exam.date}
    return onlyFutureExams
}

@Composable
fun AutoDismissAlert(
    message: String,
    durationInSeconds : Int,
    onDismiss: () -> Unit
){

    LaunchedEffect(Unit) {
        delay(TimeUnit.SECONDS.toMillis(durationInSeconds.toLong()))
        onDismiss()
    }

    Snackbar (
        modifier = Modifier.padding(16.dp)
    ){
        Text(text = message)
    }
}