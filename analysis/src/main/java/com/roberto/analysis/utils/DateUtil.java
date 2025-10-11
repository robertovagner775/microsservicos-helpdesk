package com.roberto.analysis.utils;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

@Component
public class DateUtil {
	
	  public long calculateWorkingMin(LocalDateTime start, LocalDateTime end) {
	        long totalMin = 0;
	        LocalTime workStart = LocalTime.of(9, 0);
	        LocalTime workEnd = LocalTime.of(18, 0);
	    
	        LocalDateTime current = start;
	        while (!current.isAfter(end)) {
	      
	            if (current.getDayOfWeek() != DayOfWeek.SATURDAY && current.getDayOfWeek() != DayOfWeek.SUNDAY) {
	                LocalDateTime workDayStart = LocalDateTime.of(current.toLocalDate(), workStart);
	                LocalDateTime workDayEnd = LocalDateTime.of(current.toLocalDate(), workEnd);

	                LocalDateTime effectiveStart = current.isBefore(workDayStart) ? workDayStart : current;
	                LocalDateTime effectiveEnd = end.isBefore(workDayEnd) ? end : workDayEnd;

	                if (!effectiveStart.isAfter(effectiveEnd)) {
	                    totalMin += Duration.between(effectiveStart, effectiveEnd).toMinutes();
	                }
	            }
	            current = current.plusDays(1).with(LocalTime.MIN);
	        }

	        return totalMin;
	    }

}
