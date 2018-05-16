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
        if(service.isPresent()) {
            model.addAttribute("service", service.get());
        }
        return "service-info";
    }

    @RequestMapping(value = "/list")
    public String listAllServices(Model model, @RequestParam(required = false) Long carId) {

        if (carId == null){
            Page<Service> allServices = serviceManager.getAllServices(PageRequest.of(0,10000000, new Sort(Sort.Direction.DESC, "serviceTime")));

            model.addAttribute("services", allServices);
        }
        else {
            List<Service> carServices = serviceManager.findByCarId(carId);
            model.addAttribute("services", carServices);
            model.addAttribute("selectedCar" , carId);
        }


        List<Car> allCars = carManager.getAllCars();

        model.addAttribute("cars" , allCars);

        return "list-services";
    }

    @GetMapping("/create/{carId}")
    public String showCreateServiceForm(Model model, @PathVariable Long carId) {
        Service service = new Service();
        Optional<Car> car = carManager.getCar(carId);
        model.addAttribute("service", service);
        if(car.isPresent()) {
            model.addAttribute("car", car.get());
            service.setCar(car.get());
        }
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
            return "redirect:/car/info/" + service.getCar().getId();
        }
    }

    @GetMapping("/delete/{serviceId}")
    public String deleteService(@PathVariable Long serviceId, Service service) {
        serviceManager.deleteService(serviceId);
        return "redirect:/car/info/" + service.getCar().getId();
    }

    @GetMapping("/edit/{serviceId}")
    public String editService( Model model, @PathVariable Long serviceId) {
        Optional<Service> service = serviceManager.getService(serviceId);
        Car car = service.get().getCar();
        model.addAttribute("service", service);
        model.addAttribute("car", car);

        List<Car> allCars = carManager.getAllCars();
        model.addAttribute("cars" , allCars);

        return "create-service";
    }

    @ResponseBody
    @RequestMapping(value = "/rest/create", method = RequestMethod.POST)
    public Service createServiceRest(@RequestBody Service service) {
        return service;
    }

}


