package com.polimi.dima.uniquizapp

import android.content.Intent
import android.net.Uri
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import com.google.api.services.calendar.CalendarScopes
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class GoogleSignInActivity() {

    lateinit var auth : FirebaseAuth
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var mainActivity : MainActivity
    var googleAccount : GoogleSignInAccount? = null
    lateinit var credential : AuthCredential

    fun initialize(activity: MainActivity){
        mainActivity = activity
        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("71784389950-cjv8m26m30rlokak16usoee180ockafo.apps.googleusercontent.com")
            .requestScopes(Scope(CalendarScopes.CALENDAR_READONLY),
                Scope(CalendarScopes.CALENDAR),
                Scope(CalendarScopes.CALENDAR_EVENTS),
                Scope(CalendarScopes.CALENDAR_SETTINGS_READONLY),
                Scope(CalendarScopes.CALENDAR_EVENTS_READONLY))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(activity, gso)
    }

    fun handleResults(task: Task<GoogleSignInAccount>){
       if(task.isSuccessful){
           Log.d("handleResults", "task successful")
           val account : GoogleSignInAccount? = task.result
           if (account != null) {
               Log.d("handleResults", "account not null")
               googleAccount = account
               //updateUI(account)
           }
       }else{
           Log.d("handleResults", "task not successful")

       }
   }

   fun updateUI(account: GoogleSignInAccount) {
       credential = GoogleAuthProvider.getCredential(account.idToken,null)
       auth.signInWithCredential(credential).addOnCompleteListener{
           if (it.isSuccessful){
               val intent = Intent(Intent.ACTION_VIEW)
               intent.data = Uri.parse("content://com.android.calendar/time")
               Log.d("ACCOUNT", account.email.toString())
               Log.d("ACCOUNT", account.displayName.toString())
               intent.putExtra("email", account.email)
               intent.putExtra("name", account.displayName)
               mainActivity.startActivity(intent)
           }else{
               Log.d("UPDATEUI", "auth not successful")
           }

       }
   }
}
