package mjucapstone.wiseculture.member.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import mjucapstone.wiseculture.board.Board;
import mjucapstone.wiseculture.comment.Comment;
import mjucapstone.wiseculture.member.dto.MemberResponse;
import mjucapstone.wiseculture.message.Message;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@ToString
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

    @JsonIgnore
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<Message> receivedMessages = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<Message> sentMessages = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Board> boards = new ArrayList<>();

    @Builder
    public Member(String userId, String email, String name, String nickname, String password, String phoneNumber, int point) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.point = point;
    }

    protected Member() {

    }

    public MemberResponse toResponse() {
        return MemberResponse.builder()
                .memberId(id)
                .userId(userId)
                .email(email)
                .name(name)
                .nickname(nickname)
                .password(password)
                .point(point)
                .phoneNumber(phoneNumber)
                .build();
    }
}
