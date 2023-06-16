package com.polimi.dima.uniquizapp

sealed class BottomNavItem(var title: String, var icon: Int, var screen_route: String){

    object Home : BottomNavItem("Home", R.drawable.ic_baseline_home_white,"home")
    object Subjects : BottomNavItem("Subjects", R.drawable.ic_baseline_subject_white, "subjects")
    object Groups : BottomNavItem("Groups", R.drawable.ic_baseline_groups_white,"groups")
    object Calendar : BottomNavItem("Calendar", R.drawable.ic_baseline_calendar_month_white, "calendar")
}