package mjucapstone.wiseculture.member.domain;

import lombok.Builder;
import lombok.Getter;
import mjucapstone.wiseculture.location.Location;

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

    @Builder
    public Member(String userId, String email, String name, String nickname, String password, String phoneNumber) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    protected Member() {

    }
}
