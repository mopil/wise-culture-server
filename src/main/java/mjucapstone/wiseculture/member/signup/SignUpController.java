package mjucapstone.wiseculture.member.signup;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mjucapstone.wiseculture.member.Member;
import mjucapstone.wiseculture.member.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class SignUpController {

    private final MemberService memberService;

    @PostMapping("/new")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpDto form, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.getFieldErrors());
        }
        Member member = form.buildMember("112021");
        memberService.signUp(member);
        return ResponseEntity.ok().body(member);
    }

    @GetMapping("/nickname-check/{nickname}")
    public boolean nicknameCheck(@PathVariable String nickname) {
        return memberService.nicknameCheck(nickname);
    }


}
