package dzr.hanom;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;

public class SearchIssueWithAllureReportTest {

    private static final String REPOSITORY = "Andrey-Lozbenev/qa_guru_HW10";
    private static final String ISSUE = "Allure test";
    Steps step = new Steps();

    @Test
    public void searchIssueWithSelenideOnlyTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        open("https://github.com");

        $(".header-search-input").click();
        $(".header-search-input").sendKeys("Andrey-Lozbenev/qa_guru_HW10");
        $(".header-search-input").submit();

        $(linkText("Andrey-Lozbenev/qa_guru_HW10")).click();
        $("#issues-tab").click();
        $(withText("Allure test")).should(Condition.exist);
    }

    @Test
    public void searchIssueWithLambdaStep() {

        step("Открываем главную страницу", () -> {
            open("https://github.com");
        });
        step("Ищем репозиторий " + REPOSITORY, () -> {
            $(".header-search-input").click();
            $(".header-search-input").sendKeys(REPOSITORY);
            $(".header-search-input").submit();
        });
        step("Кликаем по ссылке репозитория " + REPOSITORY, () -> {
            $(linkText(REPOSITORY)).click();
        });
        step("Открываем таб Issues", () -> {
            $("#issues-tab").click();
        });
        step("Проверяем наличие Issue с названием " + ISSUE, () -> {
            $(withText(ISSUE)).should(Condition.exist);
        });
    }

    @Test
    public void searchIssueWithStepAnnotationTest() {


        step.openMainPage()
                .searchForRepository(REPOSITORY)
                .clickOnRepositoryLink(REPOSITORY)
                .openIssuesTab()
                .shouldSeeIssueWithName(ISSUE);

    }
}