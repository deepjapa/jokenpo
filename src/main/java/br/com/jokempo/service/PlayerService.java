package br.com.jokempo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.jokenpo.domain.Player;
import br.com.jokenpo.exception.BusinessException;

@Service
public class PlayerService {
	
	public Player create(List<Player> playerList, Player player) {
		playerList.stream().forEach(p -> { if (p.getCpf().equals(player.getCpf()) && p.getId() != player.getId())
			throw new BusinessException("Já existe um jogador com o CPF cadastrado!");}			
				);
			
    	player.setId(playerList.size() + 1);
		playerList.add(player);

		return player;
	}
	
	public void remove(List<Player> playerList, int id) {
		playerList.remove(id - 1);
	}
	
	public Player findId(List<Player> playerList, int id) {
		for (Player player : playerList) {			
            if (player.getId() == id) {
            	return player;
            }            	            
        }
        return null;    	                                        
	} 

}
