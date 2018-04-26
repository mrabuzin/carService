package com.inovatrend.carService.web;


import com.inovatrend.carService.domain.Car;
import com.inovatrend.carService.domain.Service;
import com.inovatrend.carService.service.CarManager;
import com.inovatrend.carService.service.ServiceManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@SessionAttributes("service")
@RequestMapping("/service")
public class ServiceController {

    private final ServiceManager serviceManager;
    private final CarManager carManager;

    public ServiceController(ServiceManager serviceManager, CarManager carManager) {
        this.serviceManager = serviceManager;
        this.carManager = carManager;
    }


    @RequestMapping(value = "/info/{serviceId}")
    public String serviceInfo(Model model, @PathVariable Long serviceId) {

        Optional<Service> service = serviceManager.getService(serviceId);
        model.addAttribute("service", service);

        return "service-info";
    }

    @RequestMapping(value = "/list")
    public String listAllServices(Model model, @RequestParam(required = false) Long carId) {

        if (carId == null){
            List<Service> allServices = serviceManager.getAllServices();
            model.addAttribute("services", allServices);
        }
        else {
            List<Service> carServices = serviceManager.findByCarId(carId);
            model.addAttribute("services", carServices);
            model.addAttribute("selectedCar" , carId);
        }


        List<Car> allCars = carManager.getAllCars();

        model.addAttribute("cars" , allCars);

        return "list-clients";
    }

    @GetMapping("/create")
    public String showCreateServiceForm(Model model) {
        Service service = new Service();
        List<Car> allCars = carManager.getAllCars();
        model.addAttribute("service", service);
        model.addAttribute("cars" , allCars);
        return "create-service";
    }

    @PostMapping("/create")
    public String processCreateCarForm(Model model, @ModelAttribute @Valid Service service, BindingResult result){

        if(result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }
            model.addAttribute("service", service);
            return "create-service";
        }
        else {
            // save to DB
            Service savedService = serviceManager.save(service);
            model.addAttribute("service", savedService);
            return "redirect:/service/list";
        }
    }

    @GetMapping("/delete/{serviceId}")
    public String deleteService(@PathVariable Long serviceId) {
        serviceManager.deleteService(serviceId);
        return "redirect:/service/list";
    }

    @GetMapping("/edit/{serviceId}")
    public String editService( Model model, @PathVariable Long serviceId) {
        Optional<Service> service = serviceManager.getService(serviceId);
        model.addAttribute("service", service);

        List<Car> allCars = carManager.getAllCars();
        model.addAttribute("cars" , allCars);

        return "create-car";
    }

    @ResponseBody
    @RequestMapping(value = "/rest/create", method = RequestMethod.POST)
    public Service createServiceRest(@RequestBody Service service) {
        return service;
    }

}


