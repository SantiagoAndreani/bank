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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DolarService {

    private UserRepository userRepository;
    private final String URI_HEROKU = "https://api-dolar-argentina.herokuapp.com/api/dolaroficial";
    private final Double PAIS = 1.3;
    private NewUserService userService;

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

    public boolean compraInsuficiente(JsonDolar dolarForm, Double cajaPeso) {

        RestTemplate restTemplate = new RestTemplate();
        JsonDolar jsonDolar = restTemplate.getForObject(URI_HEROKU, JsonDolar.class);
        double ventaPais = Double.parseDouble(jsonDolar.getVenta()) * PAIS;
        jsonDolar.setAVender(dolarForm.getAVender());

        return cajaPeso < (jsonDolar.getAVender()*ventaPais);
    }

    public void compraDolar(JsonDolar dolarForm, Authentication authentication) {

        RestTemplate restTemplate = new RestTemplate();
        JsonDolar jsonDolar = restTemplate.getForObject(URI_HEROKU, JsonDolar.class);
        Double ventaPais = Double.parseDouble(jsonDolar.getVenta()) * PAIS;

        double aVender = dolarForm.getAVender();
        jsonDolar.setAVender(aVender);

        UserEntity userEntity = getUserEntity(authentication);
        Set<UserAccountEntity> accounts = userEntity.getAccounts();
        Optional<UserAccountEntity> cajaPeso = accounts.stream()
                .filter(userAccountEntity ->
                        userAccountEntity.getType().equals(AccountType.CAJA_AHORRO_PESOS)).findFirst();
        Optional<UserAccountEntity> cajaDolar = accounts.stream()
                .filter(userAccountEntity ->
                        userAccountEntity.getType().equals(AccountType.CAJA_AHORRO_DOLARES)).findFirst();
        Optional<UserAccountEntity> btc = accounts.stream()
                .filter(userAccountEntity ->
                        userAccountEntity.getType().equals(AccountType.BILLETERA_BITCOIN)).findFirst();
        cajaPeso.orElseThrow(()-> new UsernameNotFoundException("NO ENCONTRADO"));
        cajaDolar.orElseThrow(()-> new UsernameNotFoundException("NO ENCONTRADO"));
        btc.orElseThrow(()-> new UsernameNotFoundException("NO ENCONTRADO"));

        UserAccountEntity cajaPeso2 = cajaPeso.get();
        UserAccountEntity cajaDolar2 = cajaDolar.get();
        UserAccountEntity btc2 = btc.get();

        cajaPeso2.setAmount(cajaPeso2.getAmount() - ventaPais);
        cajaDolar2.setAmount(cajaDolar2.getAmount() + aVender);
        btc2.setAmount(btc2.getAmount());

        Set<UserAccountEntity> aGuardar = new HashSet<>();
        aGuardar.add(cajaPeso2);
        aGuardar.add(cajaDolar2);
        aGuardar.add(btc2);

        userEntity.setAccounts(aGuardar);

        userRepository.save(userEntity);
    }

    public boolean ventaInsuficiente(JsonDolar dolarForm, Double cajaDolar) {
        return dolarForm.getAVender() > cajaDolar;
    }

    public void ventaDolar(JsonDolar dolarForm, Authentication authentication) {
        RestTemplate restTemplate = new RestTemplate();
        JsonDolar jsonDolar = restTemplate.getForObject(URI_HEROKU, JsonDolar.class);
        Double compra = Double.parseDouble(jsonDolar.getCompra());

        double aComprar = dolarForm.getAComprar();
        jsonDolar.setAComprar(aComprar);

        UserEntity userEntity = getUserEntity(authentication);
        Set<UserAccountEntity> accounts = userEntity.getAccounts();
        Optional<UserAccountEntity> cajaPeso = accounts.stream()
                .filter(userAccountEntity ->
                        userAccountEntity.getType().equals(AccountType.CAJA_AHORRO_PESOS)).findFirst();
        Optional<UserAccountEntity> cajaDolar = accounts.stream()
                .filter(userAccountEntity ->
                        userAccountEntity.getType().equals(AccountType.CAJA_AHORRO_DOLARES)).findFirst();
        Optional<UserAccountEntity> btc = accounts.stream()
                .filter(userAccountEntity ->
                        userAccountEntity.getType().equals(AccountType.BILLETERA_BITCOIN)).findFirst();
        cajaPeso.orElseThrow(()-> new UsernameNotFoundException("NO ENCONTRADO"));
        cajaDolar.orElseThrow(()-> new UsernameNotFoundException("NO ENCONTRADO"));
        btc.orElseThrow(()-> new UsernameNotFoundException("NO ENCONTRADO"));

        UserAccountEntity cajaPeso2 = cajaPeso.get();
        UserAccountEntity cajaDolar2 = cajaDolar.get();
        UserAccountEntity btc2 = btc.get();

        cajaDolar2.setAmount(cajaDolar2.getAmount() - aComprar);
        cajaPeso2.setAmount(cajaPeso2.getAmount() + (aComprar * compra));
        btc2.setAmount(btc2.getAmount());

        Set<UserAccountEntity> aGuardar = new HashSet<>();
        aGuardar.add(cajaPeso2);
        aGuardar.add(cajaDolar2);
        aGuardar.add(btc2);

        userEntity.setAccounts(aGuardar);

        userRepository.save(userEntity);
    }

    public UserEntity getUserEntity(Authentication authentication) {
        String dni = authentication.getName();
        Optional<UserEntity> optionalUserEntity = userRepository.findByDni(dni);
        optionalUserEntity.orElseThrow(()-> new UsernameNotFoundException(dni + " NO ENCONTRADO"));
        return optionalUserEntity.get();
    }



}
