package al.ozone.bl.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class MenuItem implements Cloneable, Serializable {

	private static final long serialVersionUID = 4240310357109466865L;
	
	private String id;
    private String label;
    private String icon;
    private String link;

    private List<MenuItem> childs = new ArrayList<MenuItem>();
    private String roles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<MenuItem> getChilds() {
        return childs;
    }

    public void setChilds(List<MenuItem> childs) {
        this.childs = childs;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
    
    public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public boolean equals(final Object other) {
        if (!(other instanceof MenuItem))
            return false;
        MenuItem castOther = (MenuItem) other;
        return new EqualsBuilder().append(id, castOther.id).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(id).toHashCode();
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
        	.append("id", id)
        	.append("label", label)
            .append("icon", icon)
            .append("link", link)
            .append("childs", childs)
            .append("roles", roles).toString();
    }

	@Override
    public Object clone() {
        MenuItem clone = new MenuItem();
        clone.id = this.id;
        clone.label = this.label;
        clone.icon = this.icon;
        clone.link = this.link;
        clone.childs = this.childs;
        clone.roles = this.roles;
        return clone;
    }
}
