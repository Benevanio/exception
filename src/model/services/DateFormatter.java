package model.services;

import java.util.Date;

public interface DateFormatter {
    Date parse(String dateStr) throws Exception;

    String format(Date date);
}
