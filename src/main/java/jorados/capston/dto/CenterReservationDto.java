package jorados.capston.dto;

import jorados.capston.domain.Center;
import jorados.capston.domain.CenterReservation;
import jorados.capston.domain.User;
import jorados.capston.domain.type.ReservingTime;
import jorados.capston.dto.response.CenterInfoResponseDto;
import jorados.capston.dto.response.UserResponse;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class CenterReservationDto {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateReservationRequest {

        private LocalDate reservingDate;
        List<String> reservingTimes;
        private int headCount;
    }

    // 예약내역 체육관 정보
    @Data
    @Builder
    public static class ReservationCenterInfoResponse {
        private CenterInfoResponseDto center; //센터 상세정보
        private String date;
        private String openTime;
        private String closeTime;
        //private int pricePerHalfHour;
        private List<String> reservedTimes;
    }

    // 예약 생성정보
    @Data
    @Builder
    public static class CreateReservationResponse {
        private Long reservationId;
        private Long centerId;
        private String centerName;
        private String date;
        private String openTime;
        private String closeTime;
        private int pricePerHalfHour;
        //private List<ItemResponse> rentalItems;
        private List<String> reservedTimes;
        private int headCount;
    }

    // 예약 상세내역 정보
    @Data
    @Builder
    public static class ReservationInfoResponse {
        private Long reservationId;
        private Long centerId;
        private String name;
        private String status;
        private CenterReservationDto.UserResponse user;
        private String reservingDate;
        private List<String> reservingTime;
        private int headCount;
        private int price;
        private String imgUrl;

        //private String paymentType;

        public static ReservationInfoResponse fromEntity(CenterReservation reservation) {
            reservation.getReservingTimes().sort((a, b) -> Integer.compare(a.ordinal(), b.ordinal()));

            return ReservationInfoResponse.builder()
                    .reservationId(reservation.getId())
                    .centerId(reservation.getCenter().getId())
                    .name(reservation.getCenter().getCenter_name())
                    .user(CenterReservationDto.UserResponse.fromEntity(reservation.getUser()))
                    .reservingDate(reservation.getReservingDate().toString())
                    .reservingTime(reservation.getReservingTimes().stream()
                            .map(ReservingTime::getTime)
                            .collect(Collectors.toList()))
                    .headCount(reservation.getHeadCount())
                    .price(reservation.getPrice())
                    .imgUrl(reservation.getCenter().getImgUrl())
                    .headCount(reservation.getHeadCount())
//                    .paymentType(reservation.getPaymentType().toString())
//                    .items(ItemResponse.fromReservation(reservation))
                    .status(reservation.getStatus().toString())
                    .build();
        }
    }

    // 예약 내역 리스트 정보
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReservationResponse {
        private Long reservationId;
        private Long centerId;
        private String name;
        private String address;
        private String imgUrl;
        private String status;

        public static ReservationResponse fromEntity(CenterReservation reservation) {
            Center center = reservation.getCenter();
            return ReservationResponse.builder()
                    .reservationId(reservation.getId())
                    .centerId(center.getId())
                    .name(center.getCenter_name())
                    .address(center.getAddress())
                    .imgUrl(center.getImgUrl())
                    .status(reservation.getStatus().toString())
                    .build();
        }
    }


    // 체육관 예약 사용자 리스트
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CenterReservationUserResponse {
        private Long centerId;
        private Long reservationId;
        private String name;
        private String reservingDate;
        private String paymentDate;
        private String status;

        public static CenterReservationUserResponse fromEntity(CenterReservation reservation) {
            return CenterReservationUserResponse.builder()
                    .centerId(reservation.getCenter().getId())
                    .reservationId(reservation.getId())
                    .name(reservation.getUser().getUsername())
                    .reservingDate(reservation.getReservingDate().toString())
                    .status(reservation.getStatus().toString())
                    .build();
        }
    }

    @Data
    @Builder
    private static class UserResponse {
        private Long id;
        private String username;
        private String nickname;
        private String email;

        public static UserResponse fromEntity(User user) {
            return UserResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .nickname(user.getNickname())
                    .email(user.getEmail())
                    .build();
        }
    }

    @Data
    @Builder
    public static class PriceResponse {
        private int price;
    }
}
