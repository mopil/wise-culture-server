package mjucapstone.wiseculture.location.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mjucapstone.wiseculture.location.config.AreaCode;
import mjucapstone.wiseculture.location.config.ContentCode;
import mjucapstone.wiseculture.location.dto.LocationResponse;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

	public LocationResponse toResponse() {
		return LocationResponse.builder()
				.locationId(id)
				.title(title)
				.address(address)
				.mapX(mapX)
				.mapY(mapY)
				.firstImage(firstImage)
				.areaName(AreaCode.getAreaName(areaCode))
				.contentName(ContentCode.getContentName(contentTypeId))
				.build();
	}

}
