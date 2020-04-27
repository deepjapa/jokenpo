package br.com.jokenpo.domain;

public enum PlayType {
	
	PEDRA(0), PAPEL(1), TESOURA(2), LAGARTO(3), SPOK(4);		
	
	private int id;
	
	PlayType(int id) {
		this.id = id;
	}
			
	public int getId() {
		return this.id;
	}
}
