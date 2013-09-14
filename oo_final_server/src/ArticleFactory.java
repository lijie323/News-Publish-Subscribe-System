/* Factory method for generating different articles */
public class ArticleFactory {
	public static Article getArticle(String topic, String type){
		if(type.equals("News")){
			return new News(topic);
		}else if(type.equals("Story")){
			return new Story(topic);
		}else if(type.equals("Joke")){
			return new Joke(topic);
		}else if(type.equals("Poem")){
			return new Poem(topic);
		}
		
	return null;
	}

}
