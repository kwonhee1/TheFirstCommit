package TheFirstCommit.demo.news.dto;

import TheFirstCommit.demo.news.entity.DeliveryStatus;
import TheFirstCommit.demo.news.entity.NewsEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
public class ResponseNewsBoxDto {
    private List<NewsListResponseDto> news;
    @JsonProperty("status")
    private String currentMonthDeliveryStatus;

    public ResponseNewsBoxDto(List<NewsListResponseDto> news, DeliveryStatus currentMonthDeliveryStatus) {
        this.news = news;
        this.currentMonthDeliveryStatus = currentMonthDeliveryStatus.description;
    }
}