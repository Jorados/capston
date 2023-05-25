package jorados.capston.exception;

public class UnAuthorizedAccess extends SeongjinException{
    private static final String MESSAGE = "접근 권한이 없습니다.";

    public UnAuthorizedAccess() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode(){
        return 404;
    }
}
