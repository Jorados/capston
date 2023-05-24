package jorados.capston.exception;

public class AlreadyReservedTime extends SeongjinException{

    private static final String MESSAGE = "이미 예약이 완료되거나 진행중인 시간대입니다.";
    public AlreadyReservedTime() {
        super(MESSAGE);
    }
    @Override
    public int getStatusCode() {
        return 404;
    }
}
