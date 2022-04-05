package mjucapstone.wiseculture.member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    private String name;
    private String nickname;
    private String password;
    private int point;

    @Column(name = "phone")
    private String phoneNumber;
}
