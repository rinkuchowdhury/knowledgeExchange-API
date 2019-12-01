package community.innovation.ecosystem.controllers;

import community.innovation.ecosystem.entities.User;
import community.innovation.ecosystem.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.util.List;

@RestController
@CrossOrigin
@Api(value = "User", description = "Available endpoints of this API", tags = {"User"})
@RequestMapping("/api")
public class UserRestController {

    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value="Get all user", tags = { "User" })
    @GetMapping("/users")
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @ApiOperation(value="Get user", tags = { "User" })
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable ("id") String id){
        return userService.getUser(id);
    }

    // if email doesn't exist and no userId given then registration : create user
    // if userId given then edit user data
    @ApiOperation(value="Add or edit user. Please use \"ROLE_xxx\"  instead {ROLE_xxx} inside role payload", tags = { "User" })
    @PostMapping("/users")
    public String createOrUpdateUser(@Valid @RequestBody User user, @ApiIgnore BindingResult result, @ApiIgnore WebRequest request, @ApiIgnore Error error) throws ValidationException {
        return userService.createOrUpdateUser(user,result,request,error);
    }

    @ApiOperation(value="Delete user", tags = { "User" })
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable ("id") String id){
        return userService.deleteUser(id);
    }


    @ApiOperation(value="User registration confirmation", tags = { "User" })
    @GetMapping("/confirmRegistration")
    public String confirmRegistration(@ApiIgnore WebRequest request, @RequestParam("token") String verificationToken){
        return userService.activateUser(request,verificationToken);
    }

    @ApiOperation(value="User verification status", tags = { "User" })
    @GetMapping("/verificationStatus/{id}")
    public String verificationStatus(@PathVariable ("id") String id){
        return userService.verificationStatus(id);
    }
}
