import java.util.Date;

/* Base class of article to be published*/
public class Article {
	int unreadNum; //The number of users who subscribe to the topic but didn't read the article,
	String title;
	String content;
	String topicName;
	Date pubTime;
	public Date getPubTime() {
		return pubTime;
	}

	public void setPubTime(Date pubTime) {
		this.pubTime = pubTime;
	}

	public int getUnreadNum() {
		return unreadNum;
	}
	public void setUnreadNum(int unreadNum) {
		this.unreadNum = unreadNum;
	}
	public void decreaseUnreadNum() {
		unreadNum=unreadNum-1;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getTopic() {
		
		return topicName;
	}

	
}
