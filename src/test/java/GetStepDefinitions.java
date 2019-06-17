import com.pmt.health.steps.DeviceSteps;
import com.pmt.health.steps.UserSteps;
import com.pmt.health.steps.api.ApiSteps;
import com.pmt.health.steps.api.AuthSteps;
import com.pmt.health.steps.ui.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.context.annotation.Description;

import java.io.FileWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetStepDefinitions {

    private static final Pattern p = Pattern.compile("\\(([^)]*)\\)");

    public static void main(String[] args) throws Exception {

        FileWriter writer = new FileWriter("gherkin-steps.csv");
        writer.append("Functional Area,");
        writer.append("Usage,");
        writer.append("Gherkin,");
        writer.append("Description,");
        writer.append("Step Implementation,");
        writer.append("Parameter 1,");
        writer.append("Matcher,");
        writer.append("Parameter 2,");
        writer.append("Matcher,");
        writer.append("Parameter 3,");
        writer.append("Matcher,");
        writer.append("\n");

        List<Class> stepDefinitions = new ArrayList<>();
        stepDefinitions.add(UserSteps.class);
        stepDefinitions.add(DeviceSteps.class);
        // ui
        stepDefinitions.add(AddUserSteps.class);
        stepDefinitions.add(LoginSteps.class);
        stepDefinitions.add(CapacityManagementSteps.class);
        stepDefinitions.add(UserAdminSteps.class);
        // api
        stepDefinitions.add(AuthSteps.class);
        stepDefinitions.add(ApiSteps.class);


        try {
            for (Class c : stepDefinitions) {
                writer.append(c.getSimpleName());
                writer.append("\n");
                Method[] methods = c.getDeclaredMethods();
                for (Method method : methods) {
                    writer.append(",");
                    String aVal = null;
                    List<String> paramVals = new ArrayList<>();
                    if (method.isAnnotationPresent(Given.class)) {
                        writer.append("Given,");
                        aVal = method.getAnnotation(Given.class).value();
                    }
                    if (method.isAnnotationPresent(When.class)) {
                        writer.append("When,");
                        aVal = method.getAnnotation(When.class).value();
                    }
                    if (method.isAnnotationPresent(Then.class)) {
                        writer.append("Then,");
                        aVal = method.getAnnotation(Then.class).value();
                    }
                    if (aVal != null) {
                        writer.append(aVal.substring(1, aVal.length() - 1));
                        writer.append(",");
                        if (method.isAnnotationPresent(Description.class)) {
                            writer.append(method.getAnnotation(Description.class).value());
                        } else {
                            writer.append("");
                        }
                        writer.append(",");
                        writer.append(method.getName());
                        writer.append(",");
                        Matcher m = p.matcher(aVal);
                        while (m.find()) {
                            paramVals.add(m.group());
                        }
                        Parameter[] parameters = method.getParameters();
                        for (Parameter parameter : parameters) {
                            if (parameter.isNamePresent()) {
                                writer.append(parameter.getName());
                                writer.append(",");
                                writer.append(paramVals.get(0));
                                writer.append(",");
                                paramVals.remove(0);
                            }
                        }
                    }
                    writer.append("\n");
                }
            }
        } catch (Throwable e) {
            System.err.println(e);
        } finally {
            writer.close();
        }
    }
}