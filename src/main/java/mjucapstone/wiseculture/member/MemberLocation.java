package mjucapstone.wiseculture.member;

import lombok.Getter;
import mjucapstone.wiseculture.location.Location;

import javax.persistence.*;

@Entity @Getter
public class MemberLocation {

    @Id @GeneratedValue
    @Column(name = "member_location_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;
}
