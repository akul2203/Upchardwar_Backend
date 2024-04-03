package com.upchardwar.app.controller.doctor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.upchardwar.app.entity.doctor.TimeSlote;
import com.upchardwar.app.services.doctor.ITimeSlotService;




@RestController
@RequestMapping("/upchardwar/timeslote")
@CrossOrigin("*")
public class TimesloteController {

	@Autowired
	private ITimeSlotService iTimeSlotService;

//	private static final String MOCK_ORDER_ID = "id";

	
	@PostMapping("/create-order")
	  public ResponseEntity<Map<String, String>> createOrder(@RequestBody Map<String, Object> data) {
        try {
        	
           RazorpayClient client	=new RazorpayClient("rzp_test_EobRbpOb7mfGav", "try8xdpeeLofWbJWyYFWvguy");
           
           try {

        	   JSONObject ob = new JSONObject();

        	   Integer amount = (Integer) data.get("amount");

               
        	   ob.put("amount", amount/100); // amount in the smallest currency unit

        	   ob.put("currency", "INR");

        	   ob.put("receipt", "order_rcptid");
   
        	   
        	   //creating new order
        	   Order order = client.Orders.create(ob);

        	   System.out.println(order);
        	   Map<String, String> response = new HashMap<>();
//               response.put("orderId",MOCK_ORDER_ID);
               response.put("orderId", order.get("id"));
               response.put("order",order.toString());
           
        	    return new ResponseEntity<>(response, HttpStatus.OK);
        	 } catch (RazorpayException e) {

        	   // Handle Exception

        	   System.out.println(e.getMessage());
        	   return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        	 }
         
        } catch (Exception e) {
            // Handle exception appropriately
        	 System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
		
	
	
    @PostMapping("/capture-payment")
    public ResponseEntity<Map<String, String>> capturePayment(@RequestBody Map<String, String> data) {
        try {
            Map<String, String> response = new HashMap<>();
            response.put("status", "Payment captured successfully");
            response.put("data",data.toString());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            // Handle exception appropriately
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	
	@PutMapping("/update/isbooked/{timeSlotId}")
	public ResponseEntity<Map<String, Object>> updateIsBookedStatus(@PathVariable Long timeSlotId,@RequestBody Map<String, Boolean> request) {
		Boolean newIsBookedStatus = request.get("isBooked");

		Map<String, Object> response = iTimeSlotService.updateTimeSlot(timeSlotId, newIsBookedStatus);

		if ((boolean) response.get("success")) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
	
	
    @GetMapping("get/{id}")
    public ResponseEntity<Optional<TimeSlote>> getTimeSlotById(@PathVariable Long id) {
        Optional<TimeSlote> timeSlot = iTimeSlotService.getbyid(id);
        return ResponseEntity.ok().body(timeSlot);
    }

}
