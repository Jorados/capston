package jorados.capston.exception;

public class PointLack extends SeongjinException{

    private static final String MESSAGE = "포인트가 부족합니다.";

    public PointLack() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode(){
        return 404;
    }
}
