package craftvillage.bizlayer.services;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import craftvillage.datalayer.entities.UrUser;
import craftvillage.datalayer.entities.UserSurvey;
import craftvillage.datalayer.entities.Village;
import craftvillage.datalayer.repositories.UserSurveyRepository;
import craftvillage.datalayer.services.SurveyServ;
import craftvillage.datalayer.services.UserServ;

@Service
public class SurveyServices {

  @Autowired
  SurveyServ surveyServ = new SurveyServ();
  @Autowired
  UserServ userServ = new UserServ();
  @Autowired
  UserSurveyRepository userSurveyRepository;

  public int countMonthlySurvey(Village village) {
    int count = 0;
    Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
    int currentMonth = localCalendar.get(Calendar.MONTH);
    for (UserSurvey survey : village.getUserSurveys()) {
      int surveyMonth = survey.getDateSubmitSurvey().toInstant().atZone(ZoneId.systemDefault())
          .toLocalDate().getMonthValue();
      if (surveyMonth == currentMonth)
        count++;
    }
    return count;
  }

  public String getImageBySurveyId(int id) {
    UserSurvey userSurvey = userSurveyRepository.getOne(id);
    return userSurvey.getImage();
  }

  public UserSurvey getSubmitSurvey(String account, int activeId) {
    return surveyServ.getSubmitSurvey(account, activeId);
  }

  public UserSurvey getUserSurvey(UrUser user, String status) {
    return surveyServ.getUserSurvey(user, status);
  }

  public UserSurvey getUserSurveyById(int userSurveyId) {
    return surveyServ.getUserSurveyById(userSurveyId);
  }

  public boolean addUserSurvey(UserSurvey userSurvey) {
    return surveyServ.addUserSurvey(userSurvey);
  }

  public List<UserSurvey> getSrActiveInfoByStatus(UrUser user, String status) {
    return userServ.getSrActiveInfoByStatus(user, status);
  }

  public boolean changeStatus(int idUserSurvey, String newStatus) {
    return surveyServ.changeStatus(idUserSurvey, newStatus);
  }

  public String getStatus(String account, int activeId) {
    return userServ.getStatus(account, activeId);
  }

  public boolean setDateSubmitSurvey(int userSurveyId, Date dateSubmitSurvey) {
    return userServ.setDateSubmitSurvey(userSurveyId, dateSubmitSurvey);
  }

  public String getPollution(String pollution) {
    List<String> result = new ArrayList<String>();
    String[] list = {"?????t", "N?????c", "Kh??ng kh??"};
    for (int i = 0; i < pollution.length(); i++) {
      if (pollution.charAt(i) == '1')
        result.add(list[i]);
    }
    return String.join(" - ", list);
  }
}
