package proj.view;

import java.util.Calendar;
import java.util.Scanner;

import proj.controller.GameController;
import proj.model.vo.Player;

public class GameMenu {
	private Scanner sc = new Scanner(System.in);
	private GameController gc = new GameController();
	private Player[] players;
	private int round = 1;

	public GameMenu() {
		// 아래와 같이 출력하는 코드 작성
		// === 게임 시작 : xxxx년 xx월 xx일 오전/오후 xx시 xx분 xx초 ===
		Calendar c = Calendar.getInstance();

		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int date = c.get(Calendar.DATE);
		int ap = c.get(Calendar.AM_PM);
		String amPm = null;
		if (ap == 0) {
			amPm = "오전";
		} else {
			amPm = "오후";
		}
		int hour = c.get(Calendar.HOUR);
		int min = c.get(Calendar.MINUTE);
		int sec = c.get(Calendar.SECOND);

		System.out.printf("=== 게임 시작 : %d년 %d월 %d일 %s %d시 %d분 %d초 ===\n", year, month, date, amPm, hour, min, sec);

	}

	public void mainMenu() {
		while (true) {
			System.out.println("본 게임은 사용자와 컴퓨터가 플레이어가 되어 1vs1로 싸우는 게임입니다.\n" + "첫 시작은 각 플레이어의 HP가 100으로 동일하게 시작합니다.\n"
					+ "상대의 HP를 0 이하로 만들면 해당 플레이어의 승으로 게임은 끝납니다.\n"
					+ "플레이어는 한 라운드에 '어택(Attack)' 또는 '큐어(Cure)'를 선택할 수 있으며\n" + "어택 또는 큐어의 크기는 랜덤(0~10)하게 결정됩니다.");

			// 게임을 시작할 것인지 묻는 안내문 출력
			//
			// 게임을 시작하겠다고 한 경우, 사용자의 이름을 입력받은 후 "반갑습니다, xxx님." 출력
			// GameController(이하 gc)의 createPlayer()로 입력받은 이름 전달하고 반환 받은 값을 players에 저장
			// 값을 반환 받은 후 play() 실행
			//
			// 게임을 시작하지 않겠다고 한 경우, "종료합니다." 출력 후 종료
			//
			// 잘못 입력한 경우 "잘못 입력하셨습니다. Y 또는 N만 입력해주세요." 출력 후 제대로 된 대답을 받을 때까지 반복

			System.out.print("게임을 시작하시겠습니까? (Y/N) : ");
			String yn = sc.nextLine().toLowerCase();
			if (yn.equals("y")) {

				System.out.print("닉네임을 입력해주세요 : ");
				String nickName = sc.next();
				players = gc.createPlayer(nickName);
				System.out.println("반갑습니다, " + players[0].getName() + "님.");
				break;
			} else if (yn.equals("n")) {
				System.out.println("N");
				System.out.println("종료합니다.");
				return;
			} else {
				System.out.println("잘못 입력하셨습니다. Y 또는 N만 입력해주세요.");
			}
		}
		play();

	}

	public void play() {
		System.out.println();
		System.out.println("게임을 시작합니다.");

		// 사용자 플레이어와 컴퓨터 플레이어 중 선을 정하기 위해 chooseFirst() 실행 및 boolean 값을 반환 받음
		// 전달 받은 boolean 값을 fight()로 전달

		boolean fisrtReuslt = chooseFirst();

		fight(fisrtReuslt);

		// 최종 결과 발표 출력
		// 사용자 플레이어의 체력이 컴퓨터 플레이어의 체력보다 크면 "xxx님의 승리입니다. 축하합니다!" 출력
		// 사용자 플레이어의 체력이 컴퓨터 플레이어의 체력보다 적으면 "Computer님의 승리입니다. (패배자는 아무 말도 하지 못합니다.)" 출력
		// 사용자 플레이어의 체력과 컴퓨터 플레이어의 체력이 같으면 "접전 끝에 무승부로 판정되었습니다." 출력
		round--;
		System.out.printf("===--- 총 %dround에 걸친 게임 결과 발표 ---===%n",round);
		if(players[0].getHp() > players[1].getHp()) {
			System.out.println(players[0].toString() + "의 승리입니다. 축하합니다!");
		}else if(players[0].getHp() < players[1].getHp()) {
			System.out.println("Computer님의 승리입니다. (패배자는 아무 말도 하지 못합니다.)");
		}else {
			System.out.println("접전 끝에 무승부로 판정되었습니다.");
		}

	}

