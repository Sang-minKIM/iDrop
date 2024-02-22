package ifive.idrop.websocket.direction;

import ifive.idrop.websocket.direction.dto.Direction;
import ifive.idrop.websocket.direction.dto.NaverDirectionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Service
@RequiredArgsConstructor
public class NaverDirectionFinder {

    @Value("${naver_direction_url}")
    private String NAVER_DIRECTION_URL;

    @Value("${naver_client_id}")
    private String clientId;

    @Value("${naver_client_secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    public Direction getDirection(Double startLongitude, Double startLatitude, Double endLongitude, Double endLatitude) throws Exception {
        String url = makeUrl(startLongitude, startLatitude, endLongitude, endLatitude);
        HttpHeaders headers = makeHeader();

        RequestEntity<Void> request = RequestEntity
                .get(url)
                .headers(headers)
                .build();

        NaverDirectionResponse response = restTemplate.exchange(request, NaverDirectionResponse.class).getBody();
        return new Direction(response.getPath());
    }

    private String makeUrl(Double startLongitude, Double startLatitude, Double endLongitude, Double endLatitude) {
        StringBuilder sb = new StringBuilder(NAVER_DIRECTION_URL);
        sb.append("?start=").append(startLongitude).append(",").append(startLatitude);
        sb.append("&goal=").append(endLongitude).append(",").append(endLatitude);
        return sb.toString();
    }

    private HttpHeaders makeHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-NCP-APIGW-API-KEY-ID", clientId);
        headers.set("X-NCP-APIGW-API-KEY", clientSecret);
        return headers;
    }
}
