package com.upchardwar.app.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerClass {
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<CustomExceptionMessage> userNotFound(UserNotFoundException  userNotFoundException)
	{
		return new ResponseEntity<CustomExceptionMessage>(new CustomExceptionMessage(new Date().toString(), 500, "Exception in process....", userNotFoundException.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
//	@ExceptionHandler(ResourceNotFoundException.class)
//	public ResponseEntity<CustomExceptionMessage> resourceNotFound(ResourceNotFoundException resourceNotFoundException)
//	{
//		return new ResponseEntity<CustomExceptionMessage>(new CustomExceptionMessage(new Date().toString(), 500, "Exception in process....", resourceNotFoundException.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
//	}
	
	@ExceptionHandler(UserAlreadyExistException.class)
	public ResponseEntity<CustomExceptionMessage> userAlreadyExist(UserAlreadyExistException  userAlreadyExistException)
	{
		return new ResponseEntity<CustomExceptionMessage>(new CustomExceptionMessage(new Date().toString(), 500, "Exception in process....", userAlreadyExistException.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ResourceAlreadyExistException.class)
	public ResponseEntity<CustomExceptionMessage> resourceAlreadyExist(ResourceAlreadyExistException  resourceAlreadyExistException)
	{
		return new ResponseEntity<CustomExceptionMessage>(new CustomExceptionMessage(new Date().toString(), 500, "Exception in process....", resourceAlreadyExistException.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler(ResourceNotApprovedException.class)
	public ResponseEntity<CustomExceptionMessage> resourceNotApproved(ResourceNotApprovedException  resourceNotApprovedException)
	{
		return new ResponseEntity<CustomExceptionMessage>(new CustomExceptionMessage(new Date().toString(), 500, "Exception in process....", resourceNotApprovedException.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(OtpExpireException.class)
	public ResponseEntity<CustomExceptionMessage> otpExpire(OtpExpireException resourceNotApprovedException)
	{
		return new ResponseEntity<CustomExceptionMessage>(new CustomExceptionMessage(new Date().toString(), 500, "Exception in process....", resourceNotApprovedException.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<CustomExceptionMessage> badReq(BadRequestException exception)
	{
		return new ResponseEntity<CustomExceptionMessage>(new CustomExceptionMessage(new Date().toString(), 500, "Exception in process....", exception.getMessage()),HttpStatus.BAD_REQUEST);
	}
}
