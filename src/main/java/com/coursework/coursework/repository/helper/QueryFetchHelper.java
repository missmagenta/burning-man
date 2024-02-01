package com.coursework.coursework.repository.helper;

import org.postgresql.util.PSQLException;
import org.jooq.exception.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

import static com.coursework.coursework.repository.helper.ErrorFormatUtil.getPsqlExceptionMess;

public class QueryFetchHelper<P, R> {
    private final P param;
    private final Function<P, R> function;
    public QueryFetchHelper(P p, Function<P, R> function) {
        this.param = p;
        this.function = function;
    }
    private static final int MAX_RETRIES = 3;
    private static final Logger logger = LoggerFactory.getLogger(QueryFetchHelper.class);
    public Pair<R, String> fetch() {
        int retries = 0;
        while (retries < MAX_RETRIES) {
            try {
                return Pair.of(function.apply(param), null);
            } catch (DataAccessException e) {
                retries++;

                logger.error("Database error occurred: {}. Retry {} of {}", e.getMessage(), retries, MAX_RETRIES);
                Throwable cause = e.getCause();

                if (cause instanceof PSQLException psqlException) {
                    String parseMessage = getPsqlExceptionMess(psqlException.getMessage());
                    if (parseMessage != null) {
                        return Pair.of(null, parseMessage);
                    }
                }

                try {
                    Thread.sleep(500 * retries);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        return new Pair<>(null, "Упс, что-то пошло не так");
    }
}
