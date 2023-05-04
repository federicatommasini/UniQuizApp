package com.polimi.dima.uniquizapp

sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String){

    object Home : BottomNavItem("Home", R.drawable.ic_baseline_home_24,"home")
    object Subjects : BottomNavItem("Subjects", R.drawable.ic_baseline_subject_24, "subjects")
    object Groups : BottomNavItem("Groups", R.drawable.ic_baseline_groups_24,"groups")
    object Calendar : BottomNavItem("Calendar", R.drawable.ic_baseline_calendar_month_24, "calendar")
}