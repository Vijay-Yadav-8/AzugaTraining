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
public class ConstituentsItem{

	@Id
	@GeneratedValue
	@JsonProperty("constituentID")
	private int constituentID;

	@JsonProperty("role")
	private String role;

	@JsonProperty("gender")
	private String gender;

	@JsonProperty("constituentWikidata_URL")
	private String constituentWikidataURL;

	@JsonProperty("name")
	private String name;

	@JsonProperty("constituentULAN_URL")
	private String constituentULANURL;
}