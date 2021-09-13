package com.airfrance.ums.utility;

import com.airfrance.ums.entities.AppConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Utils {
    private static final Logger logger  = LoggerFactory.getLogger(Utils.class);
    public static boolean isOver18User(LocalDateTime birthday){
        Period between = Period.between(birthday.toLocalDate(), LocalDate.now());
        return between.getYears()>=18;
    }

    public static boolean isAllowedCountry(AppConfig appConfig, String country){
        Set<String> countries = null;
        String countryStr = null;

        boolean isCountryGlobalSet = Objects.nonNull(appConfig) && StringUtils.isNotBlank(appConfig.getConfigValue());

        if (isCountryGlobalSet){
            String[] split = appConfig.getConfigValue().split("\\|");
            countries = new HashSet<>();
            countries.addAll(Arrays.stream(split)
                    .map( word -> String.join(",",word.toUpperCase())
                    )
                    .collect(Collectors.toSet()));
        }else{
            countries = Constants.DEFAULT_COUNTRIES;
        }

        boolean result = StringUtils.isNotBlank(country) && countries.contains(country.toUpperCase());
        logger.info("End country is {} allowed",result ? "":"not");
        return result;

    }
}
