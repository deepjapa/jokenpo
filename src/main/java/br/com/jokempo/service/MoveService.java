package br.com.jokempo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.jokenpo.domain.Move;
import br.com.jokenpo.exception.BusinessException;

@Service
public class MoveService {
	
	public Move create(List<Move> moveList, Move move) {
		moveList.stream().forEach(player -> { if (player.getPlayer().getId() == move.getPlayer().getId())
			throw new BusinessException("Jogador já possui jogada!");}			
				);

		move.setId(moveList.size() + 1);
		moveList.add(move);
		return move;
	}
	
	public void remove(List<Move> moveList, int moveId)  {
		moveList.remove(moveId - 1);	
	}
	
	public Move findId(List<Move> MoveList, int id) {
    	for (Move move : MoveList) {
            if (move.getId() == id) {
            	return move;
            }            	            
        }
        return null;    	
	}
	
}
