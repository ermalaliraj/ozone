package al.ozone.bl.model;

import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Email implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Timestamp creationDate;
	private List<String> to;
	private String template;
	private String subject;
	private Map<String, Object> parameters;
	private List<File> attachments;
	private int retryCount = 0;

	public Email() {
		creationDate = new Timestamp(System.currentTimeMillis());
		to = new ArrayList<String>();
		parameters = new HashMap<String, Object>();
		attachments = new ArrayList<File>();
	}

	public Email(String template) {
		this();
		this.template = template;
	}

	public void addTo(String emailAddress) {
		to.add(emailAddress);
	}

	public void addTo(List<String> emails) {
		for (String emailAddress : emails) {
			addTo(emailAddress);
		}
	}

	public void addParameter(String key, Object value) {
		parameters.put(key, value);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void addAttachment(File file) {
		attachments.add(file);
	}

	public List<File> getAttachments() {
		return attachments;
	}

	public void increaseRetryCount() {
		retryCount++;
	}

	public int getRetryCount() {
		return retryCount;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Email))
			return false;
		Email castOther = (Email) other;
		return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("id", id)
				.append("creationDate", creationDate)
				.append("to", to)
				.append("template", template)
				.append("subject", subject)
				//.append("parameters", parameters)
				//.append("attachments", attachments)
				.toString();
	}

}
