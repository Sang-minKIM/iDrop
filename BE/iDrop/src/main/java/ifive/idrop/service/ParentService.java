package ifive.idrop.service;

import ifive.idrop.dto.BaseResponse;
import ifive.idrop.dto.SubscribeRequest;
import ifive.idrop.entity.*;
import ifive.idrop.entity.enums.PickUpStatus;
import ifive.idrop.exception.CommonException;
import ifive.idrop.exception.ErrorCode;
import ifive.idrop.repository.DriverRepository;
import ifive.idrop.repository.ParentRepository;
import ifive.idrop.repository.PickUpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParentService {
    private final DriverRepository driverRepository;
    private final ParentRepository parentRepository;
    private final PickUpRepository pickUpRepository;

    public BaseResponse<String> createSubscribe(Parent parent, SubscribeRequest subscribeRequest) {
        Optional<Driver> driver = driverRepository.findById(subscribeRequest.getDriverId());
        checkDriverExist(driver);
        Optional<Child> child = parentRepository.findChild(parent.getId(), subscribeRequest.getChildName());
        checkChildExist(child);

        PickUpSubscribe subscribe = createPickUpSubscribe();
        PickUpLocation location = createPickUpLocation(subscribeRequest);
        createPickUpInfo(subscribeRequest, child, driver, location, subscribe);

        return BaseResponse.success();
    }

    private PickUpSubscribe createPickUpSubscribe() {
        PickUpSubscribe subscribe = PickUpSubscribe.builder()
                .status(PickUpStatus.WAIT)
                .requestDate(LocalDateTime.now().plusDays(1))
                .expiredDate(LocalDateTime.now().plusDays(1).plusWeeks(4))
                .build();
        pickUpRepository.savePickUpSubscribe(subscribe);
        return subscribe;
    }

    private void createPickUpInfo(SubscribeRequest subscribeRequest, Optional<Child> child, Optional<Driver> driver, PickUpLocation location, PickUpSubscribe subscribe) {
        PickUpInfo pickUpInfo = PickUpInfo.builder()
                .child(child.get())
                .driver(driver.get())
                .schedule(subscribeRequest.getRequestDate())
                .pickUpLocation(location)
                .build();
        pickUpInfo.updatePickUpSubscribe(subscribe);
        pickUpInfo.updatePickUpLocation(location);
        pickUpRepository.savePickUpInfo(pickUpInfo);
    }

    private PickUpLocation createPickUpLocation(SubscribeRequest subscribeRequest) {
        PickUpLocation location = PickUpLocation.builder()
                .startAddress(subscribeRequest.getStartLocation())
                .startLatitude(subscribeRequest.getStartLatitude())
                .startLongitude(subscribeRequest.getStartLongitude())
                .endAddress(subscribeRequest.getEndLocation())
                .endLatitude(subscribeRequest.getEndLatitude())
                .endLongitude(subscribeRequest.getEndLongitude())
                .build();
        pickUpRepository.savePickUpLocation(location);
        return location;
    }

    private void checkChildExist(Optional<Child> child) {
        if (child.isEmpty()) {
            throw new CommonException(ErrorCode.CHILD_NOT_EXIST);
        }
    }

    private void checkDriverExist(Optional<Driver> driver) {
        if (driver.isEmpty()) {
            throw new CommonException(ErrorCode.DRIVER_NOT_EXIST);
        }
    }
}
