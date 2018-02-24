package test;

import com.king.config.web.RootConfig;
import com.king.config.web.WebConfig;
import com.king.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes ={WebConfig.class,RootConfig.class})
public class ParamsControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    private EmployeeService employeeService;

    @Before
    public void setup() {
        this.mockMvc= MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testJsonRequest() throws Exception {
        mockMvc.perform(get("/params/json/findEmployee"))
                .andDo(print())
                .andExpect(view().name("postParams"));
    }

    @Test
    public void testJsonResponse() throws Exception {
        String requestBody="{\"employeeName\":\"king\"}";
        mockMvc.perform(post("/params/json/listEmployee")
                .contentType(MediaType.APPLICATION_JSON).content(requestBody)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
//                .andExpect(jsonPath("$.employeeName").value("king"))
                .andExpect(redirectedUrl("/params/json/showEmployees"));
    }


    @Test
    public void showEmployeeByJsp() throws Exception {
        mockMvc.perform(get("/params/json/1")).andDo(print());

    }

    @Test
    public void showEmployeeByJson() throws Exception {
        mockMvc.perform(get("/params/json/1")).andDo(print());
    }

    @Test
    public void findEmployee() throws Exception {
        mockMvc.perform(get("/params/json/findEmployee"))
                .andDo(print())
                .andExpect(view().name("postParams"));
    }


    @Test
    public void showEmployeesByGet() throws Exception {
        mockMvc.perform(get("/params/json/showEmployees"))
                .andDo(print())
                .andExpect(view().name("employeeList"));
    }

    @Test
    public void deleteEmployees() throws Exception {
        String ids="[12,25]";
        mockMvc.perform(post("/params/json/deleteEmployees")
                .contentType(MediaType.APPLICATION_JSON).content(ids)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(redirectedUrl("/params/json/findEmployee"));

    }

    @Test
    public void testSession() throws Exception {
        mockMvc.perform(get("/params/testSessionAttribute")).andDo(print()).andExpect(redirectedUrl("/json/sessionEmployees"));
    }
}