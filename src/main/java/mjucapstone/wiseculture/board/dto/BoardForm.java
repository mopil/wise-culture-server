package mjucapstone.wiseculture.board.dto;

import lombok.Data;
import mjucapstone.wiseculture.location.domain.Location;

import javax.validation.constraints.NotEmpty;

@Data
public class BoardForm {
	
	@NotEmpty
	private String title;
	
	@NotEmpty
	private String content;
	
	private Location location;

}
