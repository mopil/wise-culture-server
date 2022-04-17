package mjucapstone.wiseculture.location;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import mjucapstone.wiseculture.member.domain.Member;

@Entity @Getter
public class Location {
	
	@Id @GeneratedValue
	@Column(name = "location_id")
	private Long id;
	
//	@OneToMany
//	private List<Member> likedMembers = new ArrayList<>();
	
	private float latitude;
	private float longitude;
	
	@Enumerated(EnumType.STRING)
	private Category category;

	private String name;
	
}
