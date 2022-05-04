package mjucapstone.wiseculture.board;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import mjucapstone.wiseculture.location.domain.Location;

@Data
public class BoardForm {
	
	@NotEmpty
	private String title;
	
	@NotEmpty
	private String content;
	
	private Location location;

}
