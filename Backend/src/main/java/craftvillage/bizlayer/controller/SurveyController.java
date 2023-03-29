package craftvillage.bizlayer.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import craftvillage.bizlayer.services.SrSurveyQuestionService;
import craftvillage.bizlayer.services.SurveyServices;
import craftvillage.bizlayer.services.UserService;
import craftvillage.corelayer.utilities.ConstantParameter;
import craftvillage.datalayer.entities.SrSurveyQuestion;
import craftvillage.datalayer.entities.UrUser;
import craftvillage.datalayer.entities.UserSurvey;

@RestController
@RequestMapping("/" + ConstantParameter._URL_ROOT + "/" + ConstantParameter._URL_API + "/"
    + ConstantParameter.ServiceSurvey._SURVEY_SERVICE)
public class SurveyController {
  @Autowired
  private SurveyServices surveyServices;
  @Autowired
  private UserService userService;
  @Autowired
  private SrSurveyQuestionService srSurveyQuestionService;

  @GetMapping("/question")
  @ResponseBody
  public List<SrSurveyQuestion> getQuestion() {
    return srSurveyQuestionService.findByActive(1);
  }

  @GetMapping("/getImage")
  @ResponseBody
  public Map<String, String> getImage(@RequestParam("surveyId") int id) {
    return surveyServices.getImageBySurveyId(id);
  }

  @GetMapping(value = "/" + ConstantParameter.ServiceSurvey._SURVEY_GET_ALL_SURVEY)
  public Map<String, List<Map<String, Object>>> getAllSurvey(Principal principal) {
    UrUser user = userService.findByUsername(principal.getName());
    Map<String, List<Map<String, Object>>> response =
        new HashMap<String, List<Map<String, Object>>>();
    List<Map<String, Object>> completedSurvey = new ArrayList<Map<String, Object>>();
    List<Map<String, Object>> inprogressSurvey = new ArrayList<Map<String, Object>>();
    for (UserSurvey survey : user.getUserSurveys()) {
      Map<String, Object> data = new HashMap<String, Object>();
      data.put("villageId", survey.getVillage().getVillageId());
      data.put("villageName", survey.getVillage().getVillageName());
      data.put("date", survey.getDateSubmitSurvey().toString());
      if (survey.getVillage().getHasAdded() == 1) {
        completedSurvey.add(data);
      } else {
        inprogressSurvey.add(data);
      }
    }
    response.put("completedSurvey", completedSurvey);
    response.put("inprogressSurvey", inprogressSurvey);
    return response;
  }

  @GetMapping("/listImage")
  @ResponseBody
  public List<Integer> getListImage(@RequestParam("villageId") int villageId) {
    return surveyServices.getListImage(villageId);
  }
}
