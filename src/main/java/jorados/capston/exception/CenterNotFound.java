package jorados.capston.exception;

public class CenterNotFound extends SeongjinException{

    private static final String MESSAGE = "존재하지 않는 센터입니다.";
    public CenterNotFound() {
        super(MESSAGE);
    }
    @Override
    public int getStatusCode() {
        return 404;
    }
}
