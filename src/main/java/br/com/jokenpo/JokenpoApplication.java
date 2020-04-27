package br.com.jokenpo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.jokempo.service.GameService;
import br.com.jokempo.service.MoveService;
import br.com.jokempo.service.PlayerService;

@SpringBootApplication
public class JokenpoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JokenpoApplication.class, args);
	}
    
	@Bean
	public PlayerService getPlayerService() {
		return new PlayerService();
	}
	
	@Bean
	public MoveService getMoveService() {
		return new MoveService();
	}
	
	@Bean
	public GameService getGameService() {
		return new GameService();
	}
	
}
