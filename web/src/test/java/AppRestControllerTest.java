import liu.chi.web.WebApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author liuchi
 * @date 2018-09-22 10:24
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
@WebAppConfiguration
public class AppRestControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @Before
    public void prepare() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void getStu2Test() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/getstu2?name=liu&age=26");
        MvcResult mvcResult = mockMvc.perform(request).andReturn();

        System.out.println("response status:" + mvcResult.getResponse().getStatus());
        System.out.println("response body:" + mvcResult.getResponse().getContentAsString());
    }
}
