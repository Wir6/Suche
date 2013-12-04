package wir6.suche.test;

import javax.ejb.Stateless;

/**
 * Session Bean implementation class TestBean
 */
@Stateless(mappedName = "ejb/test")
public class TestBean implements TestBeanRemote
{

	/**
	 * Default constructor.
	 */
	public TestBean()
	{
	}

	public String getMessage()
	{
		return "Juhu";
	}

}
