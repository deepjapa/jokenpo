package br.com.jokenpo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.jokempo.service.GameService;
import br.com.jokempo.service.MoveService;
import br.com.jokempo.service.PlayerService;
import br.com.jokenpo.domain.Move;
import br.com.jokenpo.domain.Player;
import br.com.jokenpo.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Jokenpo")
public class JokenpoController {
	@Autowired
	private PlayerService playerService;

	@Autowired
	private MoveService moveService;
	
	@Autowired
	private GameService gameService;
	
	public List<Player> playersList;
	public List<Move> moveList;
	
	
	@PostMapping(value = "/player")
	@ResponseStatus(HttpStatus.CREATED)	
	@ApiOperation(value = "Criar Jogador")
	public Player createPlayer(@Valid @RequestBody Player player ) {
		if (playersList == null) {
			playersList = new ArrayList<>();
		}
		        
		return playerService.create(playersList, player);
    }	
	
	@ApiOperation(value = "Remover Jogador")
	@DeleteMapping(value = "/player/{playerId}")
    public ResponseEntity<Void> removePlayer(@PathVariable int playerId) {
        try {
        	playerService.remove(playersList, playerId);
        	return ResponseEntity.noContent().build();
        } catch(NullPointerException npe) {
        	return ResponseEntity.notFound().build();
        } catch(IndexOutOfBoundsException ioobe) {
        	return ResponseEntity.notFound().build();
        }
    }	
	
	@ApiOperation(value = "Listar Jogador")
	@GetMapping(value = "/player")
	public List<Player> findPlayerAll() {
		return playersList;
		
	}
	
	@ApiOperation(value = "Buscar Jogador por Id")
	@GetMapping(value = "/player/{playerId}")
	public ResponseEntity<Player> findPlayerById(@PathVariable int playerId){
		Player player = playerService.findId(playersList, playerId);
		
		if (player == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(player);
		}
		
	}

	@ApiOperation(value = "Criar a Jogada do Jogador")
	@PostMapping(value = "/move")
	@ResponseStatus(HttpStatus.CREATED)
    public Move createMove(@RequestBody Move move) {		
		if (moveList == null) {
			moveList = new ArrayList<>();
		}
		    
        if (playerService.findId(playersList, move.getPlayer().getId()) == null) {
        	throw new BusinessException("Jogador não Cadastrado!");
        }
        
       	return moveService.create(moveList, move);
    }	
	
	@ApiOperation(value = "Remover Jogada")
	@DeleteMapping(value = "/move/{moveId}")
    public ResponseEntity<Void> removeMove(@PathVariable int moveId) {		        
        try {
        	moveService.remove(moveList, moveId);
        	return ResponseEntity.noContent().build();
        } catch(NullPointerException npe) {
        	return ResponseEntity.notFound().build();
        } catch(IndexOutOfBoundsException ioobe) {
        	return ResponseEntity.notFound().build();
        }
    }	
	
	@ApiOperation(value = "Listar Jogada")
	@GetMapping(value = "/move")
	public List<Move> findMoveAll() {
		moveList.stream().forEach(list -> { 
			Player player = playerService.findId(playersList, list.getPlayer().getId());
			list.setPlayer(player);			
		});
		
		return moveList;
	}
	
	@ApiOperation(value = "Buscar Jogada por Id")
	@GetMapping(value = "/move/{moveId}")
	public ResponseEntity<Move> findMoveById(@PathVariable int moveId){
		Move move = moveService.findId(moveList, moveId);
		
		if (move == null) {
			return ResponseEntity.notFound().build();
		} else {
			Player player = playerService.findId(playersList, move.getPlayer().getId());
			move.setPlayer(player);
			return ResponseEntity.ok(move);
		}
		
	}	
	
	@ApiOperation(value = "Jogar")
	@PutMapping(value = "/game")
	public ResponseEntity<Map<String, Object>> playGame() {
				
		if (playersList.size() < 2) {
			throw new BusinessException("Cadastrar no Mínimo dois Jogadores!");
		}
				
		if (moveList.size() < playersList.size()) {
			throw new BusinessException("Exsitem Jogador(es) sem Jogadas!");
		}
		
		Player player = gameService.game(moveList);
		
		if (player != null) {
			Map<String, Object> resposta = new HashMap<>();
			resposta.put("Ganhador da Partida:", player);
			return new ResponseEntity<>(resposta, HttpStatus.OK);		
		} else {
			Map<String, Object> resposta = new HashMap<>();
			resposta.put("Resultado da Partida:", "Empate");
			return new ResponseEntity<>(resposta, HttpStatus.OK);					
		}
	}
		
}
