package al.ozone.admin.backing;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import al.ozone.admin.util.JSFUtils;
import al.ozone.bl.dao.CodeGeneratorDAO;
import al.ozone.bl.manager.DiscountCardManager;
import al.ozone.bl.model.DiscountCard;
import al.ozone.bl.model.DiscountCardGroup;
import al.ozone.bl.utils.ZUtils;

/**
 * @author Florjan Gogaj
 * 
 */

@ManagedBean(name="discountCardController")
@ViewScoped
public class DiscountCardController implements Serializable{

	private static final long serialVersionUID = -7665910802909913939L;
	protected static final transient Log logger = LogFactory.getLog(DiscountCardController.class);

	private HtmlInputText fPercDiscount;
	private Date fUsedDate;

	//Binding Variables - New Partner form 
	private HtmlInputText newPercDiscount;
	private HtmlInputText newCardQuantity;
	private List<DiscountCardGroup> discountCardGroup;
	private Date newUsedDate;
	
	private List<DiscountCard> discountCardList;
	private SelectItem[] usedOptions;
	private DiscountCard discountCardSelected;
	private DiscountCardGroup discountCardGroupSelected;
	private boolean editMode;
	
	@ManagedProperty(value="#{discountCardManager}") 
	private DiscountCardManager discountCardManager;
	
	@ManagedProperty(value="#{codeGeneratorDAO}") 
	private CodeGeneratorDAO codeGeneratorDAO;
	
	
	protected String getManagedObjectName(){
		String className=this.getClass().getSimpleName();
		int length=className.length();
		return className.substring(0,length-10);
	}

	@PostConstruct
	public void init(){
		extractGroups();
		usedOptions = new SelectItem[3];  
		usedOptions[0] = new SelectItem("", "ALL");
		usedOptions[1] = new SelectItem("true", JSFUtils.getMessageFromBundle("yes"));
		usedOptions[2] = new SelectItem("false", JSFUtils.getMessageFromBundle("no"));
	}
	
	public void extractGroups(){
		discountCardGroup=discountCardManager.getCardGroups();
	}

	public void addDiscountCard(){    
		DiscountCard dc = new DiscountCard();
		int cardQuantity = JSFUtils.getIntegerFromUIInput(newCardQuantity);
		int percentage = JSFUtils.getIntegerFromUIInput(newPercDiscount);
		dc.setPercDiscount(percentage);
		
		try {
			for (int i=0; i<cardQuantity; i++){
				dc.setId(codeGeneratorDAO.generateDiscountCardCode());
				discountCardManager.insert(dc);
			}
			logger.info("DiacountCardController:"+cardQuantity +" cards with "+percentage+"% are now inserted!");	
			JSFUtils.addInfoMessage(cardQuantity+" DiscountCards with "+percentage+"% added successfully.");
		} catch (Exception e) {
			logger.error(getManagedObjectName()+" with ID "+dc.getId()+" cannot be inserted from controller.", e);
			JSFUtils.addErrorMessage(ZUtils.getMessageFromException(e, 50));
		}			
		cleanNewDiscountCardForm();
		extractGroups();
    }

	private void cleanNewDiscountCardForm() {
		newPercDiscount.setValue(null);
		newCardQuantity.setValue(null);
	}

	public void deleteDiscountCard(){
		try {
			int count = 0;
			for(DiscountCard dc: discountCardList){
				if(dc.getUsedDate()==null){
					discountCardManager.delete(dc);
					count++;
				}
			}			
			JSFUtils.addInfoMessage("Unused "+count+" DiscountCards deleted successfully.");
			logger.info(count+" "+getManagedObjectName()+"s deleted from controller.");
		}catch (Exception e) {
			logger.error(getManagedObjectName()+"s can not be deleted.", e);
			JSFUtils.addErrorMessage(ZUtils.getMessageFromException(e, 50));
		}  
		extractGroups();
	}
	
	
	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public static Log getLogger() {
		return logger;
	}

	public HtmlInputText getfPercDiscount() {
		return fPercDiscount;
	}

	public void setfPercDiscount(HtmlInputText fPercDiscount) {
		this.fPercDiscount = fPercDiscount;
	}

	public Date getfUsedDate() {
		return fUsedDate;
	}

	public void setfUsedDate(Date fUsedDate) {
		this.fUsedDate = fUsedDate;
	}

	public HtmlInputText getNewPercDiscount() {
		return newPercDiscount;
	}

	public void setNewPercDiscount(HtmlInputText newPercDiscount) {
		this.newPercDiscount = newPercDiscount;
	}

	public Date getNewUsedDate() {
		return newUsedDate;
	}

	public void setNewUsedDate(Date newUsedDate) {
		this.newUsedDate = newUsedDate;
	}

	public DiscountCardManager getDiscountCardManager() {
		return discountCardManager;
	}

	public void setDiscountCardManager(DiscountCardManager discountCardManager) {
		this.discountCardManager = discountCardManager;
	}

	public List<DiscountCard> getDiscountCardList() {
		return discountCardList;
	}

	public void setDiscountCardList(List<DiscountCard> discountCardList) {
		this.discountCardList = discountCardList;
	}

	public DiscountCard getDiscountCardSelected() {
		return discountCardSelected;
	}

	public void setDiscountCardSelected(DiscountCard discountCardSelected) {
		this.discountCardSelected = discountCardSelected;
	}

	public HtmlInputText getNewCardQuantity() {
		return newCardQuantity;
	}

	public void setNewCardQuantity(HtmlInputText newCardQuantity) {
		this.newCardQuantity = newCardQuantity;
	}

	public List<DiscountCardGroup> getDiscountCardGroup() {
		return discountCardGroup;
	}

	public void setDiscountCardGroup(List<DiscountCardGroup> discountCardGroup) {
		this.discountCardGroup = discountCardGroup;
	}

	public DiscountCardGroup getDiscountCardGroupSelected() {
		return discountCardGroupSelected;
	}

	public void setDiscountCardGroupSelected(DiscountCardGroup discountCardGroupSelected) {
		DiscountCard dc = new DiscountCard();
		dc.setPercDiscount(discountCardGroupSelected.getPercentage());
		discountCardList = discountCardManager.search(dc);
		this.discountCardGroupSelected = discountCardGroupSelected;
	}

	public SelectItem[] getUsedOptions() {
		return usedOptions;
	}

	public void setUsedOptions(SelectItem[] usedOptions) {
		this.usedOptions = usedOptions;
	}

	public void setCodeGeneratorDAO(CodeGeneratorDAO codeGeneratorDAO) {
		this.codeGeneratorDAO = codeGeneratorDAO;
	}
	
}
