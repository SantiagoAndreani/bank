package com.bank.services;

import com.bank.entities.UserAccountEntity;
import com.bank.entities.UserEntity;
import com.bank.models.AccountType;
import com.bank.models.api.dolar.JsonDolar;
import com.bank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Optional;
import java.util.Set;

@Service
public class DolarService {

    private UserRepository userRepository;
    private final String URI_HEROKU = "https://api-dolar-argentina.herokuapp.com/api/dolaroficial";
    private final Double PAIS = 1.3;

    @Autowired
    public DolarService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Double getAmount(Authentication authentication, AccountType accountType) {

        Optional<UserEntity> userEntity = userRepository.findByDni(authentication.getName());
        userEntity.orElseThrow(()-> new UsernameNotFoundException(" NO ENCONTRADO"));

        Set<UserAccountEntity> accounts = userEntity.get().getAccounts();

        Optional<UserAccountEntity> optionalUserAccountEntity = accounts.stream()
                .filter(accountEntity -> accountEntity.getType().equals(accountType))
                .findFirst();
        optionalUserAccountEntity.orElseThrow(()-> new UsernameNotFoundException("NO ENCONTRADO"));

        return optionalUserAccountEntity.get().getAmount();
    }

    public boolean insufficientAmount(JsonDolar dolarForm, Double cajaPeso) {

        RestTemplate restTemplate = new RestTemplate();
        JsonDolar jsonDolar = restTemplate.getForObject(URI_HEROKU, JsonDolar.class);
        double compraPais = Double.parseDouble(jsonDolar.getCompra()) * PAIS;
        jsonDolar.setAComprar(dolarForm.getAComprar());

        return cajaPeso < (jsonDolar.getAComprar()*compraPais);
    }

    public void compraDolar(JsonDolar dolarForm) {

        RestTemplate restTemplate = new RestTemplate();
        JsonDolar jsonDolar = restTemplate.getForObject(URI_HEROKU, JsonDolar.class);
        double compraPais = Double.parseDouble(jsonDolar.getCompra()) * PAIS;

        jsonDolar.setAComprar(dolarForm.getAComprar());


    }

}