	public boolean chooseFirst() {
		// 게임의 선을 정하기 위한 가위바위보 게임을 구현한 메소드
		// 사용자 플레이어에게 가위/바위/보 중 하나를 입력 받고 컴퓨터 플레이어의 랜덤한 가위/바위/보 값과 비교
		// 만일 사용자 플레이어가 가위/바위/보 중 하나를 입력하지 않고 잘못 입력하면 잘못 입력했다는 안내와 함께 제대로 입력할 때까지 반복
		// 만일 사용자 플레이어의 가위/바위/보와 컴퓨터 플레이어의 가위/바위/보 게임 결과 비겼으면 비겼다는 안내문과 함께 다시 가위바위보 진행
		// 두 플레이어의 가위바위보 승부가 결정났을 경우, 각 플레이어가 낸 가위/바위/보를 출력
		// 이 때, 사용자 플레이어가 이겼을 경우 true반환, 졌을 경우 false반환
		boolean first = false;

		String crsp = "";

		while (true) {
			int ncrsp = (int) (Math.random() * 3);
			System.out.print("선(先)을 정합니다. 가위/바위/보 중 아무거나 입력해주세요.\n가위/바위/보 : ");
			String rsp = sc.next();
			if (!rsp.equals("가위") && !rsp.equals("바위") && !rsp.equals("보")) {
				System.out.println("잘못 입력하셨습니다. 가위/바위/보 중 하나만 입력해주세요.");
				continue;
			}

			if (ncrsp == 0) {
				crsp = "바위";
			} else if (ncrsp == 1) {
				crsp = "가위";
			} else {
				crsp = "보";
			}

			if (rsp.equals("가위")) {
				if ("가위".equals(crsp)) {
					System.out.println("비겼습니다. 가위바위보를 다시 진행하겠습니다.");
				} else if ("바위".equals(crsp)) {
					System.out.println(players[0].getName() + "님 : " + rsp);
					System.out.println(players[1].getName() + "님 : " + crsp);
					break;
				} else if ("보".equals(crsp)) {
					first = true;
					System.out.println(players[0].getName() + "님 : " + rsp);
					System.out.println(players[1].getName() + "님 : " + crsp);
					break;
				}
			} else if (rsp.equals("바위")) {
				if ("가위".equals(crsp)) {
					first = true;
					System.out.println(players[0].getName() + "님 : " + rsp);
					System.out.println(players[1].getName() + "님 : " + crsp);
					break;
				} else if ("바위".equals(crsp)) {
					System.out.println("비겼습니다. 가위바위보를 다시 진행하겠습니다.");
				} else if ("보".equals(crsp)) {
					System.out.println(players[0].getName() + "님 : " + rsp);
					System.out.println(players[1].getName() + "님 : " + crsp);
					break;
				}
			} else if (rsp.equals("보")) {
				if ("가위".equals(crsp)) {
					System.out.println(players[0].getName() + "님 : " + rsp);
					System.out.println(players[1].getName() + "님 : " + crsp);
					break;
				} else if ("바위".equals(crsp)) {
					first = true;
					System.out.println(players[0].getName() + "님 : " + rsp);
					System.out.println(players[1].getName() + "님 : " + crsp);
					break;
				} else if ("보".equals(crsp)) {
					System.out.println("비겼습니다. 가위바위보를 다시 진행하겠습니다.");
				}
			}
		}
		return first;
	}

	public void fight(boolean userWin) {
		// 전달 받은 boolean 값으로 사용자 플레이어와 컴퓨터 플레이어의 선을 구분 짓는 메소드
		// 매개변수 값이 true이면 사용자 플레이어 먼저, false이면 컴퓨터 플레이어 먼저 진행
		// 한 라운드가 끝날 때마다 해당 라운드의 결과를 출력해주는 print1RoundResult() 실행
		// print1RoundResult()는 사용자 플레이어와 컴퓨터 플레이어가 한 라운드를 진행하고 반환한 값을 인자로 받음
		// 두 플레이어의 싸움은 두 플레이어의 체력 중 한 플레이어의 체력이 0이하가 될 때까지 반복

		while (0 < players[0].getHp() && 0 < players[1].getHp()) {
			if (userWin) {
				String[] user = doUser();
				String[] com = doComp();
				print1RoundResult(user, com);
			} else {
				String[] com = doComp();
				String[] user = doUser();
				print1RoundResult(user, com);
			}
			round++;
		}

	}

