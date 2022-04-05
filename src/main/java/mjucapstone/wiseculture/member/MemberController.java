package mjucapstone.wiseculture.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/new")
    public ResponseEntity<Member> signUp(@Valid @ModelAttribute MemberSignUpDto form, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return (ResponseEntity<Member>) ResponseEntity.internalServerError();
        }
        Member member = form.buildMember();
        memberService.signUp(member);
        return ResponseEntity.ok().body(member);
    }

    @GetMapping("/nickname-check/{nickname}")
    public boolean nicknameCheck(@PathVariable String nickname) {
        return memberService.nicknameCheck(nickname);
    }


}
