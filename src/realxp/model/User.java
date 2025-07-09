package realxp.model;

import java.util.ArrayList;
import java.util.List;


public class User {
	private int id;
    private String username;
    private String password;
    private MyCharacter character;
    private List<Quest> questList;

    // (1) 기본 생성자
    public User() {
        this.character = new MyCharacter();
        this.questList = new ArrayList<>();
    }

    // (2) username, password만 받는 생성자 (UserDAO용)
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.character = new MyCharacter(); // 기본 캐릭터 생성
        this.questList = new ArrayList<>();
    }

    // (3) username, password, character를 받는 생성자 (Main용)
    public User(String username, String password, MyCharacter character) {
        this.username = username;
        this.password = password;
        this.character = character;
        this.questList = new ArrayList<>();
    }

    // Getter & Setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MyCharacter getCharacter() {
        return character;
    }

    public void setCharacter(MyCharacter character) {
        this.character = character;
    }

    public List<Quest> getQuestList() {
        return questList;
    }

    public void setQuestList(List<Quest> questList) {
        this.questList = questList;
    }

    public void addQuest(Quest quest) {
        questList.add(quest);
    }
    
    public int getId() {
    	return id;
    }
    
    public void setId(int id) {
    	this.id = id;
    }
}