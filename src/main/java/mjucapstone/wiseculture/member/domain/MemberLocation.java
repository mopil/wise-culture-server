package mjucapstone.wiseculture.member.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity @Getter
public class MemberLocation {

    @Id @GeneratedValue
    @Column(name = "member_location_id")
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "location_id")
//    private Location location;
}
