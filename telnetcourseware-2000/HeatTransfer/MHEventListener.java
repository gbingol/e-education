//Base Listener Object for all Events
///
public interface MHEventListener {
}
//
//********************************An Inteface for Remote ShutDown of MH Server Station********************
//
interface LogOffListener extends MHEventListener{
    public void ShutDown();
}