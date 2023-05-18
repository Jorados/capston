package jorados.capston.service;


import jorados.capston.domain.Center;
import jorados.capston.domain.type.CenterStatus;
import jorados.capston.exception.CenterNotFound;
import jorados.capston.repository.CenterRepository;
import jorados.capston.request.CenterEdit;
import jorados.capston.response.CenterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 시간 남으면 클래스 분리 하자.
@Service
@RequiredArgsConstructor
public class CenterService {

    private final CenterRepository centerRepository;

    //센터등록
    @Transactional
    public void save(Center center){
        Center save_Center =  Center.builder()
                .center_name(center.getCenter_name())
                .status(CenterStatus.POSSIBLE)
                .openTime(center.getOpenTime())
                .closeTime(center.getCloseTime())
                .lat(center.getLat())
                .lng(center.getLng())
                .build();
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


    //센터정보 수정하기
    @Transactional
    public void center_update(Long id, CenterEdit CenterEdit){
        // 아직 ㄴ
    }


    //센터 삭제하기
    public void delete_center(Long id){
        Center findCenter = centerRepository.findById(id).orElseThrow(() -> new CenterNotFound());
        centerRepository.delete(findCenter);
    }

}
