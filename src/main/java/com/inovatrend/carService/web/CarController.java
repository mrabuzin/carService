package com.inovatrend.carService.web;


import com.inovatrend.carService.domain.Car;
import com.inovatrend.carService.domain.CarType;
import com.inovatrend.carService.domain.Client;
import com.inovatrend.carService.service.CarManager;
import com.inovatrend.carService.service.ClientManager;
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

    public CarController(CarManager carManager, ClientManager clientManager) {
        this.carManager = carManager;
        this.clientManager = clientManager;
    }

    @RequestMapping(value = "/info/{carId}")
    public String carInfo(Model model, @PathVariable Long carId) {

        Optional<Car> car = carManager.getCar(carId);
        model.addAttribute("car", car);

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
            return "redirect:/car/list";
        }
    }

    @GetMapping("/delete/{carId}")
    public String deleteCar(@PathVariable Long carId) {
        carManager.deleteCar(carId);
        return "redirect:/car/list";
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



