package community.innovation.ecosystem.services;

import community.innovation.ecosystem.entities.Institute;
import community.innovation.ecosystem.repositories.InstituteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstituteService {

    private InstituteRepository instituteRepository;

    public InstituteService(InstituteRepository instituteRepository) {
        this.instituteRepository = instituteRepository;
    }

    // get: retrieve all institutes
    public List<Institute> getAllInstitute(){
        return instituteRepository.findAll();
    }

    // get: retrieve a institute
    public Institute getInstitute(String instituteId){
        return instituteRepository.findById(instituteId).get();
    }

    // post : create a institute, if id available then edit a institute
    public String createOrUpdateInstitute(Institute institute){

        String instituteId=institute.getInstituteId();

        if(instituteId==null){
            instituteRepository.save(institute);
        }
        else {
            Boolean instituteIdExist=instituteRepository.existsByInstituteId(instituteId);
            if(instituteIdExist){
                instituteRepository.save(institute);
                return "Successfully edited";
            }
            else return "The id is not available in the portal";
        }
        return "Successfully added";

    }
    // delete: delete a institute
    public String deleteInstitute(String instituteId){
        instituteRepository.deleteById(instituteId);
        return "The id: "+instituteId+" related information has been deleted";
    }

}