package mjucapstone.wiseculture.location;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

import mjucapstone.wiseculture.member.Member;

@Entity
public class Location {
	
	@Id @GeneratedValue
	@Column(name = "location_id")
	private Long id;
	
	/*@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;*/
	@ManyToMany
	//@JoinTable(name = "member_id")
	private List<Member> members = new ArrayList<>();
	
	private Float latitude;
	private Float longitude;
	
	@Enumerated(EnumType.STRING)
	private Category category;
	
	private String name;
	
}
