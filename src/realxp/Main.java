package realxp;

import realxp.service.QuestService;
import realxp.service.UserService;
import realxp.ui.LoginFrame;

public class Main {

	public static void main(String[] args) {
		UserService userService = new UserService();
		QuestService questService = new QuestService();
		new LoginFrame(userService, questService);

	}

}
