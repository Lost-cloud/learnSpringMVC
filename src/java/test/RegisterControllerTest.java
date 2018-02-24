package test;

import com.king.config.web.RootConfig;
import com.king.config.web.WebConfig;
import com.king.web.controller.RegisterController;
import com.king.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextHierarchy({
        @ContextConfiguration(name = "parent", classes = RootConfig.class),
        @ContextConfiguration(name = "child", classes = WebConfig.class)
})
public class RegisterControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    private EmployeeRepository employeeRepository;
    @Before
    public void setup() {
        this.mockMvc=MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void showEmployeeByJsp() throws Exception {
        mockMvc.perform(get("/Employee/1"))
                .andExpect(view().name("showEmployee"))
                .andExpect(model().attributeExists("employee"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void showRegistrationForm() throws Exception {
        mockMvc.perform(get("/Employee/register"))
                .andExpect(view().name("registerForm"));
    }

    @Test
    public void processRegistration() throws Exception {
        mockMvc.perform(post("/Employee/register")
                .param("realName", "king")
                .param("note", "this is a mockMvc test")
                .param("sex","男")
                .param("birthday", LocalDate.of(1994,10,4).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .param("mobile", "18826455791")
                .param("email", "lost000@outlook.com")
                .param("position", "GangDong SenZen"))
                .andDo(print())
                .andExpect(handler().handlerType(RegisterController.class))
                .andExpect(handler().methodName("processRegistration"))
                .andExpect(redirectedUrl("/Employee/json/114"));
    }

    @Test
    public void showEmployee() throws Exception {
        mockMvc.perform(get("/params/json/1"))
                .andDo(print());
    }
}