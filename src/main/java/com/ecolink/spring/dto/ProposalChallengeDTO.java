package com.ecolink.spring.dto;

import java.time.LocalDate;

import com.ecolink.spring.entity.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProposalChallengeDTO {
	private Long id;
	private StartupDTO startup;
	private String title;
	private String description;
	private LocalDate date;
	private Status status;
}
