package al.ozone.bl.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class City implements Serializable{
	
	private static final long serialVersionUID = -336414112822459418L;
	
	//emrat e qyteteve jane shkurtuar per te perkuar me emrat e qyteteve ne fb ("vlor", perkon si me "vlore" si me "vlora")
	public static String BERAT = "Berat";
	public static String BULQIZE = "Bulqiz";//Bulqize
	public static String DELVINE = "Delvin";//Delvine
	public static String DEVOLL = "Devoll";
	public static String DIBER = "Diber";
	public static String DURRES = "Durr";
	public static String ELBASAN = "Elbasan";
	public static String FIER = "Fier";
	public static String GJIROKASTER = "Gjirokaster";
	public static String GRAMSH = "Gramsh";
	public static String HAS = "Has";
	public static String KAVAJE = "Kavaj";//Kavaje
	public static String KOLONJE = "Kolonj";//Kolonje
	public static String KORCE = "Korc";//Korce
	public static String KRUJE = "Kruj";//Kruje
	public static String KUCOVE = "Kucov";//Kucove
	public static String KUKES = "Kukes";
	public static String LAC = "Lac";//"Kurbin";
	public static String LEZHE = "Lezh";//Lezhe
	public static String LIBRAZHD = "Librazhd";
	public static String LUSHNJE = "Lushnje";
	public static String MALESI_E_MADHE = "Koplik";//Malesise se Madhe
	public static String MALLAKASTER = "Mallakastres";
	public static String MAT = "Mat";
	public static String MIRDITE = "Mirdit";//Mirdite
	public static String PEQIN = "Peqin";
	public static String PERMET = "Permet";
	public static String POGRADEC = "Pogradec";
	public static String PUK = "Puk";
	public static String SARANDE = "Sarand";//Sarande
	public static String SHKODER = "Shkod";//Shkoder
	public static String SKRAPAR = "Skrapar";
	public static String TEPELENE = "Tepelen";//Tepelene
	public static String TIRANE = "Tiran";//Tirane
	public static String BARJAM_CURRI = "Barjam Curr";//Tropoje
	public static String VLORE = "Vlor";//Vlore
	
	private String id;
    private String name;
    private double lat;  
    private double lng; 
    private int zoomLevel;
    private boolean active;
    
	public City() {
	}
	
	//constructor for creating a City at runtime
	public City(String id) {
		this.id=id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public int getZoomLevel() {
		return zoomLevel;
	}
	public void setZoomLevel(int zoomLevel) {
		this.zoomLevel = zoomLevel;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean equals(final Object obj) {
	        if (!(obj instanceof City))
	            return false;
	        City city = (City) obj;
	        return new EqualsBuilder().append(id, city.id)
					.append(id, city.id)
					.append(name, city.name)
					.append(active, city.active)
					.append(lat, city.lat)
					.append(lng, city.lng)
					.append(zoomLevel, city.zoomLevel)
	        		.isEquals();
	    }

	    public int hashCode() {
	        return new HashCodeBuilder().append(id)
	        		.append(id)
	        		.append(name)
	        		.append(active)
					.append(lat)
					.append(lng)
					.append(zoomLevel)
	        		.toHashCode();
	    }

		public String toString() {
	        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
	        	.appendSuper(super.toString())
	        	.append("id", id)
	        	.append("name", name)
	        	.append("active", active)
				.append("lat", lat)
				.append("lng", lng)
				.append("zoomLevel", zoomLevel)
	        	.toString();
	    }
}
