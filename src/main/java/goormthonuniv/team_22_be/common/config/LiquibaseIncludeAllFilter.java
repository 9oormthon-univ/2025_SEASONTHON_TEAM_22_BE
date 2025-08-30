package goormthonuniv.team_22_be.common.config;

import liquibase.changelog.IncludeAllFilter;

public class LiquibaseIncludeAllFilter implements IncludeAllFilter {

    private static final String SQL_FORMAT = ".sql";

    @Override
    public boolean include(String changeLogPath) {
        return changeLogPath.endsWith(SQL_FORMAT);
    }
}
