package com.example.devnotes.global.config;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.springframework.context.annotation.Configuration;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Stack;
import java.util.function.Predicate;

import static java.util.Arrays.stream;

@Configuration
public class P6SpySqlFormatter implements MessageFormattingStrategy {

    private static final String NEW_LINE = System.lineSeparator();
    private static final String TAP = "\t";
    private static final String P6SPY_FORMATTER = "P6SpySqlFormatter";
    private static final String PACKAGE = "com.example.devnotes";  // 패키지를 설정해야 메서드 스택을 확인할 수 있다.
    private static final String CREATE = "create";
    private static final String ALTER = "alter";
    private static final String DROP = "drop";
    private static final String COMMENT = "comment";


    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        if (sql.trim().isEmpty()) {
            return formatByCommand(category, getStackBuilder());
        }
        return formatBySql(formatSql(sql), category, getStackBuilder()) + getAdditionalMessages(elapsed);
    }

    private static String formatByCommand(String category, StringBuilder callStackBuilder) {
        return NEW_LINE
                + "Execute Command : "
                + NEW_LINE
                + TAP
                + category
                + NEW_LINE
                + TAP
                + (String.format("Call Stack (number 1 is entry point): %s", callStackBuilder))
                + NEW_LINE
                + "----------------------------------------------------------------------------------------------------";
    }

    private String formatBySql(String sql, String category, StringBuilder callStackBuilder) {
        String sqlType = isStatementDDL(sql, category) ? "DDL" : "DML";
        FormatStyle formatStyle = isStatementDDL(sql, category) ? FormatStyle.DDL : FormatStyle.BASIC;

        return NEW_LINE
                + "Execute " + sqlType + " : "
                + NEW_LINE
                + formatStyle
                .getFormatter()
                .format(sql)
                + NEW_LINE
                + NEW_LINE
                + TAP
                + (String.format("Call Stack (number 1 is entry point): %s", callStackBuilder));
    }

    private String getAdditionalMessages(long elapsed) {
        return NEW_LINE
                + String.format("Execution Time: %s ms", elapsed)
                + NEW_LINE
                + "----------------------------------------------------------------------------------------------------";
    }

    private boolean isStatementDDL(String sql, String category) {
        return isStatement(category) && isDDL(sql.trim().toLowerCase(Locale.ROOT));
    }

    private boolean isStatement(String category) {
        return Category.STATEMENT.getName().equals(category);
    }

    private boolean isDDL(String lowerSql) {
        return lowerSql.startsWith(CREATE)
                || lowerSql.startsWith(ALTER)
                || lowerSql.startsWith(DROP)
                || lowerSql.startsWith(COMMENT);
    }

    private StringBuilder getStackBuilder() {
        final Stack<String> callStack = new Stack<>();
        stream(new Throwable().getStackTrace())
                .map(StackTraceElement::toString)
                .filter(isExcludeWords())
                .forEach(callStack::push);

        int order = 1;
        final StringBuilder callStackBuilder = new StringBuilder();
        while (!callStack.empty()) {
            callStackBuilder.append(MessageFormat.format("{0}\t\t{1}. {2}", NEW_LINE, order++, callStack.pop()));
        }
        return callStackBuilder;
    }

    private Predicate<String> isExcludeWords() {
        return charSequence -> charSequence.startsWith(PACKAGE) && !charSequence.contains(P6SPY_FORMATTER);
    }

    private String formatSql(String sql) {
        return sql.replaceAll("=", " = ");
    }
}
