package com.polimi.dima.uniquizapp.ui.composables

import com.polimi.dima.uniquizapp.R.raw
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.polimi.dima.uniquizapp.R
import com.polimi.dima.uniquizapp.ui.theme.customLightGray
import com.polimi.dima.uniquizapp.ui.theme.customizedBlue
import com.polimi.dima.uniquizapp.ui.viewModels.SharedViewModel
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState



@Composable
fun SubjectBar(subjectId: String?, sharedViewModel: SharedViewModel, navController: NavController){

    sharedViewModel.subjectViewModel.getSubjectsByUser(sharedViewModel.user!!.id)
    val userSubjectState by sharedViewModel.subjectViewModel.userSubjectsState.collectAsState()
    val subject = sharedViewModel.subjectViewModel.getSubjectById(subjectId!!)
    val urls = sharedViewModel.subjectViewModel.getDocumentUrls(subjectId!!)
    var present : Boolean = false
    sharedViewModel.quizViewModel.getAll(subject!!.id)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ){
        Row(modifier = Modifier
            .fillMaxWidth()
            .background(customizedBlue)
            .align(Alignment.CenterHorizontally)
            .padding(end = 20.dp, start = 20.dp)
        ) {
            Text(
                text = subject!!.name,
                fontWeight = FontWeight.Bold,
                color = customLightGray,
                modifier = Modifier
                    .weight(0.8f)
                    .padding(top = 8.dp, bottom = 8.dp),
                textAlign = TextAlign.Start,
                fontSize = 30.sp
            )
            for( s in userSubjectState)
                if(s.equals(subject))
                    present = true
            if(!present){
               Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = customLightGray),
                    shape = CircleShape,
                    modifier = Modifier
                        .weight(0.2f)
                        .align(Alignment.CenterVertically),
                    onClick = {val user = sharedViewModel.userViewModel.addSubjectToUser(subject!!,sharedViewModel.user!!.id)
                        sharedViewModel.addUser(user)},
                    content = {
                        Text(
                            text = "Add",
                            fontWeight = FontWeight.Bold ,
                            color = customizedBlue
                        )
                    }
                )
            }else {
                Spacer(modifier = Modifier
                    .weight(0.2f)
                    .padding(end = 10.dp))
            }

        }
        var selectedItem by remember { mutableStateOf(0) }
        val items = listOf("Arguments", "Documents")
        TabRow(
            selectedTabIndex = selectedItem,
            contentColor = customizedBlue,
            backgroundColor = customLightGray
        ) {
            items.forEachIndexed { index, item ->
                Tab(
                    selected = selectedItem == index,
                    onClick = { selectedItem = index },
                    text = { Text(
                                text = item,
                                fontSize = 16.sp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                color = Color.Black)
                    }

                )
            }

        }
        if(selectedItem == 0){
            Box() {
                ArgumentsGrid(
                    subject = subject,
                    sharedViewModel = sharedViewModel,
                    route = "quiz/",
                    navController = navController
                )
                FloatingActionButtons(navController)
            }

        }
        else {
            var counter = 0
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 250.dp),
                contentPadding = PaddingValues(
                    start = 12.dp,
                    top = 16.dp,
                    end = 12.dp,
                    bottom = 70.dp),
                modifier = Modifier.background(Color.White),
                content = {
                    if(urls != null)
                        items(urls){ item ->
                            Row(){
                                Image(painter = painterResource(id = R.drawable.pdf_icon),
                                    contentDescription = "pdf",
                                    contentScale = ContentScale.FillWidth,
                                    modifier = Modifier.fillMaxSize(0.2f)
                                )
                                val separated = item.split("/").toTypedArray()
                                var text = separated[separated.size-1]
                                text = text.split(".pdf").toTypedArray()[0]
                                text = text.replace("%20", " ")
                                Log.d("text", text)
                                Text(
                                    text = text,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold ,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .clickable {
                                            sharedViewModel.addUrl(item)
                                            navController.navigate("pdf")
                                        }
                                )
                            }
                            if(counter>0 && counter <= urls.size){
                                Spacer(modifier = Modifier.padding(10.dp))
                                Divider(color = customLightGray, thickness = 3.dp, modifier = Modifier.fillMaxWidth())
                                Spacer(modifier = Modifier.padding(10.dp))
                            }
                            counter +=1
                        }
            })

    }}}


@Composable
fun Pdf(navController: NavController, sharedViewModel: SharedViewModel){
    val url = sharedViewModel.documentUrl!!
    val pdfState = rememberVerticalPdfReaderState(
        resource = ResourceType.Remote(url),
        isZoomEnable = true
    )
    VerticalPDFReader(
        state = pdfState,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
    )
}
