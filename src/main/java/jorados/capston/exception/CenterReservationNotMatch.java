package jorados.capston.exception;

public class CenterReservationNotMatch extends SeongjinException{
    private static final String MESSAGE = "해당 센터에 존재하지 않는 예약입니다.";
    public CenterReservationNotMatch() {
        super(MESSAGE);
    }
    @Override
    public int getStatusCode() {
        return 404;
    }
}
