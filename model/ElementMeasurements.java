package com.azuga.museum.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * This class works on museum data from museum api
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class ElementMeasurements{

	@Id
	@GeneratedValue
	private Integer elementsID;


	@JsonProperty("Height")
	private String height;

	@JsonProperty("Width")
	private String width;
}