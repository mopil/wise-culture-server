package mjucapstone.wiseculture.member;

import lombok.Getter;
import mjucapstone.wiseculture.location.Location;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    private String email;
    private String name;
    private String nickname;
    private String password;
    private int point;

    @Column(name = "phone")
    private String phoneNumber;

    @OneToMany
    private List<Location> locationList = new ArrayList<>();
}
