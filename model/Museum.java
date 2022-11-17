package com.azuga.museum.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * This class works on museum data from museum api
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Museum{

	@JsonProperty("artistDisplayName")
	private String artistDisplayName;

	@JsonProperty("country")
	private String country;

	@JsonProperty("objectDate")
	private String objectDate;

	@JsonProperty("geographyType")
	private String geographyType;

	@JsonProperty("objectURL")
	private String objectURL;

	@JsonProperty("isHighlight")
	private boolean isHighlight;

	@JsonProperty("reign")
	private String reign;

	@JsonProperty("county")
	private String county;

	@JsonProperty("objectEndDate")
	private int objectEndDate;

	@JsonProperty("artistGender")
	private String artistGender;

	@JsonProperty("repository")
	private String repository;

	@JsonProperty("dynasty")
	private String dynasty;

	@JsonProperty("portfolio")
	private String portfolio;

	@JsonProperty("excavation")
	private String excavation;

	@JsonProperty("state")
	private String state;

	@JsonProperty("artistAlphaSort")
	private String artistAlphaSort;

	@JsonProperty("period")
	private String period;

	@JsonProperty("primaryImage")
	private String primaryImage;

	@JsonProperty("subregion")
	private String subregion;

	@JsonProperty("classification")
	private String classification;

	@OneToMany(cascade = CascadeType.ALL)
	@JsonProperty("tags")
	@ToString.Exclude
	private List<TagsItem> tags;

	@JsonProperty("objectWikidata_URL")
	private String objectWikidataURL;

	@JsonProperty("isTimelineWork")
	private boolean isTimelineWork;

	@JsonProperty("accessionYear")
	private String accessionYear;

	@JsonProperty("region")
	private String region;

	@JsonProperty("primaryImageSmall")
	private String primaryImageSmall;

	@JsonProperty("isPublicDomain")
	private boolean isPublicDomain;

	@JsonProperty("artistSuffix")
	private String artistSuffix;

	@JsonProperty("city")
	private String city;

	@JsonProperty("linkResource")
	private String linkResource;

	@JsonProperty("artistPrefix")
	private String artistPrefix;

	@JsonProperty("artistWikidata_URL")
	private String artistWikidataURL;

	@JsonProperty("medium")
	private String medium;

	@JsonProperty("title")
	private String title;

	@JsonProperty("locale")
	private String locale;

	@JsonProperty("accessionNumber")
	private String accessionNumber;

	@JsonProperty("artistEndDate")
	private String artistEndDate;

	@JsonProperty("rightsAndReproduction")
	private String rightsAndReproduction;

	@JsonProperty("metadataDate")
	private String metadataDate;

	@JsonProperty("creditLine")
	private String creditLine;

	@JsonProperty("artistRole")
	private String artistRole;

	@JsonProperty("department")
	private String department;

	@OneToMany(cascade = CascadeType.ALL)
	@JsonProperty("measurements")
	@ToString.Exclude
	private List<MeasurementsItem> measurements;

	@ElementCollection
	@JsonProperty("additionalImages")
	@ToString.Exclude
	private List<String> additionalImages;

	@JsonProperty("GalleryNumber")
	private String galleryNumber;

	@JsonProperty("objectBeginDate")
	private int objectBeginDate;

	@JsonProperty("artistBeginDate")
	private String artistBeginDate;

	@JsonProperty("artistULAN_URL")
	private String artistULANURL;

	@JsonProperty("culture")
	private String culture;

	@JsonProperty("artistNationality")
	private String artistNationality;

	@JsonProperty("objectName")
	private String objectName;

	@JsonProperty("artistDisplayBio")
	private String artistDisplayBio;

	@JsonProperty("locus")
	private String locus;

	@JsonProperty("river")
	private String river;

	@OneToMany(cascade = CascadeType.ALL)
	@JsonProperty("constituents")
	@ToString.Exclude
	private List<ConstituentsItem> constituents;

	@Id
	@JsonProperty("objectID")
	private int objectID;

	@JsonProperty("dimensions")
	private String dimensions;
}