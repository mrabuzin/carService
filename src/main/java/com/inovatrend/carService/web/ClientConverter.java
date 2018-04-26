package com.inovatrend.carService.web;


import com.inovatrend.carService.domain.Client;
import com.inovatrend.carService.service.ClientManager;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class ClientConverter implements Converter<String, Client> {

    public final ClientManager clientManager;

    public ClientConverter(ClientManager clientManager) {
        this.clientManager = clientManager;
    }


    @Override
    public Client convert(String clientId) {
        Long id = Long.parseLong(clientId);

        Optional<Client> clientOptional =  clientManager.getClient(id);
        return clientOptional.orElse(null);
    }


}
