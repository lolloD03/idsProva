package com.filiera;

import com.filiera.model.OsmMap.Indirizzo;
import com.filiera.model.administration.Curatore;
import com.filiera.model.products.Prodotto;
import com.filiera.model.sellers.Produttore;
import com.filiera.model.sellers.Venditore;
import com.filiera.model.users.Acquirente;
import com.filiera.model.users.RuoloUser;
import com.filiera.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;


@SpringBootApplication
public class FilieraApplication implements CommandLineRunner {
    public static void main(String[] args) {

    SpringApplication.run(FilieraApplication.class, args);


    }

    @Autowired
    InMemoryAcquirenteRepository acquirenteRepository;

    @Autowired
    InMemoryOrdineRepository ordineRepository;

    @Autowired
    InMemoryProductRepository prodottiRepository;

    @Autowired
    InMemoryVenditoreRepository venditoreRepository;

    @Autowired
    InMemoryUserRepository userRepository;

    @Autowired
    InMemoryCarrelloRepository carrelloRepository;

    @Autowired
    InMemoryCuratoreRepository curatoreRepository;


    @Override
    public void run(String... args) throws Exception {



        Indirizzo indirizzo1 = new Indirizzo("Milano" , "ViaMilano" , "1");
        Indirizzo indirizzo2 = new Indirizzo("Torino" , "ViaTorino" , "1");



        Venditore venditore1 = Produttore.builder()
                .name("produttore1")
                .indirizzo(indirizzo1)
                .process("boh")
                .partitaIva("tuobabbo")
                .email("venditore1@gmail.com")
                .password("password")
                .ruolo(RuoloUser.PRODUTTORE)
                .build();

        Prodotto pomodoro = Prodotto.builder()
                .name("Pomodoro")
                .description("Pomodoro Ciliegino")
                .price(1.99)
                .availableQuantity(5)
                .certification("DOP")
                .expirationDate(LocalDate.of(2025, 10, 5)) // Se presente
                .seller(venditore1)
                .build();

        Prodotto patata = Prodotto.builder()
                .name("Patata buona molto")
                .description("Cultivated")
                .price(2.99)
                .availableQuantity(3)
                .certification("DOP")
                .expirationDate(LocalDate.of(2025, 10, 5)) // Se presente
                .seller(venditore1)
                .build();



        Venditore venditore2 = Produttore.builder()
                .name("produttore2")
                .indirizzo(indirizzo2)
                .process("boh2")
                .partitaIva("tuobabbo2")
                .email("venditore2@gmail.com")
                .password("password")
                .ruolo(RuoloUser.PRODUTTORE)
                .build();

        Prodotto passataDiPomodoro = Prodotto.builder()
                .name("Passata di pomodoro")
                .description("Trasformated")
                .price(4.99)
                .availableQuantity(1)
                .certification("DOP")
                .expirationDate(LocalDate.of(2025, 10, 5)) // Se presente
                .seller(venditore2)
                .build();


        Curatore curatore = Curatore.builder()
                .name("Curatore")
                .ruolo(RuoloUser.CURATORE)
                .email("curatore@gmail.com")
                .password("password")
                .build();

        Acquirente buyer1 = Acquirente.builder()
                .name("Acquirente1")
                .ruolo(RuoloUser.ACQUIRENTE)
                .email("acquirente1@gmail.com")
                .password("password")
                .build();

        Acquirente buyer2 = Acquirente.builder()
                .name("Acquirente2")
                .ruolo(RuoloUser.ACQUIRENTE)
                .email("acquirente2@gmail.com")
                .password("password")
                .build();


        userRepository.save(venditore1);
        userRepository.save(venditore2);
        prodottiRepository.save(pomodoro);
        prodottiRepository.save(patata);
        prodottiRepository.save(passataDiPomodoro);

        venditore1.addProdotto(pomodoro);
        venditore1.addProdotto(patata);
        venditore2.addProdotto(passataDiPomodoro);

        System.out.println(venditore1.getId());

        userRepository.save(curatore);
        userRepository.save(buyer1);
        userRepository.save(buyer2);


    }
}
