import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 다음 경로에 대한 요청은 인증 없이 접근을 허용하도록 설정한다.
		http.authorizeRequests().antMatchers("/", "/web/**", "/js/**", "/image/**", "/webjars/**").permitAll();

		// 위에서 언급한 경로 외에는 모두 인증을 거치도록 설정한다.
		http.authorizeRequests().anyRequest().authenticated();

		// 시큐리티가 제공하는 기본 로그인 화면은 CSRF 토큰을 무조건 전달한다.
		// 하지만 사용자 정의 로그인 화면에서는 CSRF 토큰을 전달하지 않는다.
		http.csrf().disable();

		// 사용자가 만든 로그인 화면을 띄운다.
		http.formLogin().loginPage("/web/login");

		// 로그아웃 설정
        http.logout().logoutUrl("/web/logout").logoutSuccessUrl("/");
	}

}