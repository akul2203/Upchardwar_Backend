package com.upchardwar.app.services.doctor;

import java.util.Map;
import java.util.Optional;

import com.upchardwar.app.entity.doctor.TimeSlote;

public interface ITimeSlotService {
	public Map<String, Object> createTimeSlots(Long scheduleId, int intervalInMinutes);
	
    public Map<String, Object> updateTimeSlot(Long timeSlotId, Boolean isBooked);
    
    
    public Optional<TimeSlote> getbyid(Long id);
}
