package com.inovatrend.carService.web;

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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Controller
@SessionAttributes("client")
@RequestMapping("/client")
public class ClientController {


    private final ClientManager clientManager;
    private final CarManager carManager;
    private final ServiceManager serviceManager;

    public ClientController(ClientManager clientManager, CarManager carManager, ServiceManager serviceManager) {
        this.clientManager = clientManager;
        this.carManager = carManager;
        this.serviceManager = serviceManager;
    }


    @RequestMapping(value = "/info/{clientId}")
    public String clientInfo(Model model, @PathVariable Long clientId) {

        Optional<Client> client = clientManager.getClient(clientId);
        if(client.isPresent()) {
            model.addAttribute("client", client.get());
        }
        return "client-info";
    }

    @RequestMapping(value = "/list")
    public String listAllClient(Model model) {

        List<Client> allClients = clientManager.getAllClients();

        model.addAttribute("clients", allClients);

        return "list-clients";
    }


    @GetMapping("/create")
    public String createClient( Model model) {

        Client client = new Client(null,"", "", "", null, new HashSet<>());
        model.addAttribute("client", client);
        return "create-client";
    }


    @PostMapping("/create")
    public String processCreateClientForm(Model model, @ModelAttribute @Valid Client client, BindingResult result){

        if(result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }
            model.addAttribute("client", client);
            return "create-client";
        }
        else {
            // save to DB
            Client savedClient = clientManager.save(client);
            model.addAttribute("client", savedClient);
            return "redirect:/client/info/" + client.getId();
        }
    }

    @GetMapping("/delete/{clientId}")
    public String deleteClient(@PathVariable Long clientId) {

        clientManager.deleteClient(clientId);
        return "redirect:/client/list";
    }

    @GetMapping("/edit/{clientId}")
    public String editClient( Model model, @PathVariable Long clientId) {
        Optional<Client> client = clientManager.getClient(clientId);

        model.addAttribute("client", client);
        return "create-client";

    }

    @ResponseBody
    @RequestMapping(value = "/rest/create", method = RequestMethod.POST)
    public Client createClientRest(@RequestBody Client client) {
        return client;
    }
}

