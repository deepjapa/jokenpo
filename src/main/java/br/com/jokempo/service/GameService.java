package br.com.jokempo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.jokenpo.domain.Move;
import br.com.jokenpo.domain.Player;
import br.com.jokenpo.exception.BusinessException;

@Service
public class GameService {
	
	public Player game(List<Move> moveList) {
		
		if (moveList.size() < 2) {
			throw new BusinessException("Entrar com no mínimo duas jogadas!");
		}

		for (Move move : moveList) {
			boolean bValid = true;
			for (Move move2 : moveList) {
				if (move.getPlayer().getId() != move2.getPlayer().getId()) {
					if (!rules(move.getPlayType().getId(), move2.getPlayType().getId())) {
						bValid = false;
					}										
				}				
			}
			
			if (bValid) return move.getPlayer();
		}
		
		return null;
	}

	
	private boolean rules(int playType1, int playType2) {
		//PEDRA(0), PAPEL(1), TESOURA(2), LAGARTO(3), SPOK(4)
		switch (playType1) {
		    case 0: return ((playType2 == 2) || (playType2 == 3)) ? true : false;

			case 1: return ((playType2 == 0) || (playType2 == 4)) ? true : false;
			
			case 2: return ((playType2 == 1) || (playType2 == 3)) ? true : false;
			
			case 3: return ((playType2 == 1) || (playType2 == 4)) ? true : false;
			
			case 4: return ((playType2 == 0) || (playType2 == 2)) ? true : false;
				
			default: return false;					
		}
		
	}
}
