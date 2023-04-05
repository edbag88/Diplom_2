import dto.UserCreateDTO;
import dto.UserLoginDTO;
import user_steps.UserGenerator;
import user_steps.UserRequest;
import user_steps.UserResponse;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserLoginTest {
    private final UserGenerator userGenerator = new UserGenerator();
    private final UserRequest userRequest = new UserRequest();
    private final UserResponse userResponse = new UserResponse();
    private final UserCreateDTO uniqueUser = userGenerator.randomDataCourier();
    private String accessToken;

    @Before
    public void setUpUser() {
        ValidatableResponse create = userRequest.createUser(uniqueUser);
        accessToken = userResponse.assertCreationSusses(create);
    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            ValidatableResponse response = userRequest.deleteUser(accessToken);
            userResponse.assertDeleteSusses(response);
        }
    }

    @Test
    @DisplayName("Проверка успешного входа под существующим логином и паролем пользователя")
    public void testSuccessfulUserLogin() {
        UserLoginDTO userLoginDTO = UserLoginDTO.from(uniqueUser);
        ValidatableResponse login = userRequest.loginUser(userLoginDTO);
        userResponse.loginInSusses(login);
    }

    @Test
    @DisplayName("Проверка на ошибку при входе с неверным адресом электронной почты")
    public void testLoginErrorWithIncorrectEmail() {
        UserLoginDTO userLoginDTO = UserLoginDTO.from(uniqueUser);
        userLoginDTO.setEmail(userLoginDTO.getEmail() + "1");
        ValidatableResponse login = userRequest.loginUser(userLoginDTO);
        userResponse.loginInFailed(login);
    }

    @Test
    @DisplayName("Проверка на ошибку при входе с неверным паролем")
    public void testLoginErrorWithIncorrectPassword() {
        UserLoginDTO userLoginDTO = UserLoginDTO.from(uniqueUser);
        userLoginDTO.setPassword(userLoginDTO.getPassword() + "1");
        ValidatableResponse login = userRequest.loginUser(userLoginDTO);
        userResponse.loginInFailed(login);
    }
}
