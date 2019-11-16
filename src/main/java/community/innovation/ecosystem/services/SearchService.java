package community.innovation.ecosystem.services;

import community.innovation.ecosystem.entities.Knowledge;
import community.innovation.ecosystem.repositories.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    private SearchRepository searchRepository;

    TextCriteria criteria;

    @Autowired
    public SearchService(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public List<Knowledge> searchResult(String keyword) {

        criteria= TextCriteria.forDefaultLanguage().matchingAny(keyword);

        return searchRepository.findAllBy(criteria);

        /* to modify search query with score
        TextCriteria criteria = TextCriteria.forDefaultLanguage()
                .matchingAny("coffee", "cake");

        Query query = TextQuery.queryText(criteria)
                .sortByScore()
                .with(new PageRequest(0, 5));

        List<CookingRecipe> recipes = template.find(query, CookingRecipe);*/
    }
}
