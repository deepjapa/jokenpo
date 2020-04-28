package br.com.jokempo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.jokenpo.domain.Move;
import br.com.jokenpo.domain.PlayType;
import br.com.jokenpo.domain.Player;

@Service
public class GameService {
	
	public Player game(List<Move> moveList) {
		
		for (Move move : moveList) {
			boolean isWin = true;
			for (Move move2 : moveList) {
				if (move.getPlayer().getId() != move2.getPlayer().getId()) {
					if (!rules(move.getPlayType().getId(), move2.getPlayType().getId())) {
						isWin = false;
					}										
				}				
			}
			
			if (isWin) return move.getPlayer();
		}
		
		return null;
	}

	
	private boolean rules(int playType1, int playType2) {
		//PEDRA(0), PAPEL(1), TESOURA(2), LAGARTO(3), SPOK(4)
		switch (playType1) {
		    case 0: 
		    	return playType2 == PlayType.TESOURA.getId() || playType2 == PlayType.LAGARTO.getId();

			case 1: 
				return playType2 == PlayType.PEDRA.getId() || playType2 == PlayType.SPOK.getId();
			
			case 2: 
				return playType2 == PlayType.PAPEL.getId() || playType2 == PlayType.LAGARTO.getId();
			
			case 3: 
				return playType2 == PlayType.PAPEL.getId() || playType2 == PlayType.SPOK.getId();
			
			case 4: 
				return playType2 == PlayType.PEDRA.getId() || playType2 == PlayType.TESOURA.getId();
				
			default: 
				return false;					
		}
		
	}
}
