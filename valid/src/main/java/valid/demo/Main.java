package valid.demo;

import valid.ValidUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuchi
 * @date 2018-10-12 11:18
 */
public class Main {
    public static void main(String[] args) {
        Person person = new Person();
        person.setName("11");

//        person.setPhone(new Phone());

        List list = new ArrayList();
        list.add(new Phone());
        person.setPhones(list);

        ValidUtil.ValidationResult validationResult = ValidUtil.validateEntity(person);
        System.out.println(validationResult.isHasErrors());
        System.out.println(validationResult.getErrorMsg());
    }
}
