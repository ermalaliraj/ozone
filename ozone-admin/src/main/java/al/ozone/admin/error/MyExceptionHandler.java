package al.ozone.admin.error;

import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MyExceptionHandler extends ExceptionHandlerWrapper {
	 
	  private static Log log = LogFactory.getLog(MyExceptionHandler.class);
	  private ExceptionHandler wrapped;
	 
	  public MyExceptionHandler(ExceptionHandler wrapped) {
	    this.wrapped = wrapped;
	  }
	 
	  @Override
	  public ExceptionHandler getWrapped() {
	    return wrapped;
	  }
	 
	  @Override
	  public void handle() throws FacesException {
	    Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
	    while (i.hasNext()) {
	      ExceptionQueuedEvent event = (ExceptionQueuedEvent) i.next();
	      ExceptionQueuedEventContext context = (ExceptionQueuedEventContext)event.getSource();
	 
	      Throwable t = context.getException();
	      try{
	        log.error("Serious error happened!", t);
	        //redirect to error view etc.... 
	      }finally{
	        i.remove();
	      }
	    }
	    //let the parent handle the rest
	    getWrapped().handle();
	  }
	}