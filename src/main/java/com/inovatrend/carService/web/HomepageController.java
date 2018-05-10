package com.inovatrend.carService.web;

import com.inovatrend.carService.domain.Car;
import com.inovatrend.carService.domain.Service;
import com.inovatrend.carService.service.CarManager;
import com.inovatrend.carService.service.ServiceManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class HomepageController {

    private final ServiceManager serviceManager;
    private final CarManager carManager;

    public HomepageController(ServiceManager serviceManager, CarManager carManager) {
        this.serviceManager = serviceManager;
        this.carManager = carManager;
    }

    @RequestMapping("/")
    public String showHomePage(Model model){
        Page<Service> lastTenService = serviceManager.getAllServices(PageRequest.of(0, 10, new Sort(Sort.Direction.DESC, "serviceTime")));

            model.addAttribute("services", lastTenService);

            List<Car> allCars = carManager.getAllCars();
            model.addAttribute("cars" , allCars);

        return "index";

    }

    @GetMapping(value = "/login")
    public String login(Model model) {
        return "login";
    }

    @PostMapping(value = "/login")
    public String loginPage(Model model) {
        return "login";
    }



}
