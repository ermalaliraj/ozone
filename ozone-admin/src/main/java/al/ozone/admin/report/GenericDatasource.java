package al.ozone.admin.report;

import java.util.Collection;
import java.util.Iterator;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class GenericDatasource implements JRDataSource{	
	protected Collection<?> data;
	protected Iterator<?> iterator;
	protected Object currentBean;

	
	public GenericDatasource(Collection<?> data) {
		if(data != null){
			this.data = data;
			this.iterator = data.iterator();
		}
	}

	public boolean next() {
		boolean hasNext = false;
		
		if (this.iterator != null) {
			hasNext = this.iterator.hasNext();
			
			if (hasNext) {
				this.currentBean = this.iterator.next();
			}
		}
		
		return hasNext;
	}
	
	@Override
	public Object getFieldValue(JRField jrField) throws JRException {
		return this.currentBean;
	}
}
