package ifive.idrop.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SubscribeRequest {
    private Long driverId;
    private String childName;
    private List<SubscribeLocationRequest> locationDatas = new ArrayList<>();
}