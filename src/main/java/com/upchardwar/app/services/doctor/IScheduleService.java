package com.upchardwar.app.services.doctor;

import java.util.List;

import org.springframework.data.domain.Page;

import com.upchardwar.app.entity.doctor.Schedule;
import com.upchardwar.app.entity.payload.ScheduleRequest;
import com.upchardwar.app.entity.payload.ScheduleResponse;

public interface IScheduleService {
	
    public List<ScheduleResponse> getAllSchedules();

    public ScheduleResponse createSchedule(ScheduleRequest scheduleRequest) ;
    
    public ScheduleResponse createSchdule(ScheduleRequest request);
	  
	public ScheduleResponse getSchduleById(Long id);
	
	public String deleteScheduleById(Long id);
	
	public Page<ScheduleResponse> getAllSchdule(Integer pageNo, Integer pageSize);
	
	public List<ScheduleResponse> searchShdule(Integer pageNo,Integer pageSize,ScheduleRequest request,String sortBy);
	
	public ScheduleResponse updateSchedule(ScheduleRequest request);
	
	public Boolean updatestatus(Long id,Boolean status);
	
	
    public  List<Schedule> getSchedulesByDoctorId(Long doctorId);
    
    public  List<Schedule> getupcomingSchedulesByDoctorId(Long doctorId);
    
	

}
