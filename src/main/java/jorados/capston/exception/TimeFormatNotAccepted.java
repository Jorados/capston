package jorados.capston.exception;

public class TimeFormatNotAccepted extends SeongjinException{
    private static final String MESSAGE = "시간 형식이 옳바르지 않습니다.";

    public TimeFormatNotAccepted() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode(){
        return 404;
    }
}
