package jorados.capston.exception;

public class CouldNotCancelReservation extends SeongjinException{
    private static final String MESSAGE = "취소할 수 없는 예약입니다.";
    public CouldNotCancelReservation() {
        super(MESSAGE);
    }
    @Override
    public int getStatusCode() {
        return 404;
    }
}
