package gp.training.kim.bar.controller;

import gp.training.kim.bar.controller.entity.StoreHouseReport;
import gp.training.kim.bar.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;

    @GetMapping(value = "/report", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    public StoreHouseReport report(@RequestHeader final Integer admin) {
        return adminService.getIngredientsReport();
    }

}