	public void print1RoundResult(String[] nRoundUser, String[] nRoundComp) {
		// 한 라운드가 끝날 때마다 해당 라운드의 결과와 각 플레이어의 체력을 출력하는 메소드
		// 한 라운드에서 사용자 플레이어와 컴퓨터 플레이어가 선택한 액션(어택/큐어)과 에너지를 출력하고
		// players 배열에 저장된 사용자 플레이어와 컴퓨터 플레이어의 체력을 가지고 남은 체력을 그림으로 표현
		// ex. 체력 90인 경우 : ●●●●●●●●●○
		// ex. 체력 87인 경우 : ●●●●●●●●◐○
		// ex. 체력 82인 경우 : ●●●●●●●●○○
		// 이 때, 큐어에 의해서 체력 100이 넘을 경우가 발생한다면 이에 대한 처리 안내문도 함께 출력
		System.out.println("--- " + round + "round 결과 ---");
		System.out.println(players[0].getName() + "님)" + nRoundUser[0] + " : " + nRoundUser[1]);
		System.out.println(players[1].getName() + "님)" + nRoundComp[0] + " : " + nRoundComp[1]);
//		System.out.println(players[0].getName() + "님 : " + players[0].getHp());
//		System.out.println(players[1].getName() + "님 : " + players[1].getHp());
		System.out.print(players[0].getName() + "님 : ");
		for (int i = 0; i < players[0].getHp() / 10; i++) {
			System.out.print("●");
		}
		if ((players[0].getHp() % 10) >= 5) {
			System.out.print("◐");
		} else if((players[0].getHp() % 10) < 5&&(players[0].getHp() % 10) > 0) {
			System.out.print("○");
		}
		for (int i = 0; i < (100 - players[0].getHp()) / 10; i++) {
			System.out.print("○");
		}
		System.out.print(" (" + players[0].getHp() + ")");
		System.out.println();
		System.out.print(players[1].getName() + "님 : ");
		for (int i = 0; i < players[1].getHp() / 10; i++) {
			System.out.print("●");
		}
		if ((players[1].getHp() % 10) >= 5) {
			System.out.print("◐");
		} else if((players[1].getHp() % 10) < 5&&(players[1].getHp() % 10) > 0){
			System.out.print("○");
		}
		for (int i = 0; i < (100 - players[1].getHp()) / 10; i++) {
			System.out.print("○");
		}
		System.out.print(" (" + players[1].getHp() + ")");
		
		System.out.println();
		if(nRoundComp[2] != null) {
			System.out.println(nRoundComp[2]);
		}

	}

	public String[] doUser() {
		// 한 라운드에서 사용자 플레이어가 취할 액션(어택/큐어)에 대해 선택하는 메소드
		// 해당 라운드의 결과가 담겨있는 문자열 배열 두 칸짜리 생성
		// 사용자 플레이어가 어택 또는 큐어 중 하나를 입력하게 함
		// 0부터 10까지의 랜덤한 에너지 값을 하나 생성하고
		// 어택을 선택했을 경우, gc의 doUserAttack()에 생성한 에너지를 인자로 넘김
		// 큐어를 선택했을 경우, gc의 doUserCure()에 생성한 에너지를 인자로 넘기고 문자열을 반환 받아 문자열 배열에 저장
		// 이 때, 어택 또는 큐어 중 하나를 입력하지 않으면 잘못 입력했다는 안내문과 함께 제대로 입력 할 때까지 반복
		// 선택을 마치면 "[ n round xxx님 선택 종료 ]" 출력과 함께
		// 해당 라운드에서 사용자 플레이어가 했던 액션과 에너지에 대한 정보를 담은 문자열을 문자열 배열에 저장
		// 현 라운드의 결과를 담은 문자열 배열 반환
		String[] result = new String[3];
		while (true) {
			int eng = (int) (Math.random() * 11);
			System.out.print("어택 또는 큐어 중에 선택해주세요 : ");
			String atkcure = sc.next();
			if ("어택".equals(atkcure)) {
				gc.doUserAttack(eng);
				result[0] = atkcure;
				result[1] = eng + "";
				System.out.println("[ " +round + " round " + players[0].getName() + "님 선택 종료 ]");
				break;
			} else if ("큐어".equals(atkcure)) {
				String cure = gc.doUserCure(eng);
				result[0] = atkcure;
				result[1] = eng + "";
				
				System.out.println(cure);
				System.out.println("[ " +round + " round " + players[0].getName() + "님 선택 종료 ]");
				break;
			} else {
				System.out.println("잘못 입력했습니다. 어택 또는 큐어만 입력해주세요.");
			}
		}
		return result;
	}

	public String[] doComp() {
		// 한 라운드에서 컴퓨터 플레이어가 취할 액션(어택/큐어)을 랜덤으로 정하는 메소드
		// 해당 라운드의 결과가 담겨있는 문자열 배열 두 칸짜리 생성
		// 컴퓨터 플레이어가 이번 라운드에서 취할 액션(어택/큐어)과 에너지(0~10)를 랜덤하게 정함
		// 어택일 경우, gc의 doCompAttack()에 생성한 에너지를 인자로 넘김
		// 큐어일 경우, gc의 doCompCure()에 생성한 에너지를 인자로 넘기고 문자열을 반환 받아 문자열 배열에 저장
		// 모두 정하고 수행을 완료하면 "[ n round Computer님 선택 종료 ]" 출력과 함께
		// 해당 라운드에서 컴퓨터 플레이어가 했던 액션과 에너지에 대한 정보를 담은 문자열을 문자열 배열에 저장
		String[] result = new String[3];
		int eng = (int) (Math.random() * 10);
		int choose = (int) (Math.random() * 2);
		if (0 == choose) {
			gc.doCompAttack(eng);
			result[0] = "어택";
			result[1] = eng + "";
			
			System.out.println("[ " +round + " round " + players[1].getName() + "님 선택 종료 ]");
		} else {
			result[0] = "큐어";
			result[1] = eng + "";
			result[2] = gc.doCompCure(eng);
			System.out.println("[ " +round + " round " + players[1].getName() + "님 선택 종료 ]");
		}
		return result;
	}
}
