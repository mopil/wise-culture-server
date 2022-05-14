package mjucapstone.wiseculture.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mjucapstone.wiseculture.board.dto.BoardForm;
import mjucapstone.wiseculture.board.dto.BoardResponse;
import mjucapstone.wiseculture.comment.Comment;
import mjucapstone.wiseculture.member.domain.Member;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Getter
public class Board {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String title;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdDate;

    private String content;
    private int viewCount;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "location_id")
//    private Location location;
    private String locationTitle;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Board(String title, String content, Member writer) {
        this.title = title;
        this.content = content;
        this.member = writer;
        this.viewCount = 0;
    }

    public BoardResponse toResponse() {
        return BoardResponse.builder()
                .boardId(id)
                .writerNickName(member.getNickname())
                .title(title)
                .content(content)
                .locationTitle(locationTitle)
                .viewCount(viewCount)
                .createdDate(createdDate)
                .build();
    }

    /**
     * 비즈니스 메소드
     */
    // 게시글 조회수 증가
    public void addViewCount() {
        this.viewCount += 1;
    }

    // 게시글 수정
    public void update(BoardForm form) {
        this.content = form.getContent();
        this.locationTitle = form.getLocationTitle();
    }

}
