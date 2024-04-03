package com.upchardwar.app.entity.payload;

import com.upchardwar.app.entity.doctor.Appointment;
import com.upchardwar.app.entity.doctor.Schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlotRequest {

	private Long id;

	private Schedule schedule;
	private String startTime;
	private String endTime;
	private Boolean isBooked;
	private Boolean isDeleted;
	}
