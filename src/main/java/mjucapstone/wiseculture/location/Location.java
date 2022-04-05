package mjucapstone.wiseculture.location;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import mjucapstone.wiseculture.board.Board;

@Entity
public class Location {
	
	@Id @GeneratedValue
	@Column(name = "location_id")
	private Long id;
	
	@Column(name = "board_id")
	private Board board;
	
	private Float latitude;
	private Float longitude;
	
	@Enumerated(EnumType.STRING)
	private Category category;
	
	private String name;	
	
	enum Category {
		영화,
		축제,
		관광지
	}
	
}
