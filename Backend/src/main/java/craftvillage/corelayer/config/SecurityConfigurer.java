package craftvillage.corelayer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;

import craftvillage.bizlayer.services.MyUserDetailsService;
import craftvillage.corelayer.utilities.ConstantParameter;

@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
	@Autowired
	private MyUserDetailsService myuserDetailsService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter= new JwtRequestFilter();
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(myuserDetailsService).passwordEncoder(passwordEncoder());	
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		String url = "/" + ConstantParameter._URL_ROOT + "/" + ConstantParameter._URL_API + "/";
		String url_address = url + ConstantParameter.ServiceAddress._ADDRESS_SERVICE;
		String url_image = url + ConstantParameter.ServiceImage._IMAGE_SERVICE;
		String url_survey = url + ConstantParameter.ServiceSurvey._SURVEY_SERVICE;
		String url_answer = url + ConstantParameter.ServiceAnswer._ANSWER_SERVICE;
		String url_user = url + ConstantParameter.ServiceUser._USER_SERVICE;
		String url_village = url + ConstantParameter.ServiceVillage._VILLAGE_SERVICE;
		
		//http.csrf().disable();
		http.csrf().ignoringAntMatchers("/web/**",
										"/craftvillage/api/village/newvillage",
										url_answer + "/" + ConstantParameter.ServiceAnswer._ANSWER_GET_COMPLETED,
										url_answer + "/" + ConstantParameter.ServiceAnswer._ANSWER_GET_INPROGRESS,
										url_answer + "/" + ConstantParameter.ServiceAnswer._ANSWER_UPLOAD_FILE,										
										url_user + "/" + ConstantParameter.ServiceUser._USER_LOGIN,
										url_user + "/" + ConstantParameter.ServiceUser._USER_REGISTER,
										url_user + "/" + ConstantParameter.ServiceUser._USER_FORGOTTEN_PASS,
										url_user + "/" + ConstantParameter.ServiceUser._USER_ACTIVATE,
										url_user + "/" + ConstantParameter.ServiceUser._USER_GET_PASSWORD,
										url_user + "/" + ConstantParameter.ServiceUser._USER_CHANGE_PASS,
										url_user + "/" + ConstantParameter.ServiceUser._USER_UPDATE_INFOR,										
										url_village + "/" + ConstantParameter.ServiceVillage._VILLAGE_SUBMIT,										
										url_image + "/" + ConstantParameter.ServiceImage._IMAGE_GET_PICTURE,
										url_image + "/" + ConstantParameter.ServiceImage._IMAGE_DEL_PICTURE,																				
										url_address + "/" + ConstantParameter.ServiceAddress._ADDRESS_CHECK_VILLAGE);
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
			http.authorizeRequests().antMatchers(
										"/web/**",
										"/craftvillage/api/village/newvillage",
										url_user + "/" + ConstantParameter.ServiceUser._USER_LOGIN,
										url_user + "/" + ConstantParameter.ServiceUser._USER_REGISTER,
										url_user + "/" + ConstantParameter.ServiceUser._USER_LOGOUT_TEST,
										url_user + "/" + ConstantParameter.ServiceUser._USER_SEND_EMAIL,
										url_user + "/" + ConstantParameter.ServiceUser._USER_FORGOTTEN_PASS,
										url_user + "/" + ConstantParameter.ServiceUser._USER_GET_PASSWORD,										
										url_survey + "/" + ConstantParameter.ServiceSurvey._SURVEY_GET_SURVEY_BYID, 
										url_address + "/" + ConstantParameter.ServiceAddress._ADDRESS_GET_COUNTRY, 
										url_address + "/" + ConstantParameter.ServiceAddress._ADDRESS_GET_PROVINCE,
										url_address + "/" + ConstantParameter.ServiceAddress._ADDRESS_GET_DISTRICT,
										url_address + "/" + ConstantParameter.ServiceAddress._ADDRESS_GET_WARD,
										url_answer + "/" + ConstantParameter.ServiceAnswer._ANSWER_UPLOAD_FILE,
										url_address + "/" + ConstantParameter.ServiceAddress._ADDRESS_GET_VILLAGE
					).permitAll();
//			.antMatchers(				url + "/hello",
////										url_address + "/" + ConstantParameter.ServiceAddress._ADDRESS_GET_VILLAGE,
//										url_address + "/" + ConstantParameter.ServiceAddress._ADDRESS_GET_ADDRESS,
//										url_address + "/" + ConstantParameter.ServiceAddress._ADDRESS_CHECK_VILLAGE,
//										url_user + "/" + ConstantParameter.ServiceUser._USER_GET_DATA,
//										url_user + "/" + ConstantParameter.ServiceUser._USER_CHANGE_PASS,										
//										url_user + "/" + ConstantParameter.ServiceUser._USER_LOGOUT,
//										url_user + "/" + ConstantParameter.ServiceUser._USER_UPDATE_INFOR,										
//										url_village + "/" + ConstantParameter.ServiceVillage._VILLAGE_SUBMIT, 
//										url_village + "/" + ConstantParameter.ServiceVillage._VILLAGE_GET_INFOR,
//										url_village + "/" + ConstantParameter.ServiceVillage._VILLAGE_GET_SURVEY,										
//										url_answer + "/" + ConstantParameter.ServiceAnswer._ANSWER_UPLOAD_FILE,
//										url_village + "/" + ConstantParameter.ServiceVillage._VILLAGE_DETECT
//					).access("hasRole('ROLE_USER')")
//			.anyRequest().authenticated();
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return  new BCryptPasswordEncoder();
	}
}
