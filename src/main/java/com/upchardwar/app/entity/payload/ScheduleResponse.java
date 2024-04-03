package com.upchardwar.app.entity.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleResponse {
	private Long id;

	private String selectedDate;
	private List<TimeSlotRequest> timeSlots;
	private Boolean isActive;

	private Boolean isDeleted;

	private DoctorResponse doctor;
}
