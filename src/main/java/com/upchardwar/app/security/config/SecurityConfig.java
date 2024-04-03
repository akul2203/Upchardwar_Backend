package com.upchardwar.app.security.config;

import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.upchardwar.app.security.SecurityFilter;

@Configuration
@EnableWebSecurity

@SuppressAjWarnings("ALL")
public class SecurityConfig {

	@Autowired
	private BCryptPasswordEncoder passwoerEncoder;

	@Autowired
	private UserDetailsService userDetailsService;

	private String[] pAll = {"upchardwar/doctor/reviewrating/**","/upchardwar/appointment/patient/all/count/{drId}","/upchardwar/appointment/patient/today/count/{drId}","/upchardwar/appointment/patient/count/{drId}","/upchardwar/doctor/mypatient/{drid}","upchardwar/appointment/cancel/{appointmentId}", "/upchardwar/doctor/cities", "/upchardwar/doctor/{id}",
			"upchardwar/lab/all/{pageNo}/{pageSize}/{sortBy}", "/upchardwar/chat/api/chat/upload-photo",
			"/upchardwar/user/users-by-email/{email}", "/upchardwar/user/users-by-emails",
			"/upchardwar/appointment/doctor/status/{doctorId}", "/upchardwar/user/getall",
			"/upchardwar/chat/messages/{id}", "/upchardwar/chat/messages/{senderId}/{recipientId}/count",
			"/upchardwar/chat/messages/{senderId}/{recipientId}", "/ws/**", "/upchardwar/invoice/create",
			"/upchardwar/invoice/get/doctor/{doctorId}", "/upchardwar/timeslote/get/{id}",
			"/upchardwar/patient/by-email/{email}", "/upchardwar/timeslote/update/isbooked/{timeSlotId}",
			"/upchardwar/schedule/{id}", "/upchardwar/appointment/book", "upchardwar/schedule/doctor/{doctorId}",
			"upchardwar/reviewrating/", "/upchardwar/doctor/userid/{userId}", "/upchardwar/auth/login",
			"/upchardwar/doctor/save", "/upchardwar/speciality/"

			, "/upchardwar/patient/save1", "/upchardwar/lab/save", "/upchardwar/lab/all", "/upchardwar/pharmacy/save",
			"/upchardwar/auth/login", "/upchardwar/auth/*", "/upchardwar/auth/generate-otp", "/upchardwar/auth/verify",
			"upchardwar/speciality/all", "/upchardwar/schedule/", "/upchardwar/appointment/book-appointment",
			"/upchardwar/appointment/notify", "upchardwar/labrequest/save",
			"/upchardwar/lab/all/{pageNo}/{pageSize}/{sortBy}",

			"/upchardwar/patient/save", "/upchardwar/lab/save", "/upchardwar/pharmacy/save",
			"/upchardwar/auth/sendemail", "/upchardwar/auth/generate-otp", "/upchardwar/auth/verify",
			"upchardwar/speciality/all", "/upchardwar/schedule/",

			"/upchardwar/appointment/book-appointment", "/upchardwar/appointment/notify",
			"/upchardwar/doctor/userid/{id}", "/upchardwar/doctor/{pageNo}/{pageSize}",

			"/upchardwar/appointment/book-appointment", "/upchardwar/appointment/notify", "/api/getImageApi/{imgName}",
			"upchardwar/lab/save1", "upchardwar/labBooking/", "upchardwar/lab/getLab/{id}",
			"/upchardwar/appointment/doctor/{doctorId}",
			"/upchardwar/appointment/doctor/{doctorId}/upcoming-appointments",
			"/upchardwar/appointment/doctor/today/{doctorId}", "/upchardwar/appointment/patient/{patientId}",
			"/upchardwar/user/search", "/upchardwar/auth/reset", "/upchardwar/forgetpassword/*",
			"/upchardwar/auth/change-password", "/upchardwar/labBooking/", "/socket/**",
			"upchardwar/labBooking/patientTotalBooking/{patientId}", "/upchardwar/labreviewrating/lab/{labId}",
			"/upchardwar/reviewrating/","/upchardwar/reviewrating/doctor/{doctorId}",
			"upchardwar/labTest/all/{pageNo}/{pageSize}/{sortBy}/{labId}" };

