package wir6.suche.test;

import javax.ejb.Remote;

@Remote
public interface TestBeanRemote {
    
    public String getMessage();
}
