package wir6.rssImport.server;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

public class NotificationService extends Thread
{
	public static final String STD_NOTIFICATION_DIR = "Archive/viewernotification";

	private File notificationDir;
	private boolean stoppen = false;

	private List<RssReader> listener;

	public NotificationService()
	{
		super();
		notificationDir = new File(STD_NOTIFICATION_DIR);
		listener = new ArrayList<RssReader>();
	}

	public NotificationService(String notificationDirPath) throws IOException
	{
		super();
		notificationDir = new File(notificationDirPath);
		if (notificationDir.isFile())
			throw new IOException("Der angegebene Pfad ist kein Ordner!");

		listener = new ArrayList<RssReader>();
	}

	public void addRssReader(RssReader rssReader)
	{
		listener.add(rssReader);
	}

	public void removeRssReader(RssReader rssReader)
	{
		if (rssReader != null)
		{
			try
			{
				listener.remove(rssReader);
			} catch (Exception exc)
			{
			}
		}
	}

	public void stopppeUeberwachung()
	{
		stoppen = true;
	}

	public void run()
	{
		System.out.println("Ueberwache Ordner "
				+ notificationDir.getAbsolutePath());
		// newNewsForAll("D:/SkyDrive/Workspace/Workspace_Praktikum_Programmieren/Volltext/Archive/rssfiles/US/en/technology/CNNcomTechnology/y2013/m2/d27/RSS-2026153048.xml");

		while (true)
		{
			for (File f : notificationDir.listFiles())
			{
				// System.out.println(f.getName());
				for (String path : parsePath(f))
				{
					System.out.println("        " + path);
					newNewsForAll(path);
				}

				f.delete();
			}

			if (stoppen)
				break;
			try
			{
				Thread.sleep(1000);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	private List<String> parsePath(File viewerFile)
	{
		List<String> pathList = new ArrayList<String>();
		try
		{
			LineNumberReader lnr = new LineNumberReader(new FileReader(
					viewerFile));
			String line = null;

			while ((line = lnr.readLine()) != null)
				pathList.add(line);

			lnr.close();
		} catch (IOException exc)
		{
			exc.printStackTrace();
		}

		return pathList;
	}

	private void newNewsForAll(String path)
	{

		for (RssReader reader : listener)
			reader.newNews(path);
	}
}