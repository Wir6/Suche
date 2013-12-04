import wir6.rssImport.importArticle.ArticleParser;
import wir6.rssImport.server.NotificationService;
import wir6.rssImport.server.RssServer;

public class Main
{

	public static void main(String[] args) throws Exception
	{
		// Server starten
		new RssServer().start();
		// Notification
		NotificationService notify = new NotificationService();
		ArticleParser aP = new ArticleParser();
		notify.addRssReader(aP);
		notify.start();
	}
}
