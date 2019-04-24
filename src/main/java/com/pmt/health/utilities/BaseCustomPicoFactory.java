package com.pmt.health.utilities;

import com.pmt.health.controllers.CustomPicoFactory;
import com.pmt.health.objects.user.User;

/**
 * Extension of the standard PicoContainer ObjectFactory which will register the
 * proper AutomatioApi implementation based on a system property.
 */
public class BaseCustomPicoFactory extends CustomPicoFactory {
    public BaseCustomPicoFactory() {
        super();
        addClass(User.class);
    }
}