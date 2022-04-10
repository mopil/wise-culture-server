package mjucapstone.wiseculture.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class LoginException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7711897124872030318L;

	public LoginException(String message) {
        super(message);
    }
}
