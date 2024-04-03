package com.upchardwar.app.controller.doctor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upchardwar.app.entity.doctor.Schedule;
import com.upchardwar.app.entity.payload.ScheduleRequest;
import com.upchardwar.app.entity.payload.ScheduleResponse;
import com.upchardwar.app.services.doctor.IScheduleService;

@RestController
@RequestMapping("/upchardwar/schedule")
@CrossOrigin("*")
public class ScheduleController {

	@Autowired
	private IScheduleService service;

	@GetMapping("getall")
	public List<ScheduleResponse> getAllSchedules() {
		return service.getAllSchedules();
	}

	@GetMapping("/doctor/{doctorId}")
	public ResponseEntity<List<Schedule>> getSchedulebydoctorid(@PathVariable Long doctorId) {
		return new ResponseEntity<List<Schedule>>(this.service.getSchedulesByDoctorId(doctorId), HttpStatus.OK);
	}

	@GetMapping("/doctor/upcoming/{doctorId}")
	public ResponseEntity<List<Schedule>> getupcomingSchedulebydoctorid(@PathVariable Long doctorId) {
		return new ResponseEntity<List<Schedule>>(this.service.getupcomingSchedulesByDoctorId(doctorId), HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<ScheduleResponse> createSchedule(@RequestBody ScheduleRequest createScheduleRequest) {
		
//		System.out.println("-------->>>>>>>>>>"+createScheduleRequest.getTimeSlots().size());
		System.err.println("created.....");
		return new ResponseEntity<>(service.createSchedule(createScheduleRequest), HttpStatus.CREATED);

	}


	// to get schedule by id
	@GetMapping("/{id}")
	public ResponseEntity<ScheduleResponse> getSchedule(@PathVariable("id") Long id) {
		return new ResponseEntity<ScheduleResponse>(this.service.getSchduleById(id), HttpStatus.OK);
	}

	// delete schedule by id
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteSchedule(@PathVariable("id") Long id) {
		return new ResponseEntity<String>(this.service.deleteScheduleById(id), HttpStatus.OK);
	}

	// get all schedul
	@GetMapping("/{pageNo}/{pageSize}")
	public ResponseEntity<Page<ScheduleResponse>> getAllSchedule(@PathVariable("pageNo") Integer pageNo,
			@PathVariable("pageSize") Integer pageSize) {
		Page<ScheduleResponse> sr = this.service.getAllSchdule(pageNo, pageSize);
		return new ResponseEntity<Page<ScheduleResponse>>(sr, HttpStatus.OK);
	}

	// for search schedule
	@PostMapping("/search/{pageNo}/{pageSize}/{sortBy}")
	public ResponseEntity<List<ScheduleResponse>> search(@RequestBody ScheduleRequest request,
			@PathVariable("pageNo") Integer pageNo, @PathVariable("pageSize") Integer pageSize,
			@PathVariable("sortBy") String sortBy) {
		List<ScheduleResponse> sr = this.service.searchShdule(pageNo, pageSize, request, sortBy);
		return new ResponseEntity<List<ScheduleResponse>>(sr, HttpStatus.OK);
	}

	// for updating a schedule
	@PutMapping("/")
	public ResponseEntity<ScheduleResponse> update(@RequestBody ScheduleRequest request) {
		return new ResponseEntity<ScheduleResponse>(this.service.updateSchedule(request), HttpStatus.OK);
	}
	
	@PutMapping("/status/{id}")
	public ResponseEntity<?> updateActiveinactive(@RequestBody Boolean status,@PathVariable("id") Long id) {
		
	    Boolean response = service.updatestatus(id, status);

        if (response) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(404).body(response); // Or another appropriate status code
        }
        
	}

}
