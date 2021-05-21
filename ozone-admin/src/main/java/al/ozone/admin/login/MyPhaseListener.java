package al.ozone.admin.login;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class MyPhaseListener implements PhaseListener{    

	private static final long serialVersionUID = 1L;

	public MyPhaseListener(){
    }

    public void afterPhase(PhaseEvent event){
        System.out.println("After Phase->" + event.getPhaseId());    
    }

    public void beforePhase(PhaseEvent event){
        System.out.println("Before Phase->" + event.getPhaseId());
    }

    public PhaseId getPhaseId(){
        return PhaseId.ANY_PHASE;
    }    
}
