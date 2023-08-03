package jorados.capston.service;


import jorados.capston.domain.Center;
import jorados.capston.domain.User;
import jorados.capston.domain.type.CenterStatus;
import jorados.capston.exception.CenterNotFound;
import jorados.capston.exception.UserNotFound;
import jorados.capston.repository.CenterRepository;
import jorados.capston.repository.UserRepository;
import jorados.capston.dto.request.CenterEdit;
import jorados.capston.dto.response.CenterInfoResponseDto;
import jorados.capston.dto.response.CenterResponse;
import jorados.capston.dto.response.CenterResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

// 시간 남으면 클래스 분리 하자.
@Service
@RequiredArgsConstructor
public class CenterService {

    private final CenterRepository centerRepository;
    private final UserRepository userRepository;

    /**
     *  기본 Crud
     */

    //센터 등록
    @Transactional
    public void save(Center center,User user){
        Center save_Center =  Center.builder()
                .center_name(center.getCenter_name())
                .status(CenterStatus.POSSIBLE)
                .openTime(center.getOpenTime())
                .closeTime(center.getCloseTime())
                .lat(center.getLat())
                .lng(center.getLng())
                .user(user)
                .address(center.getAddress())
                .price(center.getPrice())
                .imgUrl(center.getImgUrl())
                .build();

        centerRepository.save(save_Center);
    }

    //특정 센터 정보가져오기
    public CenterResponse read_Center(Long center_id){
        Center findCenter = centerRepository.findById(center_id).orElseThrow(() -> new CenterNotFound());

        CenterResponse centerResponse = CenterResponse.builder()
                .center_name(findCenter.getCenter_name())
                .status(CenterStatus.POSSIBLE)
                .openTime(findCenter.getOpenTime())
                .closeTime(findCenter.getCloseTime())
                .lat(findCenter.getLat())
                .lng(findCenter.getLng())
                .build();
        return centerResponse;
    }

    //센터 정보 싹 다 조회
    public List<Center> findAll(){
        List<Center> findCenterAll = centerRepository.findAll();
        return findCenterAll;
    }

    // 센터 정보 싹 다 조회
    @Transactional(readOnly = true)
    public Page<CenterResponseDto> getAllCenter(Pageable pageable) {
        return centerRepository.findAll(pageable).map(CenterResponseDto::fromEntity);
    }

    // 센터 정보 특정 조회
    @Transactional(readOnly = true)
    public CenterInfoResponseDto getCenterInfo(Long centerId,User user) {
        Center center = centerRepository.findById(centerId).orElseThrow(() -> new CenterNotFound());
        return CenterInfoResponseDto.fromEntity(center);
    }

    //센터 삭제하기
    public void delete_center(Long id){
        Center findCenter = centerRepository.findById(id).orElseThrow(() -> new CenterNotFound());
        centerRepository.delete(findCenter);
    }


    /**
     *  회원 정보를 이용한 센터 Crud
     */

    //센터 예약하기 - Create
    @Transactional
    public void CenterReserveSave(User user,Center center){
        User findUser = userRepository.findByUsername(user.getUsername()).orElseThrow(() -> new UserNotFound());

        Center saveCenter = Center.builder()
                .center_name(center.getCenter_name())
                .openTime(center.getOpenTime())
                .closeTime(center.getOpenTime())
                .lat(center.getLat())
                .lng(center.getLng())
                .user(findUser)
                .build();

        centerRepository.save(saveCenter);
    }

    // 내가 만든 센터 - Read
    public List<Center> CenterReserveRead(User user){
        User findUser = userRepository.findByUsername(user.getUsername()).orElseThrow(() -> new UserNotFound());
        List<Center> findCenter = centerRepository.findCenter(findUser.getId());
        return findCenter;
    }

    // 센터 삭제
    public void CenterReserveDelete(Long centerId){
        Center findCenter = centerRepository.findById(centerId).orElseThrow(() -> new CenterNotFound());
        centerRepository.delete(findCenter);
    }

    @Transactional
    public Page<Center> search(String searchValue, Pageable pageable){
        Page<Center> centersPage = centerRepository.findAllSearch(searchValue, pageable);
        return centersPage;
    }



}
