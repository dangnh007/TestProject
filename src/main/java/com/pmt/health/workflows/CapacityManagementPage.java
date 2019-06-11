package com.pmt.health.workflows;

import com.pmt.health.interactions.application.App;
import com.pmt.health.interactions.element.selenified.WebbElement;
import com.pmt.health.objects.user.User;
import com.pmt.health.utilities.LocatorType;
import org.testng.log4testng.Logger;

public class CapacityManagementPage {
    private final App app;
    private final WebbElement capacityManagementSidebarButton;

    Logger log = Logger.getLogger(CapacityManagementPage.class);
    private User user;

    public CapacityManagementPage(App app, User user) {
        this.app = app;
        this.user = user;
        this.capacityManagementSidebarButton = app.newElement(LocatorType.CSS, "a > svg[data-icon=\"chart-pie\"]");
    }
}
