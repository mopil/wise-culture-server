package mjucapstone.wiseculture.member.exception;

public class MemberNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1699943824385869101L;
	
	public MemberNotFoundException(String message) {
        super(message);
    }

}
