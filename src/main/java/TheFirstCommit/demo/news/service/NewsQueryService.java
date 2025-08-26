package TheFirstCommit.demo.news.service;

import TheFirstCommit.demo.family.entity.FamilyEntity;
import TheFirstCommit.demo.news.dto.ResponseNewsBoxDto;

public interface NewsQueryService {
    ResponseNewsBoxDto getNewsBox(FamilyEntity family);
}