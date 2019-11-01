package community.innovation.ecosystem.controllers;

import community.innovation.ecosystem.entities.Institute;
import community.innovation.ecosystem.services.InstituteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@Api(value = "Institute", description = "Available endpoints of this API", tags = {"Institute"})
@RequestMapping("/api")
public class InstituteRestController {

    private InstituteService instituteService;

    public InstituteRestController(InstituteService instituteService) {
        this.instituteService = instituteService;
    }

    @ApiOperation(value="Get all institutes", tags = { "Institute" })
    @GetMapping("/institutes")
    public List<Institute> getInstitutes(){
        return instituteService.getAllInstitute();
    }

    @ApiOperation(value="Get institute", tags = { "Institute" })
    @GetMapping("/institutes/{id}")
    public Institute getInstitute(@PathVariable("id") String instituteId){
        return instituteService.getInstitute(instituteId);
    }

    @ApiOperation(value="Add or edit institute", tags = { "Institute" })
    @PostMapping("/institutes")
    public String createOrUpdateInstitute(@Valid @RequestBody Institute institute){
        return instituteService.createOrUpdateInstitute(institute);
    }

    @ApiOperation(value="Delete institute", tags = { "Institute" })
    @DeleteMapping("/institutes/{id}")
    public String deleteInstitute(@PathVariable("id") String instituteId){
        return instituteService.deleteInstitute(instituteId);
    }
}