package com.airfrance.ums.utility;


import com.airfrance.ums.entities.AppConfig;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {
    private static final Logger logger  = LoggerFactory.getLogger(UtilsTest.class);

    @Test
    void isOver18User() {
        logger.info("Given user with birthday 2001/04/05 return true");
        LocalDateTime birthday = LocalDateTime.of(2001, 04, 05, 0, 0);
        assertTrue(Utils.isOver18User(birthday));

        logger.info("Given user with birthday 2014/01/30 return false");
        LocalDateTime birthday2 = LocalDateTime.of(2014, 01, 30, 0, 0);
        assertFalse(Utils.isOver18User(birthday2));
    }

    @Test
    void isAllowedCountry() {
        AppConfig app = AppConfig.builder().configName(Constants.ALLOWED_COUNTRY).configValue("Fr|Usa|Italy|scotland").build();
        String country = "fr";
        assertTrue(Utils.isAllowedCountry(app,country));

        assertFalse(Utils.isAllowedCountry(app,"Poland"));
        assertTrue(Utils.isAllowedCountry(null,country));
    }
}
