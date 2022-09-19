package craftvillage.datalayer.entities;
// Generated Mar 10, 2020 9:28:01 AM by Hibernate Tools 4.3.5.Final

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import craftvillage.corelayer.utilities.ConstantParameter;

/**
 * UserSurveyAnswer generated by hbm2java
 */
@Entity
@Table(name = "USER_SURVEY_ANSWER", schema = ConstantParameter._SCHEMA_NAME)
public class UserSurveyAnswer implements java.io.Serializable {

	private int id;
	private UserSurvey userSurvey;
	private int srSqId;
	private String answerContent;
	private String otherContent;

	public UserSurveyAnswer() {
	}

	public UserSurveyAnswer(int id) {
		this.id = id;
	}

	public UserSurveyAnswer(int id, UserSurvey userSurvey, int srSqId, String answerContent,
			String otherContent) {
		this.id = id;
		this.userSurvey = userSurvey;
		this.srSqId = srSqId;
		this.answerContent = answerContent;
		this.otherContent = otherContent;
	}
	public UserSurveyAnswer(int id, int srSqId, String answerContent,
			String otherContent) {
		this.id = id;
		this.srSqId = srSqId;
		this.answerContent = answerContent;
		this.otherContent = otherContent;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="USER_SUREY_ANSWER_SEQ")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	@JsonBackReference
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_SURVEY_ID")
	public UserSurvey getUserSurvey() {
		return this.userSurvey;
	}

	public void setUserSurvey(UserSurvey userSurvey) {
		this.userSurvey = userSurvey;
	}

	@Column(name = "SR_SQ_ID", precision = 22, scale = 0)
	public int getSrSqId() {
		return this.srSqId;
	}

	public void setSrSqId(int srSqId) {
		this.srSqId = srSqId;
	}

	@Column(name = "ANSWER_CONTENT", length = 100)
	public String getAnswerContent() {
		return this.answerContent;
	}

	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}

	@Column(name = "OTHER_CONTENT", length = 100)
	public String getOtherContent() {
		return this.otherContent;
	}

	public void setOtherContent(String otherContent) {
		this.otherContent = otherContent;
	}

}
