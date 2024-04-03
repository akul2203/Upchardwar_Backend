package com.upchardwar.app.services.impl.doctor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.upchardwar.app.entity.doctor.Doctor;
import com.upchardwar.app.entity.doctor.Schedule;
import com.upchardwar.app.entity.doctor.TimeSlote;
import com.upchardwar.app.entity.payload.ScheduleRequest;
import com.upchardwar.app.entity.payload.ScheduleResponse;
import com.upchardwar.app.exception.ResourceNotFoundException;
import com.upchardwar.app.repository.AppointmentRepository;
import com.upchardwar.app.repository.ScheduleRepository;
import com.upchardwar.app.repository.TimeSlotRepository;
import com.upchardwar.app.services.doctor.IScheduleService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ScheduleServiceImpl implements IScheduleService {
	@Autowired
	private ScheduleRepository repository;
    @Autowired
	private TimeSlotRepository treopo;
	/*
	 * @Autowired private DoctorRepository doctorRepository;
	 */
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private AppointmentRepository appointmentRepository;

	public ScheduleResponse scheduleToScheduleResponse(Schedule schedule) {
		return this.modelMapper.map(schedule, ScheduleResponse.class);
	}

	public Schedule scheduleRequestToSchedule(ScheduleRequest scheduleRequest) {
		return this.modelMapper.map(scheduleRequest, Schedule.class);
	}

	@Override
	public ScheduleResponse getSchduleById(Long id) {
		Optional<Schedule> s = this.repository.findById(id);
		if (s.isPresent())
			return this.scheduleToScheduleResponse(s.get());

		throw new ResourceNotFoundException("Doctor with this id not exist");
	}

	@Override
	@Transactional
	public String deleteScheduleById(Long id) {
		Optional<Schedule> s = this.repository.findById(id);
		
			if (s.isEmpty()) {
				throw new ResourceNotFoundException("schedule with this id not exist");
			}
			else {
				System.err.println("Inside Else");
				List<TimeSlote> listOfTimesSlots = this.treopo.getAllTimeSlotsBySechdule(id);
				for(TimeSlote timeSlote: listOfTimesSlots)
				{
					if(java.util.Objects.nonNull(timeSlote.getAppointment()))
					appointmentRepository.deleteById(timeSlote.getAppointment().getId());
				}
				treopo.deleteByScheduleId(id);
				this.repository.deleteById(id);
//			List<TimeSlote> listOfTimesSlotsq = this.treopo.getAllTimeSlotsBySechdule(id);
			}
		
//		 TimeSlote timeSlote = treopo.findById(timeSloteId)
//		            .orElseThrow(() -> new EntityNotFoundException("TimeSlote not found with id: " + timeSloteId));

//		    // Manually delete associated Appointment entities
//		    for (Appointment appointment : timeSlote.getAppointments()) {
//		        appointmentRepository.delete(appointment);
//		    }
//
//		    // Delete the TimeSlote
//		    treopo.delete(timeSlote);
////		Optional<Schedule> s = this.repository.findById(id);
////
////		if (s.isEmpty()) {
////			throw new ResourceNotFoundException("schedule with this id not exist");
////		}
////		else {
////		this.repository.delete(s.get());
////		return "deleted successfully";
////		}
			return "deleted successfully";
		}

	@Override
	public Page<ScheduleResponse> getAllSchdule(Integer pageNo, Integer pageSize) {
		PageRequest page = PageRequest.of(pageNo, pageSize);
		Page<Schedule> pag = this.repository.findAll(page);
		return pag.map(u -> this.scheduleToScheduleResponse(u));

	}

	@Override
	public List<ScheduleResponse> searchShdule(Integer pageNo, Integer pageSize, ScheduleRequest request,
			String sortBy) {
		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnoreNullValues()
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) // Match anywhere in the string
				.withIgnoreCase() // Ignore case when matching strings
				.withMatcher("id", match -> match.transform(value -> value.map(id -> ((Integer) id == 0) ? null : id)));

		Example<Schedule> example = Example.of(scheduleRequestToSchedule(request), exampleMatcher);
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, sortBy);
		Page<Schedule> findAllSchedule = this.repository.findAll(example, pageable);
		return findAllSchedule.getContent().stream().map(s -> scheduleToScheduleResponse(s))
				.collect(Collectors.toList());
	}

	@Override
	public ScheduleResponse updateSchedule(ScheduleRequest request) {

		Schedule s = this.repository.save(scheduleRequestToSchedule(request));
		return this.scheduleToScheduleResponse(s);
	}

	@Override
	public ScheduleResponse createSchdule(ScheduleRequest request) {
		Schedule s = this.repository.save(this.scheduleRequestToSchedule(request));
		return this.scheduleToScheduleResponse(s);
	}



	@Override
	public List<ScheduleResponse> getAllSchedules() {
		List<Schedule> schedules = repository.findAll();
		return schedules.stream().map(this::convertToResponseDto).collect(Collectors.toList());

	}

	@Override
	public ScheduleResponse createSchedule(ScheduleRequest scheduleRequest) {
		System.err.println("service");
		Schedule schedule = convertToEntity(scheduleRequest);
		Doctor doctor = scheduleRequest.getDoctor();
        schedule.setTimeSlots(null);
		schedule.setDoctor(doctor);
		
		Schedule savedSchedule = repository.save(schedule);		   
		List<TimeSlote> timeSlots = scheduleRequest.getTimeSlots();
	for (TimeSlote ts : timeSlots)
	{
		ts.setSchedule(savedSchedule);
			this.treopo.save(ts);
		}
		return convertToResponseDto(savedSchedule);
	}
	

	private Schedule convertToEntity(ScheduleRequest scheduleRequest) {

		return modelMapper.map(scheduleRequest, Schedule.class);
	}

	private <R> ScheduleResponse convertToResponseDto(Schedule schedule1) {
		return modelMapper.map(schedule1, ScheduleResponse.class);
	}

    public List<Schedule> getSchedulesByDoctorId(Long doctorId) {
    
    	  List<Schedule> schedules = repository.findByDoctorId(doctorId);
          return schedules.isEmpty() ? null : schedules;
    }

	@Override
	public List<Schedule> getupcomingSchedulesByDoctorId(Long doctorId) {
		  List<Schedule> schedules = repository.findUpcomingSchedulesByDoctor(doctorId);
          return schedules.isEmpty() ? null : schedules;
	}

	@Override
	public Boolean updatestatus(Long id,Boolean status) {
	    int affectedRows = repository.updateStatusById(id, status);

        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }
		
	  
	}

}
//
//@Override
//public String deleteScheduleById(Long id) {
//	Optional<Schedule> s = this.repository.findById(id);
//
//	if (s.isEmpty()) {
//		throw new ResourceNotFoundException("schedule with this id not exist");
//	}
//	else {
//		System.err.println("Inside Else");
//	List<TimeSlote> listOfTimesSlots = this.treopo.getAllTimeSlotsBySechdule(id);
//	
//	for(int i=0;i<listOfTimesSlots.size();i++) 
//	{
//	
//		
//		Appointment p = this.aRepo.findAllAppointmentByTimeSlotId(listOfTimesSlots.get(i).getId());
//		this.aRepo.delete(p);
//	}
//	this.treopo.deleteTimeSlotsBySchduleId(id);
//	this.repository.delete(s.get());
//	return "deleted successfully";
//	}
//
//}
