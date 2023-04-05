import dto.UserCreateDTO;
import user_steps.UserGenerator;
import user_steps.UserRequest;
import user_steps.UserResponse;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserUpdateTest {
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
    @DisplayName("Проверка возможности смены почты зарегистрированным пользователем")
    public void testUpdateRegisteredUserEmail() {
        uniqueUser.setEmail(uniqueUser.getEmail() + "1");
        ValidatableResponse response = userRequest.updateUser(accessToken, uniqueUser);
        userResponse.updateDateUserInSusses(response);
    }
    @Test
    @DisplayName("Проверка возможности смены пароля зарегистрированным пользователем")
    public void testUpdateRegisteredUserPassword() {
        uniqueUser.setPassword(uniqueUser.getPassword() + "1");
        ValidatableResponse response = userRequest.updateUser(accessToken, uniqueUser);
        userResponse.updateDateUserInSusses(response);
    }
    @Test
    @DisplayName("Проверка возможности смены пароля зарегистрированным пользователем")
    public void testUpdateRegisteredUserName() {
        uniqueUser.setName(uniqueUser.getName() + "1");
        ValidatableResponse response = userRequest.updateUser(accessToken, uniqueUser);
        userResponse.updateDateUserInSusses(response);
    }
    @Test
    @DisplayName("Проверка возможности смены пароля незарегистрированным пользователем")
    public void testUpdateUnregisteredUserName() {
        uniqueUser.setName(uniqueUser.getName() + "1");
        ValidatableResponse response = userRequest.updateUser("1", uniqueUser);
        userResponse.updateDateUserNotInFailed(response);
    }
    @Test
    @DisplayName("Проверка возможности смены пароля зарегистрированным пользователем")
    public void testUpdateUnregisteredUserPassword() {
        uniqueUser.setPassword(uniqueUser.getPassword() + "1");
        ValidatableResponse response = userRequest.updateUser("1", uniqueUser);
        userResponse.updateDateUserNotInFailed(response);
    }
    @Test
    @DisplayName("Проверка возможности смены почты зарегистрированным пользователем")
    public void testUpdateUnregisteredUserEmail() {
        uniqueUser.setEmail(uniqueUser.getEmail() + "1");
        ValidatableResponse response = userRequest.updateUser("1", uniqueUser);
        userResponse.updateDateUserNotInFailed(response);
    }
}
