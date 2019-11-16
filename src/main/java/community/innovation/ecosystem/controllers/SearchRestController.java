package community.innovation.ecosystem.controllers;

import community.innovation.ecosystem.entities.Knowledge;
import community.innovation.ecosystem.services.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@Api(value = "Search", description = "Available endpoints of this API", tags = {"Search"})
@RequestMapping("/api")
public class SearchRestController {

    private SearchService searchService;

    @Autowired
    public SearchRestController(SearchService searchService) {
        this.searchService = searchService;
    }

    @ApiOperation(value="Get search result", tags = { "Search" })
    @PostMapping("/search/{keyword}")
    public List<Knowledge> searchResult(@PathVariable("keyword") String keyword){
        return searchService.searchResult(keyword);
    }
}

