package com.example.uptodo.screens.calender

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.uptodo.components.patterns.getNext7Dates
import com.example.uptodo.components.patterns.getPrevious7Dates
import com.example.uptodo.ui.theme.BottomBarColor
import com.example.uptodo.ui.theme.Purple40
import com.himanshoe.kalendar.color.KalendarThemeColor
import com.himanshoe.kalendar.component.day.KalendarDay
import com.himanshoe.kalendar.component.day.config.KalendarDayColors
import com.himanshoe.kalendar.component.header.KalendarHeader
import com.himanshoe.kalendar.component.header.config.KalendarHeaderConfig
import com.himanshoe.kalendar.component.text.KalendarNormalText
import com.himanshoe.kalendar.component.text.config.KalendarTextColor
import com.himanshoe.kalendar.component.text.config.KalendarTextConfig
import com.himanshoe.kalendar.model.KalendarDay
import com.himanshoe.kalendar.model.KalendarEvent
import com.himanshoe.kalendar.model.toKalendarDay
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import java.time.format.TextStyle
import java.util.*

@Composable
fun CalenderHorizontal(
    modifier: Modifier = Modifier,
    showWeekDays: Boolean = true,
    calendarEvents: List<KalendarEvent> = emptyList(),
    onCurrentDayClick: (KalendarDay, List<KalendarEvent>) -> Unit = { _, _ -> },
    takeMeToDate: LocalDate?,
    calendarDayColors: KalendarDayColors,
    calendarThemeColor: KalendarThemeColor,
    calendarHeaderConfig: KalendarHeaderConfig? = null
) {
    val currentDay = takeMeToDate ?: Clock.System.todayIn(TimeZone.currentSystemDefault())
    val weekValue = remember { mutableStateOf(currentDay.getNext7Dates()) }
    val month = weekValue.value.first().month
    val year = weekValue.value.first().year
    val selectedKalendarDate = remember { mutableStateOf(currentDay) }

    Column(
        modifier = modifier
            .background(BottomBarColor)
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(all = 5.dp)
    ) {
        KalendarHeader(
            modifier = Modifier.fillMaxWidth(),
            month = month,
            year = year,
            onPreviousClick = {
                val firstDayOfDisplayedWeek = weekValue.value.first()
                weekValue.value = firstDayOfDisplayedWeek.getPrevious7Dates()
            },
            onNextClick = {
                val lastDayOfDisplayedWeek = weekValue.value.last().plus(1, DateTimeUnit.DAY)
                weekValue.value = lastDayOfDisplayedWeek.getNext7Dates()
            },
            kalendarHeaderConfig = calendarHeaderConfig?: KalendarHeaderConfig(
                kalendarTextConfig = KalendarTextConfig(
                    kalendarTextColor = KalendarTextColor(Color.White)
                )
            )
        )
        Row(modifier = Modifier.wrapContentWidth()) {
            weekValue.value.forEach { localDate ->
                val isCurrentDay = localDate == currentDay
                Column {
                    if (showWeekDays) {
                        KalendarNormalText(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = localDate.dayOfWeek.getDisplayName(
                                TextStyle.FULL, Locale.getDefault()
                            ).take(1),
                            fontWeight = FontWeight.Normal,
                            textColor = Color.White
                        )
                    }
                    KalendarDay(
                        kalendarDay = localDate.toKalendarDay(),
                        modifier = Modifier.clip(shape = RoundedCornerShape(5.dp)),
                        kalendarEvents = calendarEvents,
                        isCurrentDay = isCurrentDay,
                        onCurrentDayClick = { kalendarDay, events ->
                            selectedKalendarDate.value = kalendarDay.localDate
                            onCurrentDayClick(kalendarDay, events)
                        },
                        selectedKalendarDay = selectedKalendarDate.value,
                        kalendarDayColors = calendarDayColors,
                        dotColor = calendarThemeColor.headerTextColor,
                        dayBackgroundColor = calendarThemeColor.dayBackgroundColor,
                    )
                }
            }
        }
    }
}