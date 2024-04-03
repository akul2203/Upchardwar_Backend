package com.upchardwar.app.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PageAppointmentDto {
	private List<?> contents;
	private Integer pageNo;
	private Integer pageSize;
	private Long totalElements;
	private Integer totalPages;
	private Boolean lastPage; 
}