	private String[] accessByAdmin = { "/user/admin", "upchardwar/lab/delete/{id}" };

	private String[] accessByAdminDoctor = { "/upchardwar/doctor/", "/upchardwar/appointment/updateAppointment",
			"/upchardwar/appointment/rescheduleAppointment" };

	@SuppressWarnings("unused")
	private String[] accessByAdminDoctorPatient = { "upchardwar/reviewrating/add",
			"upchardwar/schedule/doctor/upcoming/{id}", "/upchardwar/doctor/{id}",
			"/upchardwar/doctor/{pageNo}/{pageSize}", "/upchardwar/appointment/appointmentDetails/{id}",
			"/upchardwar/appointment/all/{pageNo}/{pageSize}/{sortBy}" };

	private String[] accessByDoctor = { "/upchardwar/doctor/update/{id}", "/upchardwar/schedule/create","/upchardwar/schedule/status/{id}",
			"/upchardwar/appointment/createSchedule", "/upchardwar/appointment/createTimeSlote",
			"/upchardwar/appointment/todaysAppointments", "/upchardwar/appointment/cancelAppointment/{id}",
			"/upchardwar/appointment/countPatient",

			"/upchardwar/appointment/countTodaysPetient", "/upchardwar/appointment/countUpcomingAppointments" };

	@SuppressWarnings("unused")
	private String[] accessByLabPatientAdmin = { "upchardwar/labTest/get/{labTestId}",
			"upchardwar/lab/all/{pageNo}/{pageSize}/{sortBy}", "upchardwar/lab/search/{pn}/{ps}/{sortBy}" };

	private String[] accessByPatient = { "/upchardwar/patient/save1", "/upchardwar/patient/{id}",
			"/upchardwar/appointment/all/patient/{pageNo}/{pageSize}/{sortBy}", "upchardwar/labreviewrating/",
			"/upchardwar/reviewrating/", "upchardwar/labBooking/createTransaction/{amount}",
			"upchardwar/lab/{labId}/favorite/{patientId}",
			"upchardwar/lab/favorites/{patientId}/{pageNo}/{pageSize}/{sortBy}",
			"upchardwar/lab/{labId}/remove/{patientId}" };

	private String[] accessByLab = { "upchardwar/labTest/save", "upchardwar/lab/user/{userId}",
			"upchardwar/labBooking/labtests/{labTestId}/changeStatus/{labId}/{bookingId}",
			"upchardwar/labBooking/getAll/{labId}", "upchardwar/labBooking/lab/today/{labId}",
			"upchardwar/labBooking/totalBookig/{labId}", "upchardwar/labBooking/todaysTotalBookings/{labId}",
			"upchardwar/labBooking/success/{labId}" };

	@SuppressWarnings("unused")
	private String[] accessByLabAdmin = { "upchardwar/labTest/delete/{labTestId}",
			"upchardwar/labTest/update/{labTestId}" };

	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private SecurityFilter securityfilter;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwoerEncoder);
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}

	@Bean
	public SecurityFilterChain configurePaths(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().requestMatchers(pAll).permitAll().requestMatchers(accessByAdmin)
				.hasAuthority("ADMIN").requestMatchers(accessByAdminDoctor).hasAnyAuthority("ADMIN", "DOCTOR")

				.requestMatchers("accessByLabPatientAdmin").hasAnyAuthority("ADMIN", "PATIENT", "ADMIN")

				.requestMatchers(accessByDoctor).hasAuthority("DOCTOR").requestMatchers(accessByPatient)
				.hasAuthority("PATIENT").requestMatchers(accessByLab).hasAuthority("LAB").anyRequest().authenticated()
				.and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.addFilterBefore(securityfilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();

	}

	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedOriginPattern("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("OPTIONS");
		config.addAllowedMethod("HEAD");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("DELETE");
		config.addAllowedMethod("PATCH");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

}
