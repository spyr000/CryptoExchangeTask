package com.company.cryptoexchangetask;
import io.jsonwebtoken.io.Encoders;
import org.jboss.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.encrypt.Encryptors;

@SpringBootApplication
public class CryptoExchangeTaskApplication {

	public static void main(String[] args) {
//		Logger.getLogger(CryptoExchangeTaskApplication.class.getName()).info("USER: " + Encryptors.stronger("USER","2A472D4B6150645367566B59703273357638792F423F4528482B4D6251655468"));
		SpringApplication.run(CryptoExchangeTaskApplication.class, args);
	}

}
