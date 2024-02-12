package ifive.idrop.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@RequiredArgsConstructor
public class PickUp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pickup_id")
    private Long id;

    private String startImage;
    private String endImage;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String reservedTime; // cron 식으로 변경

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pickup_subscribe_id")
    private PickUpSubscribe pickUpSubscribe;

    public void updatePickUpSubscribe(PickUpSubscribe subscribe) {
        this.pickUpSubscribe = subscribe;
        subscribe.getPickUpList().add(this);
    }
}