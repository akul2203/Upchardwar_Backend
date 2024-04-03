package com.upchardwar.app.entity.payload;

import com.upchardwar.app.entity.doctor.Schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlotResponse {

	private Long id;
	private String startTime;
	private String endTime;
	private Boolean isBooked;
	private Boolean isDeleted;

	private Schedule schedule;
}
