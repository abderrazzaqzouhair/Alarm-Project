package com.alarm.alarm

data class Alarm(
    var label: String,
    var time: String,
    var status: Boolean,
    var days: List<String>,
    )