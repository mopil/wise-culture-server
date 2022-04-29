package mjucapstone.wiseculture.board;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import mjucapstone.wiseculture.location.Location;
import mjucapstone.wiseculture.member.domain.Member;

@Data
public class BoardForm {
	
	@NotEmpty
	private String title;
	
	@NotEmpty
	private String content;
	
	private Location location;
	
	@NotNull
	private Member writer;

}
