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

public class TagsItem{


	@Id
	@GeneratedValue
	private Integer tagsID;

	@JsonProperty("AAT_URL")
	private String aATURL;

	@JsonProperty("term")
	private String term;

	@JsonProperty("Wikidata_URL")
	private String wikidataURL;
}