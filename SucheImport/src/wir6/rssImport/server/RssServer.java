package wir6.rssImport.server;

import de.iisys.mmis.rssServer.RSSThread;

public class RssServer extends Thread
{
	public RssServer()
	{
		super();
	}

	public void run()
	{
		System.out.println("Starte RSS-Server...");
		try
		{
			RSSThread.main(new String[0]);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("RSS-Server gestartet und laeuft!");
	}
}
