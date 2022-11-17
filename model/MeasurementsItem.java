package com.azuga.museum.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

/**
 * This class works on museum data from museum api
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class MeasurementsItem{

	@Id
	@GeneratedValue
	private Integer measurementsID;

	@OneToOne(cascade = CascadeType.ALL)
	@JsonProperty("elementMeasurements")
	private ElementMeasurements elementMeasurements;

	@JsonProperty("elementDescription")
	private String elementDescription;

	@JsonProperty("elementName")
	private String elementName;
}