package realxp.model;

public class Quest {
	private static int nextId = 1;
	
	private int id;
	private String title;
	private String category;
	private boolean completed;
	private int userId;
	
	public Quest(String title, String category) {
		this.id = nextId++;
		this.title = title;
		this.category = category;
		this.completed = false;
	}
	
	public void complete() {
		this.completed = true;
	}
	
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
	public boolean isCompleted() {
		return completed;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getCategory() {
		return category;
	}
	
	public String getStatus() {
		return completed ? "✅ 완료" : "❌ 미완료";
	}
	
	public String toString() {
	    return "[" + (completed ? "✅ 완료" : "❌ 미완료") + "] "
	         + title + " (" + category + ")";
	}
	
	public String toDisplayString() {
		return title + " (" + category + ") - " + getStatus();
	}
	
	public Quest() {
		
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}

}
