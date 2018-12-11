package liu.chi.web.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import liu.chi.web.pojo.AppException;
import liu.chi.web.pojo.Error;
import liu.chi.web.pojo.Gender;
import liu.chi.web.pojo.Stu;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.time.LocalDateTime;

import static org.springframework.format.annotation.NumberFormat.Style.NUMBER;

/**
 * @author liuchi
 * @date 2018-09-12 11:14
 */
@RestController
public class AppRestController {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * getstu/liuchi/25/MALE
     *
     * @param gender:必须是大写,否则400
     */
    @RequestMapping(value = "/getstu/{nname}/{agee}/{gender}")
    public Stu getStu(@Valid @NotNull @NumberFormat(style = NUMBER) @PathVariable("agee") Integer age,
                      @NotNull @PathVariable("nname") String name,
                      @PathVariable("gender") Gender gender) {
        System.out.println("进入getStu()");
        Stu stu = new Stu();
        stu.setName(name);
        stu.setAge(age);
        stu.setGender(gender);
        stu.setBirthday(LocalDateTime.of(1994, 3, 9, 12, 13, 14));
        return stu;
    }

    /**
     * requestparam注解:默认是必须传递,否则400
     */
    @RequestMapping(value = "/getstu2")
    public Stu getStu2(@RequestParam("name") String name, @RequestParam("age") Integer age) {
        System.out.println("进入getStu2()");
        Stu stu = new Stu();
        stu.setName(name);
        stu.setAge(age);
        stu.setBirthday(LocalDateTime.now());
        return stu;
    }

    /**
     * 注入servlet获取参数,参数若不存在,则为null
     */
    @RequestMapping(value = "/getstu3")
    public void getStu3(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("进入getStu3()");
        System.out.println(request.getParameter("name"));
        response.getWriter().write("aaa");
    }

    /**
     * requestbody注解:封装content-type为application/json格式
     */
    @PostMapping(value = "/getstu4")
    public Stu getStu4(@RequestBody Stu stu) throws JsonProcessingException {
        System.out.println("进入getStu4()");
        System.out.println(objectMapper.writeValueAsString(stu));
        return stu;
    }

    /**
     * 该形式获取等于request.getParameter()
     */
    @GetMapping(value = "/getstu5", consumes = MediaType.ALL_VALUE)
    public Stu getStu5(Stu stu) {
        System.out.println("进入getStu5()");
        stu.setBirthday(LocalDateTime.now());
        return stu;
    }

    @PostMapping(value = "/getstu6", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Stu getStu6(@RequestParam("name") String name) {
        System.out.println("进入getStu6()");
        Stu stu = new Stu();
        stu.setName(name);
        stu.setBirthday(LocalDateTime.now());
        return stu;
    }

    /**
     * <form method="post" action="http://127.0.0.1:8080/springmvc/upload" enctype="multipart/form-data">
     * <input type= "text"name="name"/>
     * <input type="file" name="file2"/>
     * </form>
     */
    @PostMapping(value = "/upload")
    public Stu upload(@RequestParam("name") String name, @RequestParam("file2") MultipartFile file) throws IOException {
        System.out.println("进入upload()");
        Stu stu = new Stu();
        stu.setName(name);
        //表单名
        stu.setName(file.getName());
        //文件名
        OutputStream out = new FileOutputStream("/home/liuchi/桌面/" + file.getOriginalFilename());
        IOUtils.copy(file.getInputStream(), out);
        out.flush();
        out.close();
        return stu;
    }

    /**
     * "Content-Disposition", "attachment;filename=aa.jpeg"为附件形式下载文件
     * 拷贝至输出流则在页面显示
     */
    @GetMapping(value = "/download")
    public void download(HttpServletResponse response) throws IOException {
        System.out.println("进入download()");
        //response.setHeader("Content-Disposition", "attachment;filename=aa.jpeg");
        InputStream in = new FileInputStream("/home/liuchi/a.jpeg");
        IOUtils.copy(in, response.getOutputStream());
        response.getOutputStream().flush();
    }

    @RequestMapping("/appexception")
    public String appException() throws AppException {
        throw new AppException(Error.HAHA);
    }

}
