package mjucapstone.wiseculture.location.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity @Getter
public class Location {
	
	@Id @GeneratedValue
	@Column(name = "location_id")
	private Long id;

	private String title;
	private String address;
	private Long areaCode;
	private Long contentTypeId;
	private String firstImage;

	@Column(name = "map_x")
	private double mapX;

	@Column(name = "map_y")
	private double mapY;

	@Builder
	public Location(String title, String address, Long areaCode, Long contentTypeId, String firstImage, double mapX, double mapY) {
		this.title = title;
		this.address = address;
		this.areaCode = areaCode;
		this.contentTypeId = contentTypeId;
		this.firstImage = firstImage;
		this.mapX = mapX;
		this.mapY = mapY;
	}

	protected Location() {

	}
}
