package com.polimi.dima.uniquizapp.ui.composables

import android.graphics.Bitmap
import com.polimi.dima.uniquizapp.R.raw
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
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
import java.lang.reflect.Field


@Composable
fun SubjectBar(subjectId: String?, sharedViewModel: SharedViewModel, navController: NavController){

    sharedViewModel.subjectViewModel.getSubjectsByUser(sharedViewModel.user!!.id)
    val userSubjectState by sharedViewModel.subjectViewModel.userSubjectsState.collectAsState()
    val subject = sharedViewModel.subjectViewModel.getSubjectById(subjectId!!)
    var present : Boolean = false
    sharedViewModel.quizViewModel.getAll(subject!!.id)
    val subjectQuizzes by sharedViewModel.quizViewModel.allQuizzesState.collectAsState()

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
                    present = true;
            /*OutlinedButton(onClick = { /*TODO*/ },
                modifier= Modifier.size(50.dp),  //avoid the oval shape
                shape = CircleShape,
                border= BorderStroke(1.dp, Color.Blue),
                contentPadding = PaddingValues(0.dp),  //avoid the little icon
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = customLightGray)
            ) {}*/
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
        if(selectedItem == 0)
            ArgumentsGrid(
                subject= subject,
                sharedViewModel= sharedViewModel,
                route = "",
                navController = navController
            )
        else {
            val fields: Array<Field> =  raw::class.java.fields
            val state = fields.toList()
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
                    items(state){ item ->
                        Row(){
                            Image(painter = painterResource(id = R.drawable.pdf_icon),
                                contentDescription = "pdf",
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier.fillMaxSize(0.2f)
                            )
                            Text(
                                text = item.name,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold ,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.CenterVertically).clickable {
                                    navController.navigate("pdf/" + item.getInt(null))}
                            )
                        }
                        Log.d("fields",fields.get(0).name)
                        if(counter>0 && counter <= state.size){
                            Spacer(modifier = Modifier.padding(10.dp))
                            Divider(color = customLightGray, thickness = 3.dp, modifier = Modifier.fillMaxWidth())
                            Spacer(modifier = Modifier.padding(10.dp))
                        }
                        counter +=1
                    }
            })

    }}}


@Composable
fun Pdf(navController: NavController, id: Int?){
    val pdfState = rememberVerticalPdfReaderState(
        resource = ResourceType.Asset(id!!),
        //.Local(Uri.parse("assets/lesson_2_datawarehouse")),
        // .Remote("https://myreport.altervista.org/Lorem_Ipsum.pdf"),
        isZoomEnable = true
    )
    VerticalPDFReader(
        state = pdfState,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
    )
}
