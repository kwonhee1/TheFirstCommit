package TheFirstCommit.demo.news.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class ResponseNewsBoxDto {
    private final List<ResponseNewsDto> news;
    private final String currentMonthDeliveryStatus;
}