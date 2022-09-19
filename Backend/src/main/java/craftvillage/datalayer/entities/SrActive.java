package craftvillage.datalayer.entities;
// Generated Mar 10, 2020 9:28:01 AM by Hibernate Tools 4.3.5.Final

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Sort;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import craftvillage.corelayer.utilities.ConstantParameter;
import craftvillage.datalayer.entities.UserSurvey;

/**
 * SrActive generated by hbm2java
 */
@Entity
@Table(name = "SR_ACTIVE", schema = ConstantParameter._SCHEMA_NAME)
public class SrActive implements java.io.Serializable {

	private int id;
	private Date dateActive;
	private Date dateEnd;
	private String describe;
	private String isActive;
	private String forRole;
	private SrSurvey srSurvey;
	private Set<UserSurvey> userSurveys = new LinkedHashSet<UserSurvey>(0);

	public SrActive() {
	}

	public SrActive(int id) {
		this.id = id;
	}

//	public SrActive(int id, SrSurvey srSurvey, Date dateActive, Date dateEnd, String describe, String isActive,
//			int userIdCreate, Set<UserSurvey> userSurveys) {
//		this.id = id;
//		this.srSurvey = srSurvey;
//		this.dateActive = dateActive;
//		this.dateEnd = dateEnd;
//		this.describe = describe;
//		this.isActive = isActive;
//		this.userIdCreate = userIdCreate;
//		this.userSurveys = userSurveys;
//	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="SR_ACTIVE_SEQ")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "SURVEY_ID")
	public SrSurvey getSrSurvey() {
		return this.srSurvey;
	}

	public void setSrSurvey(SrSurvey srSurvey) {
		this.srSurvey = srSurvey;
	}
	@JsonFormat(pattern="dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_ACTIVE", length = 7)
	public Date getDateActive() {
		return this.dateActive;
	}

	public void setDateActive(Date dateActive) {
		this.dateActive = dateActive;
	}
	@JsonFormat(pattern="dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_END", length = 7)
	public Date getDateEnd() {
		return this.dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	@Column(name = "DESCRIBE", length = 100)
	public String getDescribe() {
		return this.describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	@Column(name = "IS_ACTIVE", length = 100)
	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name = "FOR_ROLE", length = 100)
	public String getForRole() {
		return this.forRole;
	}

	public void setForRole(String forRole) {
		this.forRole = forRole;
	}
	@JsonBackReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "srActive")
	public Set<UserSurvey> getUserSurveys() {
		return this.userSurveys;
	}

	public void setUserSurveys(Set<UserSurvey> userSurveys) {
		this.userSurveys = userSurveys;
	}

}
