package proj.controller;

import proj.model.vo.Player;

public class GameController {
	private Player[] players;
	
	public Player[] createPlayer(String userName) {
		// players에 사용자 플레이어에 대한 객체와 컴퓨터 플레이어에 대한 객체 저장
		// 이 때, 사용자 플레이어 객체는 전달받은 매개변수 값을 이용하여 생성하고
		// 컴퓨터 플레이어 객체는 인자에 "Computer" 값을 넣어 생성
		// 완성된 players 반환
		players = new Player[2];
		
		players[0] = new Player(userName);
		players[1] = new Player("Computer");
		
		return players;
	}
	
	public void doUserAttack(int n) {
		// 컴퓨터 플레이어의 체력을 전달받은 매개변수 값만큼 감소 시킴
		players[1].setHp(players[1].getHp()-n);
	}

	public String doUserCure(int n) {
		// 사용자 플레이어의 체력을 전달받은 매개변수의 값만큼 증가 시킴
		// 이 때, 사용자 플레이어의 체력이 100 이상이 되면 "HP의 최대 값인 100을 넘었기 때문에 100으로 다시 설정합니다."라는 안내 문구 반환
		if(100 < players[0].getHp()+n){
			players[0].setHp(100);
			return "HP의 최대 값인 100을 넘었기 때문에 100으로 다시 설정합니다.";
		}else{
			players[0].setHp(players[0].getHp()+n);
			return "";
		}
	}

	public void doCompAttack(int n) {
		// 사용자 플레이어의 체력을 전달받은 매개변수 값만큼 감소 시킴
		players[0].setHp(players[0].getHp()-n);
	}

	public String doCompCure(int n) {
		// 컴퓨터 플레이어의 체력을 전달받은 매개변수의 값만큼 증가 시킴
		// 이 때, 컴퓨터 플레이어의 체력이 100 이상이 되면 "HP의 최대 값인 100을 넘었기 때문에 100으로 다시 설정합니다."라는 안내 문구 반환
		if(100 < players[1].getHp()+n){
			players[1].setHp(100);
			return "HP의 최대 값인 100을 넘었기 때문에 100으로 다시 설정합니다.";
		}else{
			players[1].setHp(players[1].getHp()+n);
			return null;
		}
	}
}
