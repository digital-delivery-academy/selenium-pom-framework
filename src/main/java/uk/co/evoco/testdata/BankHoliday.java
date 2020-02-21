package uk.co.evoco.testdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BankHoliday {

    private String title;
    private LocalDate localDate;
    private DateTime dateTime;

    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocalDateTime(String dateFormat) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(dateFormat);
        return this.localDate.toString(dateTimeFormatter);
    }

    public DateTime getLocalDateTime() {
        return this.dateTime;
    }

    @JsonProperty("date")
    public void setLocalDateTime(String localDate) {
        this.dateTime=new DateTime(this.localDate).toDateTime();

    }
}
