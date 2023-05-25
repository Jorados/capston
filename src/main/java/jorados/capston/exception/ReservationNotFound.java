package jorados.capston.exception;

public class ReservationNotFound extends SeongjinException{
    private static final String MESSAGE = "일치하는 예약 정보가 존재하지 않습니다.";
    public ReservationNotFound() {
        super(MESSAGE);
    }
    @Override
    public int getStatusCode() {
        return 404;
    }
}
