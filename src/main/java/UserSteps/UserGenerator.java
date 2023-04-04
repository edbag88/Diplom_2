package UserSteps;

import Dto.UserCreateDTO;
import org.apache.commons.lang3.RandomStringUtils;

public class UserGenerator {
    public UserCreateDTO randomDataCourier() {
        return new UserCreateDTO(RandomStringUtils.randomAlphabetic(10) + "@yandex.ru", "TestDiplom2", "Иван Абрамов");
    }
}
