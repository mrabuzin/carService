package com.inovatrend.carService.web;


import com.inovatrend.carService.domain.Car;
import com.inovatrend.carService.domain.CarType;
import com.inovatrend.carService.domain.Client;
import com.inovatrend.carService.service.CarManager;
import com.inovatrend.carService.service.ClientManager;
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
@SessionAttributes("car")
@RequestMapping("/car")
public class CarController {

    private final CarManager carManager;
    private final ClientManager clientManager;
    private final ServiceManager serviceManager;

    public CarController(CarManager carManager, ClientManager clientManager, ServiceManager serviceManager) {
        this.carManager = carManager;
        this.clientManager = clientManager;
        this.serviceManager = serviceManager;
    }

    @RequestMapping(value = "/info/{carId}")
    public String carInfo(Model model, @PathVariable Long carId) {

        Optional<Car> car = carManager.getCar(carId);
        if(car.isPresent()) {
            model.addAttribute("car", car.get());
        }
        return "car-info";
    }

    @RequestMapping(value = "/list")
    public String listAllCars(Model model, @RequestParam(required = false) Long clientId) {

        if (clientId == null){
            List<Car> allCars = carManager.getAllCars();
            model.addAttribute("cars", allCars);
        }
        else {
            List<Car> clientCars = carManager.findByClientId(clientId);
            model.addAttribute("cars", clientCars);
            model.addAttribute("selectedClient" , clientId);
        }


        List<Client> allClients = clientManager.getAllClients();

        model.addAttribute("clients" , allClients);

        return "list-cars";
    }

    @GetMapping("/create")
    public String showCreateCarForm(Model model) {
        Car car = new Car();
        List<Client> allClients = clientManager.getAllClients();
        model.addAttribute("car", car);
        model.addAttribute("clients" , allClients);
        model.addAttribute("carTypes", CarType.values());
        return "create-car";
    }

    @PostMapping("/create")
    public String processCreateCarForm(Model model, @ModelAttribute @Valid Car car, BindingResult result){

        if(result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }
            model.addAttribute("car", car);
            return "create-car";
        }
        else {
            // save to DB
            Car savedCar = carManager.save(car);
            model.addAttribute("car", savedCar);
            return "redirect:/client/info/" + car.getClient().getId();
        }
    }

    @GetMapping("/delete/{carId}")
    public String deleteCar(@PathVariable Long carId, Car car) {
        serviceManager.deleteByCarId(carId);
        carManager.deleteCar(carId);
        return "redirect:/client/info/" + car.getClient().getId();
    }

    @GetMapping("/edit/{carId}")
    public String editCar( Model model, @PathVariable Long carId) {
        Optional<Car> car = carManager.getCar(carId);
        model.addAttribute("car", car);

        List<Client> allClients = clientManager.getAllClients();
        model.addAttribute("clients" , allClients);

        model.addAttribute("carTypes", CarType.values());
        return "create-car";
    }

    @ResponseBody
    @RequestMapping(value = "/rest/create", method = RequestMethod.POST)
    public Car createCarRest(@RequestBody Car car) {
        return car;
    }

}



