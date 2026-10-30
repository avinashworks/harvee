package support;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import tests.BaseTest;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/** Saves a screenshot to target/screenshots when a test fails. */
public class ScreenshotExtension implements AfterTestExecutionCallback {
    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        if (context.getExecutionException().isEmpty()) {
            return;
        }
        Object instance = context.getRequiredTestInstance();
        if (instance instanceof BaseTest test && test.driver != null) {
            File source = ((TakesScreenshot) test.driver).getScreenshotAs(OutputType.FILE);
            Path dir = Path.of("target", "screenshots");
            Files.createDirectories(dir);
            String name = context.getDisplayName().replaceAll("[^a-zA-Z0-9_-]", "_");
            Files.copy(source.toPath(), dir.resolve(name + ".png"), StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
