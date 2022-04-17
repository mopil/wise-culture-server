package mjucapstone.wiseculture.member.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class DeleteMemberForm {

    @NotEmpty
    private String password;

    @NotEmpty
    private String passwordCheck;
}
