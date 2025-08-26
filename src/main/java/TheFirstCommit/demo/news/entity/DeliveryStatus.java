package TheFirstCommit.demo.news.entity;

public enum DeliveryStatus {
    COMPLETED_PRODUCTION("제작 완료"),
    SHIPPING("배송 중"),
    DELIVERED("배송 완료");

    public String description;

    DeliveryStatus(String description) {
        this.description = description;
    }

}