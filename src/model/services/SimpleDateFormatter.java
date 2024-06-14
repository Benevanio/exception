package model.services;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDateFormatter implements DateFormatter {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public Date parse(String dateStr) throws Exception {
        return sdf.parse(dateStr);
    }

    @Override
    public String format(Date date) {
        return sdf.format(date);
    }
}
