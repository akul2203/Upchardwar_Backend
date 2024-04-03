package com.upchardwar.app.services.impl.doctor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upchardwar.app.entity.doctor.TimeSlote;
import com.upchardwar.app.repository.ScheduleRepository;
import com.upchardwar.app.repository.TimeSlotRepository;
import com.upchardwar.app.services.doctor.ITimeSlotService;


@Service
public class TimeSloteServiceImpl implements ITimeSlotService {

	@Override
	public Map<String, Object> createTimeSlots(Long scheduleId, int intervalInMinutes) {
		// TODO Auto-generated method stub
		return null;
	}
  
	@Autowired
	private TimeSlotRepository timeSlotRepository;
	
	@Autowired
	private ScheduleRepository scheduleRepository;

	@Override
	public Map<String, Object> updateTimeSlot(Long timeSlotId, Boolean isBooked) {
        Map<String, Object> response = new HashMap<>();

        // Retrieve the time slot by ID
        Optional<TimeSlote> optionalTimeSlot = timeSlotRepository.findById(timeSlotId);

        if (optionalTimeSlot.isEmpty()) {
            response.put("success", false);
            response.put("message", "Time slot not found");
            return response;
        }

        TimeSlote timeSlot = optionalTimeSlot.get();

        timeSlot.setIsBooked(isBooked);
        // Save the updated time slot to the database
        timeSlotRepository.save(timeSlot);

        response.put("success", true);
        response.put("message", "Time slot updated successfully");
        return response;
    }

	@Override
	public Optional<TimeSlote> getbyid(Long id) {
		// TODO Auto-generated method stub
		return this.timeSlotRepository.findById(id);
	}

	


}
